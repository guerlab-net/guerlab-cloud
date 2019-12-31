package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.RegexConstants;
import net.guerlab.smart.platform.commons.enums.Gender;
import net.guerlab.smart.platform.commons.exception.*;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.stream.utils.MessageUtils;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.exception.DutyInvalidException;
import net.guerlab.smart.platform.user.core.exception.NeedPasswordException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.entity.Department;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.guerlab.smart.platform.commons.Constants.*;

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

    private DutyService dutyService;

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
        String pwd = StringUtils.trimToNull(password);
        return user == null || pwd == null || !passwordEncoder.matches(pwd, user.getPassword());
    }

    @Override
    public Collection<String> getPermissionKeys(Long userId) {
        return dutyPermissionService.findPermissionKeyList(positionService.findByUserId(userId));
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

        Long mainDutyId = entity.getMainDutyId();

        if (mainDutyId == null || mainDutyId < UserAuthConstants.SYSTEM_POSITION_ID_RANGE) {
            entity.setMainDutyId(EMPTY_ID);
            entity.setMainDutyName(EMPTY_NAME);
        } else {
            entity.setMainDutyName(
                    dutyService.selectByIdOptional(mainDutyId).orElseThrow(DutyInvalidException::new).getDutyName());
            positionService.save(entity.getUserId(), entity.getDepartmentId(), mainDutyId);
        }
    }

    @Override
    protected void insertAfter(User entity) {
        positionService.save(entity.getUserId(), entity.getDepartmentId(), UserAuthConstants.POSITION_ID_DEFAULT);
        MessageUtils.send(userAddSenderChannel.output(), entity);
    }

    @Override
    protected void updateBefore(User entity) {
        checkPhoneAndEmail(entity);

        Long userId = entity.getUserId();
        Long departmentId = entity.getDepartmentId();
        Long oldDepartmentId = entity.getOldDepartmentId();

        if (entity.getEnableTwoFactorAuthentication() == null) {
            entity.setTwoFactorAuthenticationToken(null);
        }

        if (NumberHelper.greaterZero(departmentId)) {
            Department department = departmentService.selectById(departmentId);

            if (department == null) {
                throw new DepartmentInvalidException();
            }

            entity.setDepartmentName(department.getDepartmentName());

            positionService.delete(userId, oldDepartmentId, UserAuthConstants.POSITION_ID_DEFAULT);
            positionService.save(userId, departmentId, UserAuthConstants.POSITION_ID_DEFAULT);
        } else {
            entity.setDepartmentId(null);
            entity.setDepartmentName(null);
        }

        Long mainDutyId = entity.getMainDutyId();
        Long oldMainDutyId = entity.getOldMainDutyId();

        if (NumberHelper.greaterZero(mainDutyId) && !Objects.equals(mainDutyId, oldMainDutyId)) {
            if (NumberHelper.greaterZero(oldMainDutyId)) {
                positionService.delete(userId, oldDepartmentId, oldMainDutyId);
            }
            if (mainDutyId < UserAuthConstants.SYSTEM_POSITION_ID_RANGE) {
                entity.setMainDutyId(EMPTY_ID);
                entity.setMainDutyName(EMPTY_NAME);
            } else {
                Long depId = departmentId == null ? oldDepartmentId : departmentId;
                entity.setMainDutyName(dutyService.selectByIdOptional(mainDutyId).orElseThrow(DutyInvalidException::new)
                        .getDutyName());
                positionService.save(userId, depId, mainDutyId);
            }
        } else {
            entity.setMainDutyId(null);
            entity.setMainDutyName(null);
        }

        String pwd = StringUtils.trimToNull(entity.getPassword());
        if (pwd != null) {
            entity.setPassword(getPassword(pwd));
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

        Collection<Long> originUserIds = searchParams.getUserIds();
        boolean needSearch = false;

        if (NumberHelper.greaterZero(searchParams.getDepartmentId())) {
            positionSearchParams.setDepartmentId(searchParams.getDepartmentId());
            positionSearchParams.setUserIds(originUserIds);
            needSearch = true;
        }
        if (NumberHelper.greaterZero(searchParams.getDutyId())) {
            positionSearchParams.setDutyId(searchParams.getDutyId());
            positionSearchParams.setUserIds(originUserIds);
            needSearch = true;
        }

        searchParams.setDepartmentId(null);

        if (!needSearch) {
            return originUserIds;
        }

        if (originUserIds == null || originUserIds.isEmpty()) {
            return positionService.findUserIdList(positionSearchParams);
        }

        Collection<Long> userIds = positionService.findUserIdList(positionSearchParams);

        if (userIds == null) {
            return Collections.emptyList();
        }

        return originUserIds.stream().filter(NumberHelper::greaterZero).filter(userIds::contains)
                .collect(Collectors.toList());
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
        if (sameCheck(selectByUsername(username), entity.getUserId())) {
            throw new UsernameRepeatException();
        }

        entity.setUsername(username);
        entity.setName(name);

        checkPhoneAndEmail(entity);
    }

    private void checkPhoneAndEmail(User entity) {
        String phone = StringUtils.trimToNull(entity.getPhone());
        String email = StringUtils.trimToNull(entity.getEmail());

        if (email != null) {
            if (!Pattern.matches(RegexConstants.EMAIL_REG, email)) {
                throw new EmailFormatErrorException();
            }
            if (sameCheck(selectByEmail(email), entity.getUserId())) {
                throw new EmailRepeatException();
            }
        }
        if (phone != null) {
            if (!Pattern.matches(RegexConstants.PHONE_REG, phone)) {
                throw new PhoneFormatErrorException();
            }
            if (sameCheck(selectByPhone(phone), entity.getUserId())) {
                throw new PhoneNoRepeatException();
            }
        }

        entity.setPhone(phone);
        entity.setEmail(email);
    }

    private boolean sameCheck(User user, Long userId) {
        return user != null && (!NumberHelper.greaterZero(userId) || !Objects.equals(userId, user.getUserId()));
    }

    private void initProperties(User entity) {
        LocalDateTime now = LocalDateTime.now();

        entity.setUserId(sequence.nextId());
        entity.setEnabled(true);
        entity.setRegistrationTime(now);
        entity.setUpdateTime(now);
        entity.setPassword(getPassword(entity.getPassword()));
        entity.setEnableTwoFactorAuthentication(false);
        entity.setTwoFactorAuthenticationToken(EMPTY_NAME);

        if (entity.getGender() == null) {
            entity.setGender(Gender.OTHER);
        }
        if (entity.getEmail() == null) {
            entity.setEmail(EMPTY_NAME);
        }
        if (entity.getPhone() == null) {
            entity.setPhone(EMPTY_NAME);
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
        searchParams.setParentId(DEFAULT_PARENT_ID);

        Department department = departmentService.selectOne(searchParams);

        if (department == null) {
            throw new DepartmentInvalidException();
        }

        entity.setDepartmentId(department.getDepartmentId());
        entity.setDepartmentName(department.getDepartmentName());
    }

    private String getPassword(String rawPassword) {
        String pwd = StringUtils.trimToNull(rawPassword);
        if (pwd == null) {
            throw new NeedPasswordException();
        }
        return passwordEncoder.encode(pwd);
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
        department.setChargeUserId(EMPTY_ID);
        department.setChargeUserName(EMPTY_NAME);

        updateDepartment(searchParams, department);
    }

    private void removeDirector(Long id) {
        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDirectorUserId(id);

        Department department = new Department();
        department.setDirectorUserId(EMPTY_ID);
        department.setDirectorUserName(EMPTY_NAME);

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
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
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
