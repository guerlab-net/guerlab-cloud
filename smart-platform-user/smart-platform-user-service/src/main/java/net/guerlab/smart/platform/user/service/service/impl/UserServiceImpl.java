package net.guerlab.smart.platform.user.service.service.impl;

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
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.entity.Department;
import net.guerlab.smart.platform.user.service.entity.Position;
import net.guerlab.smart.platform.user.service.entity.User;
import net.guerlab.smart.platform.user.service.mapper.UserMapper;
import net.guerlab.smart.platform.user.service.service.*;
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

    private PositionService positionService;

    private DutyPermissionService dutyPermissionService;

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
        Collection<Position> positions = positionService.findByUserId(userId);

        return dutyPermissionService.findPermissionKeyList(positions);
    }

    @Override
    public boolean isAdmin(Long userId) {
        User user = selectById(userId);

        return user != null && user.getAdmin() != null && user.getAdmin();
    }

    @Override
    public void deleteAvatar(Long userId) {
        if (!NumberHelper.greaterZero(userId)) {
            throw new UserIdInvalidException();
        }

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserId(userId);

        User user = new User();
        user.setAvatar(DEFAULT_AVATAR);

        mapper.updateByExampleSelective(user, getExample(searchParams));
    }

    @Override
    protected void insertBefore(User entity) {
        checkProperties(entity);
        initProperties(entity);
        departmentCheck(entity);
    }

    @Override
    protected void insertAfter(User entity) {
        positionService.save(entity.getUserId(), entity.getDepartmentId(), UserAuthConstants.POSITION_ID_DEFAULT);
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
            positionService.delete(userId, entity.getOldDepartmentId(), UserAuthConstants.POSITION_ID_DEFAULT);
            positionService.save(userId, departmentId, UserAuthConstants.POSITION_ID_DEFAULT);
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
        removePosition(id);
        removeCharge(id);
        removeDirector(id);
    }

    private Collection<Long> getUserIds(UserSearchParams searchParams) {
        PositionSearchParams positionSearchParams = new PositionSearchParams();

        boolean needSearch = false;

        if (NumberHelper.greaterZero(searchParams.getDepartmentId())) {
            positionSearchParams.setDepartmentId(searchParams.getDepartmentId());
            needSearch = true;
        }
        if (NumberHelper.greaterZero(searchParams.getDutyId())) {
            positionSearchParams.setDutyId(searchParams.getDutyId());
            needSearch = true;
        }

        searchParams.setDepartmentId(null);

        if (!needSearch) {
            return null;
        }

        return positionService.findUserIdList(positionSearchParams);
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

    private void removePosition(Long id) {
        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setUserId(id);
        positionService.delete(searchParams);
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
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setDutyPermissionService(DutyPermissionService dutyPermissionService) {
        this.dutyPermissionService = dutyPermissionService;
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
