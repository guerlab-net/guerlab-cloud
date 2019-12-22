package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.commons.exception.UserIdInvalidException;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.OrderEntityUtils;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.stream.utils.MessageUtils;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.exception.*;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.entity.*;
import net.guerlab.smart.platform.user.service.mapper.DepartmentMapper;
import net.guerlab.smart.platform.user.service.searchparams.DepartmentParentsSearchParams;
import net.guerlab.smart.platform.user.service.service.*;
import net.guerlab.smart.platform.user.stream.binders.DepartmentAddSenderChannel;
import net.guerlab.smart.platform.user.stream.binders.DepartmentUpdateSenderChannel;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门服务实现
 *
 * @author guer
 */
@Service
@EnableBinding({ DepartmentAddSenderChannel.class, DepartmentUpdateSenderChannel.class })
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long, DepartmentMapper>
        implements DepartmentService {

    private static final Collection<DepartmentParents> EMPTY_DEPARTMENT_PARENTS = Collections.emptyList();

    private PositionService positionService;

    private UserService userService;

    private DepartmentParentsService parentsService;

    private DepartmentTypeService departmentTypeService;

    private DepartmentDutyDistributionService distributionService;

    private DepartmentAddSenderChannel departmentAddSenderChannel;

    private DepartmentUpdateSenderChannel departmentUpdateSenderChannel;

    private static Collection<Long> filterParentIds(Collection<Long> all, Collection<Long> add,
            Collection<Long> remove) {
        if (all == null) {
            return Collections.emptyList();
        }

        if (CollectionUtil.isNotEmpty(remove)) {
            all.removeAll(remove);
        }

        if (CollectionUtil.isNotEmpty(add)) {
            all.addAll(add);
        }

        return all;
    }

    @Override
    protected void insertBefore(Department entity) {
        if (StringUtils.isBlank(entity.getDepartmentName())) {
            throw new DepartmentNameInvalidException();
        }
        if (!NumberHelper.greaterZero(entity.getParentId())) {
            entity.setParentId(Constants.DEFAULT_PARENT_ID);
        } else {
            getDepartment(entity.getParentId());
        }
        if (!NumberHelper.greaterZero(entity.getDirectorUserId())) {
            entity.setDirectorUserId(Constants.EMPTY_ID);
            entity.setDirectorUserName(Constants.EMPTY_NAME);
        }
        if (!NumberHelper.greaterZero(entity.getChargeUserId())) {
            entity.setChargeUserId(Constants.EMPTY_ID);
            entity.setChargeUserName(Constants.EMPTY_NAME);
        }

        setDepartmentType(entity);

        entity.setDepartmentId(sequence.nextId());
        entity.setUpdateTime(LocalDateTime.now());
        OrderEntityUtils.propertiesCheck(entity);
    }

    private void setDepartmentType(Department entity) {
        String departmentTypeKey = StringUtils.trimToNull(entity.getDepartmentTypeKey());
        if (departmentTypeKey == null) {
            entity.setDepartmentTypeKey(DepartmentType.DEFAULT_KEY);
            entity.setDepartmentTypeName(DepartmentType.DEFAULT_NAME);
            return;
        }

        DepartmentType departmentType = departmentTypeService.selectById(departmentTypeKey);

        if (departmentType == null) {
            throw new DepartmentTypeInvalidException();
        }

        entity.setDepartmentTypeKey(departmentType.getDepartmentTypeKey());
        entity.setDepartmentTypeName(departmentType.getDepartmentTypeName());
    }

    @Override
    protected void insertAfter(Department entity) {
        setParentInfo(entity);
        setManagerInfo(entity);

        MessageUtils.send(departmentAddSenderChannel.output(), entity);
    }

    private void setParentInfo(Department entity) {
        Long parentId = entity.getParentId();

        if (!NumberHelper.greaterZero(parentId)) {
            return;
        }

        Collection<Long> parentIds = parentsService.findParentIdsByDepartmentId(parentId);
        parentIds.add(parentId);

        List<DepartmentParents> parents = parentIds.stream()
                .map(pid -> new DepartmentParents(entity.getDepartmentId(), pid)).collect(Collectors.toList());

        parentsService.save(parents);
    }

    private void setManagerInfo(Department entity) {
        if (!NumberHelper.allGreaterZero(entity.getDirectorUserId(), entity.getChargeUserId())) {
            return;
        }

        Long departmentId = entity.getDepartmentId();

        List<Position> positions = new ArrayList<>(2);

        if (NumberHelper.greaterZero(entity.getDirectorUserId())) {
            if (userService.selectById(entity.getDirectorUserId()) == null) {
                throw new UserInvalidException();
            }

            Position position = new Position(entity.getDirectorUserId(), departmentId,
                    UserAuthConstants.POSITION_ID_DIRECTOR);

            positions.add(position);
        }

        if (NumberHelper.greaterZero(entity.getChargeUserId())) {
            if (userService.selectById(entity.getChargeUserId()) == null) {
                throw new UserInvalidException();
            }

            Position position = new Position(entity.getChargeUserId(), departmentId,
                    UserAuthConstants.POSITION_ID_CHARGE);

            positions.add(position);
        }

        positionService.save(positions);
    }

    @Override
    protected void updateBefore(Department entity) {
        if (entity == null) {
            throw new DepartmentInvalidException();
        }

        Long departmentId = entity.getDepartmentId();

        if (!NumberHelper.greaterZero(departmentId)) {
            throw new DepartmentIdInvalidException();
        }

        setDepartmentType(entity);

        Long parentId = entity.getParentId();

        if (parentId == null || parentId < 0 || Objects.equals(parentId, departmentId)) {
            entity.setParentId(null);
            return;
        }

        if (NumberHelper.greaterZero(parentId) && selectById(parentId) == null) {
            throw new ParentDepartmentIdInvalidException();
        }

        //查询当前节点的所有下级ID列表
        Collection<Long> childrenIds = getChildrenIdsByDepartmentId(departmentId);

        //判断是否有循环依赖
        if (childrenIds.contains(parentId)) {
            throw new ParentDepartmentIdInvalidException();
        }

        //构造上级ID列表map
        Map<Long, Collection<Long>> parentIdsMap = new HashMap<>(childrenIds.size() + 1);
        //判断当前节点是否变更为顶级节点
        boolean topNode = Objects.equals(Constants.DEFAULT_PARENT_ID, parentId);
        //获取目标父节点的上级节点列表
        Collection<Long> adds = topNode ? new ArrayList<>() : parentsService.findParentIdsByDepartmentId(parentId);
        //如果不为顶级则添加目标上级节点ID到添加队列
        if (NumberHelper.greaterZero(parentId)) {
            adds.add(parentId);
        }

        if (!childrenIds.isEmpty()) {
            //当前节点所有下级列表
            Map<Long, List<Long>> parentsMap = findParentsMapByChildrenIds(childrenIds);

            //获取需要删除的上级ID列表
            Collection<Long> remove = parentsService.findParentIdsByDepartmentId(departmentId);

            //设置各子节点的上级列表
            parentsMap.forEach((childrenDepartmentId, childrenParentIds) -> parentIdsMap
                    .put(childrenDepartmentId, filterParentIds(childrenParentIds, adds, remove)));
        }

        //设置当前节点的上级列表
        parentIdsMap.put(departmentId, adds);
        batchSaveParents(parentIdsMap);

        entity.setUpdateTime(LocalDateTime.now());
    }

    private Collection<Long> getChildrenIdsByDepartmentId(Long departmentId) {
        Collection<Long> children = parentsService.findDepartmentIdsByParentId(departmentId);
        return CollectionUtil.isEmpty(children) ? Collections.emptyList() : children;
    }

    private Map<Long, List<Long>> findParentsMapByChildrenIds(Collection<Long> childrenIds) {
        DepartmentParentsSearchParams searchParams = new DepartmentParentsSearchParams();
        searchParams.setDepartmentIds(childrenIds);

        Map<Long, List<DepartmentParents>> map = CollectionUtil
                .group(parentsService.findList(searchParams), DepartmentParents::getDepartmentId);

        Map<Long, List<Long>> result = new HashMap<>(map.size());

        map.forEach((key, value) -> result.put(key, CollectionUtil.toList(value, DepartmentParents::getParentId)));

        return result;
    }

    private void batchSaveParents(Map<Long, Collection<Long>> parentIdsMap) {
        DepartmentParentsSearchParams deleteSearchParams = new DepartmentParentsSearchParams();
        deleteSearchParams.setDepartmentIds(parentIdsMap.keySet());

        parentsService.delete(deleteSearchParams);

        List<DepartmentParents> saves = parentIdsMap.entrySet().stream().map(entry -> {
            if (entry.getValue() == null) {
                return EMPTY_DEPARTMENT_PARENTS;
            } else {
                Long departmentId = entry.getKey();
                return entry.getValue().stream().map(parentId -> new DepartmentParents(departmentId, parentId))
                        .collect(Collectors.toList());
            }
        }).flatMap(Collection::stream).collect(Collectors.toList());

        if (!saves.isEmpty()) {
            parentsService.save(saves);
        }
    }

    @Override
    protected void updateAfter(Department entity) {
        SpringApplicationContextUtil.getContext().getBeansOfType(AfterDepartmentUpdateHandler.class).values()
                .forEach(handler -> handler.afterDepartmentUpdateHandler(entity));

        MessageUtils.send(departmentUpdateSenderChannel.output(), entity);
    }

    @Override
    public void delete(Department entity, Boolean force) {
        if (entity == null) {
            throw new DepartmentInvalidException();
        }

        deleteById(entity.getDepartmentId(), force);
    }

    @Override
    protected void deleteByIdBefore(Long id, Boolean force) {
        if (!NumberHelper.greaterZero(id)) {
            throw new DepartmentIdInvalidException();
        }

        UserSearchParams userSearchParams = new UserSearchParams();
        userSearchParams.setDepartmentId(id);

        if (userService.selectCount(userSearchParams) != 0) {
            throw new DepartmentHasUserException();
        }

        DepartmentSearchParams departmentSearchParams = new DepartmentSearchParams();
        departmentSearchParams.setParentId(id);

        if (selectCount(departmentSearchParams) != 0) {
            throw new HasSubDepartmentException();
        }

        if (selectCount((DepartmentSearchParams) null) <= 1) {
            throw new OnlyOneDepartmentException();
        }
    }

    @Override
    protected void deleteAfter(Department entity, Boolean force) {
        super.deleteByIdAfter(entity.getDepartmentId(), force);
    }

    @Override
    protected void deleteByIdAfter(Long id, Boolean force) {
        PositionSearchParams positionSearchParams = new PositionSearchParams();
        positionSearchParams.setDepartmentId(id);
        positionService.delete(positionSearchParams);

        distributionService.deleteByDepartmentId(id);

        DepartmentParentsSearchParams departmentParentsSearchParams = new DepartmentParentsSearchParams();
        departmentParentsSearchParams.setDepartmentId(id);
        parentsService.delete(departmentParentsSearchParams);
    }

    @Override
    public void setDirectorUser(Long departmentId, Long userId) {
        Department department = getDepartment(departmentId);
        User user = setManager(department, userId, UserAuthConstants.POSITION_ID_DIRECTOR);

        department.setDirectorUserId(userId);
        department.setDirectorUserName(user.getName());

        mapper.updateByPrimaryKey(department);
    }

    @Override
    public void setChargeUser(Long departmentId, Long userId) {
        Department department = getDepartment(departmentId);
        User user = setManager(department, userId, UserAuthConstants.POSITION_ID_CHARGE);

        department.setChargeUserId(userId);
        department.setChargeUserName(user.getName());

        mapper.updateByPrimaryKey(department);
    }

    private Department getDepartment(Long departmentId) {
        if (!NumberHelper.greaterZero(departmentId)) {
            throw new DepartmentIdInvalidException();
        }

        return selectByIdOptional(departmentId).orElseThrow(DepartmentInvalidException::new);
    }

    private User getUser(Long userId) {
        if (!NumberHelper.greaterZero(userId)) {
            throw new UserIdInvalidException();
        }

        return userService.selectByIdOptional(userId).orElseThrow(UserInvalidException::new);
    }

    private User setManager(Department department, Long userId, Long dutyId) {
        User user = getUser(userId);

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setDepartmentId(department.getDepartmentId());
        searchParams.setDutyId(dutyId);

        positionService.delete(searchParams);

        Position position = new Position();
        position.setDepartmentId(department.getDepartmentId());
        position.setDutyId(dutyId);
        position.setUserId(userId);

        positionService.save(position);

        return user;
    }

    @Override
    public void removeDirectorUser(Long departmentId) {
        removeManager(departmentId, UserAuthConstants.POSITION_ID_DIRECTOR);

        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentId(departmentId);

        Department department = new Department();
        department.setDirectorUserId(Constants.EMPTY_ID);
        department.setDirectorUserName(Constants.EMPTY_NAME);

        mapper.updateByExampleSelective(department, getExample(searchParams));
    }

    @Override
    public void removeChargeUser(Long departmentId) {
        removeManager(departmentId, UserAuthConstants.POSITION_ID_CHARGE);

        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentId(departmentId);

        Department department = new Department();
        department.setChargeUserId(Constants.EMPTY_ID);
        department.setChargeUserName(Constants.EMPTY_NAME);

        mapper.updateByExampleSelective(department, getExample(searchParams));
    }

    private void removeManager(Long departmentId, Long dutyId) {
        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setDepartmentId(departmentId);
        searchParams.setDutyId(dutyId);

        positionService.delete(searchParams);
    }

    @Override
    public void updateByDepartmentType(DepartmentType departmentType) {
        if (departmentType == null || StringUtils
                .isAnyBlank(departmentType.getDepartmentTypeKey(), departmentType.getDepartmentTypeName())) {
            return;
        }

        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentTypeKey(departmentType.getDepartmentTypeKey());

        Department update = new Department();
        update.setDepartmentTypeName(departmentType.getDepartmentTypeName());

        mapper.updateByExampleSelective(update, getExample(searchParams));
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setParentsService(DepartmentParentsService parentsService) {
        this.parentsService = parentsService;
    }

    @Autowired
    public void setDepartmentTypeService(DepartmentTypeService departmentTypeService) {
        this.departmentTypeService = departmentTypeService;
    }

    @Autowired
    public void setDistributionService(DepartmentDutyDistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @Autowired
    public void setDepartmentAddSenderChannel(DepartmentAddSenderChannel departmentAddSenderChannel) {
        this.departmentAddSenderChannel = departmentAddSenderChannel;
    }

    @Autowired
    public void setDepartmentUpdateSenderChannel(DepartmentUpdateSenderChannel departmentUpdateSenderChannel) {
        this.departmentUpdateSenderChannel = departmentUpdateSenderChannel;
    }
}
