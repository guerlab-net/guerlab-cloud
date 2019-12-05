package net.guerlab.smart.platform.user.server.service.impl;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.commons.enums.Gender;
import net.guerlab.smart.platform.commons.exception.*;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.stream.utils.MessageUtils;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.exception.NeedPasswordException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.TakeOfficeSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.server.entity.Department;
import net.guerlab.smart.platform.user.server.entity.TakeOffice;
import net.guerlab.smart.platform.user.server.entity.User;
import net.guerlab.smart.platform.user.server.mapper.UserMapper;
import net.guerlab.smart.platform.user.server.service.*;
import net.guerlab.smart.platform.user.stream.binders.UserAddSenderChannel;
import net.guerlab.smart.platform.user.stream.binders.UserUpdateSenderChannel;
import net.guerlab.web.result.ListObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * 用户服务实现
 *
 * @author guer
 */
@Service
@EnableBinding({ UserAddSenderChannel.class, UserUpdateSenderChannel.class })
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserMapper>
        implements UserService, AfterDepartmentUpdateHandler {

    private DepartmentService departmentService;

    private TakeOfficeService takeOfficeService;

    private PositionPermissionService positionPermissionService;

    private PasswordEncoder passwordEncoder;

    private UserAddSenderChannel userAddSenderChannel;

    private UserUpdateSenderChannel userUpdateSenderChannel;

    @Override
    public void afterDepartmentUpdateHandler(Department department) {
        if (department == null || !NumberHelper.greaterZero(department.getDepartmentId()) || StringUtils
                .isBlank(department.getDepartmentName())) {
            return;
        }
        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setMainDepartmentId(department.getDepartmentId());

        User user = new User();
        user.setDepartmentName(department.getDepartmentName());

        mapper.updateByExampleSelective(user, getExample(searchParams));
    }

    @Override
    public ListObject<User> queryPage(UserSearchParams searchParams) {
        Collection<Long> userIds = getUserIds(searchParams);

        if (userIds != null && userIds.isEmpty()) {
            return ListObject.empty();
        }

        searchParams.setUserIds(userIds);
        return selectPage(searchParams);
    }

    @Override
    public Collection<User> queryAll(UserSearchParams searchParams) {
        Collection<Long> userIds = getUserIds(searchParams);

        if (userIds != null && userIds.isEmpty()) {
            return Collections.emptyList();
        }

        searchParams.setUserIds(userIds);
        return selectAll(searchParams);
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {
        if (!NumberHelper.greaterZero(userId)) {
            throw new UserIdInvalidException();
        }

        User user = new User();
        user.setPassword(getPassword(newPassword));

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserId(userId);

        mapper.updateByExampleSelective(user, getExample(searchParams));
    }

    @Override
    public boolean checkPasswordError(User user, String password) {
        return user == null || StringUtils.isBlank(password) || !passwordEncoder
                .matches(password.trim(), user.getPassword());
    }

    @Override
    public Collection<String> getPermissionKeys(Long userId) {
        Collection<TakeOffice> takeOffices = takeOfficeService.findByUserId(userId);

        return positionPermissionService.findPermissionKeyList(takeOffices);
    }

    @Override
    public boolean isAdmin(Long userId) {
        User user = selectById(userId);

        return user != null && user.getAdmin() != null && user.getAdmin();
    }

    @Override
    protected void insertBefore(User entity) {
        checkProperties(entity);
        initProperties(entity);
        departmentCheck(entity);
    }

    @Override
    protected void insertAfter(User entity) {
        takeOfficeService.save(entity.getUserId(), entity.getDepartmentId(), UserAuthConstants.POSITION_ID_DEFAULT);
        MessageUtils.send(userAddSenderChannel.output(), entity);
    }

    @Override
    protected void updateBefore(User entity) {
        checkPhoneAndEmail(entity);

        Long departmentId = entity.getDepartmentId();

        if (entity.getEnableTwoFactorAuthentication() == null) {
            entity.setTwoFactorAuthenticationToken(null);
        }

        if (NumberHelper.greaterZero(departmentId)) {
            Department department = departmentService.selectById(departmentId);

            if (department == null) {
                throw new DepartmentInvalidException();
            }

            entity.setDepartmentName(department.getDepartmentName());

            Long userId = entity.getUserId();
            takeOfficeService.delete(userId, entity.getOldDepartmentId(), UserAuthConstants.POSITION_ID_DEFAULT);
            takeOfficeService.save(userId, departmentId, UserAuthConstants.POSITION_ID_DEFAULT);
        } else {
            entity.setDepartmentId(null);
            entity.setDepartmentName(null);
        }

        if (StringUtils.isNotBlank(entity.getPassword())) {
            entity.setPassword(getPassword(entity.getPassword()));
        }

        entity.setUpdateTime(LocalDateTime.now());
    }

    @Override
    protected void updateAfter(User entity) {
        MessageUtils.send(userUpdateSenderChannel.output(), entity);
    }

    @Override
    protected void deleteByIdAfter(Long id, Boolean force) {
        removeTakeOffice(id);
        removeCharge(id);
        removeDirector(id);
    }

    private Collection<Long> getUserIds(UserSearchParams searchParams) {
        TakeOfficeSearchParams takeOfficeSearchParams = new TakeOfficeSearchParams();

        boolean needSearch = false;

        if (NumberHelper.greaterZero(searchParams.getDepartmentId())) {
            takeOfficeSearchParams.setDepartmentId(searchParams.getDepartmentId());
            needSearch = true;
        }
        if (NumberHelper.greaterZero(searchParams.getPositionId())) {
            takeOfficeSearchParams.setPositionId(searchParams.getPositionId());
            needSearch = true;
        }

        searchParams.setDepartmentId(null);

        if (!needSearch) {
            return null;
        }

        return takeOfficeService.findUserIdList(takeOfficeSearchParams);
    }

    private void checkProperties(User entity) {
        String username = StringUtils.trimToNull(entity.getUsername());
        String name = StringUtils.trimToNull(entity.getName());

        if (StringUtils.isBlank(entity.getPassword())) {
            throw new NeedPasswordException();
        }
        if (username == null) {
            throw new UsernameInvalidException();
        }
        if (name == null) {
            throw new FullNameInvalidException();
        }
        if (usernameSameCheck(username, entity.getUserId())) {
            throw new UsernameRepeatException();
        }

        entity.setUsername(username);
        entity.setName(name);

        checkPhoneAndEmail(entity);
    }

    private void checkPhoneAndEmail(User entity) {
        String phone = StringUtils.trimToNull(entity.getPhone());
        String email = StringUtils.trimToNull(entity.getEmail());

        if (phoneSameCheck(phone, entity.getUserId())) {
            throw new PhoneNoRepeatException();
        }
        if (emailSameCheck(email, entity.getUserId())) {
            throw new EmailRepeatException();
        }

        entity.setPhone(phone);
        entity.setEmail(email);
    }

    private boolean usernameSameCheck(String username, Long userId) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUsername(username);

        return sameCheck(searchParams, userId);
    }

    private boolean phoneSameCheck(String phone, Long userId) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setPhone(phone);

        return sameCheck(searchParams, userId);
    }

    private boolean emailSameCheck(String email, Long userId) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setEmail(email);

        return sameCheck(searchParams, userId);
    }

    private boolean sameCheck(UserSearchParams searchParams, Long userId) {
        User user = selectOne(searchParams);

        if (user == null) {
            return false;
        }

        return !NumberHelper.greaterZero(userId) || !Objects.equals(userId, user.getUserId());
    }

    private void initProperties(User entity) {
        LocalDateTime now = LocalDateTime.now();

        entity.setUserId(sequence.nextId());
        entity.setEnabled(true);
        entity.setRegistrationTime(now);
        entity.setUpdateTime(now);
        entity.setPassword(getPassword(entity.getPassword()));
        entity.setEnableTwoFactorAuthentication(false);
        entity.setTwoFactorAuthenticationToken(Constants.EMPTY_NAME);

        if (entity.getGender() == null) {
            entity.setGender(Gender.OTHER);
        }
        if (StringUtils.isBlank(entity.getEmail())) {
            entity.setEmail(Constants.EMPTY_NAME);
        }
        if (StringUtils.isBlank(entity.getPhone())) {
            entity.setPhone(Constants.EMPTY_NAME);
        }
        if (StringUtils.isBlank(entity.getAvatar())) {
            entity.setAvatar(DEFAULT_AVATAR);
        }
    }

    private void departmentCheck(User entity) {
        Long departmentId = entity.getDepartmentId();

        if (NumberHelper.greaterZero(departmentId)) {
            Department department = departmentService.selectById(departmentId);

            if (department != null) {
                entity.setDepartmentName(department.getDepartmentName());
                return;
            }
        }

        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setParentId(Constants.DEFAULT_PARENT_ID);

        Department department = departmentService.selectOne(searchParams);

        if (department == null) {
            throw new DepartmentInvalidException();
        }

        entity.setDepartmentId(department.getDepartmentId());
        entity.setDepartmentName(department.getDepartmentName());
    }

    private String getPassword(String rawPassword) {
        if (StringUtils.isBlank(rawPassword)) {
            throw new NeedPasswordException();
        }

        return passwordEncoder.encode(rawPassword.trim());
    }

    private void removeTakeOffice(Long id) {
        TakeOfficeSearchParams searchParams = new TakeOfficeSearchParams();
        searchParams.setUserId(id);
        takeOfficeService.delete(searchParams);
    }

    private void removeCharge(Long id) {
        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setChargeUserId(id);

        Department department = new Department();
        department.setChargeUserId(Constants.EMPTY_ID);
        department.setChargeUserName(Constants.EMPTY_NAME);

        updateDepartment(searchParams, department);
    }

    private void removeDirector(Long id) {
        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDirectorUserId(id);

        Department department = new Department();
        department.setDirectorUserId(Constants.EMPTY_ID);
        department.setDirectorUserName(Constants.EMPTY_NAME);

        updateDepartment(searchParams, department);
    }

    private void updateDepartment(DepartmentSearchParams searchParams, Department department) {
        Collection<Department> departments = departmentService.selectAll(searchParams);

        Collection<Long> departmentIds = CollectionUtil.toList(departments, Department::getDepartmentId);

        if (CollectionUtil.isEmpty(departmentIds)) {
            return;
        }

        DepartmentSearchParams departmentSearchParams = new DepartmentSearchParams();
        departmentSearchParams.setDepartmentIds(departmentIds);

        departmentService.updateByExampleSelective(department, departmentService.getExample(departmentSearchParams));
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setTakeOfficeService(TakeOfficeService takeOfficeService) {
        this.takeOfficeService = takeOfficeService;
    }

    @Autowired
    public void setPositionPermissionService(PositionPermissionService positionPermissionService) {
        this.positionPermissionService = positionPermissionService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserAddSenderChannel(UserAddSenderChannel userAddSenderChannel) {
        this.userAddSenderChannel = userAddSenderChannel;
    }

    @Autowired
    public void setUserUpdateSenderChannel(UserUpdateSenderChannel userUpdateSenderChannel) {
        this.userUpdateSenderChannel = userUpdateSenderChannel;
    }
}
