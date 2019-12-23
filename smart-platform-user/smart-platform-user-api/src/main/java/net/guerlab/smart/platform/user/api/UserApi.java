package net.guerlab.smart.platform.user.api;

import net.guerlab.smart.platform.user.core.domain.PositionDataDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.domain.UserModifyDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.web.result.ListObject;

import java.util.List;
import java.util.Set;

/**
 * 用户服务接口
 *
 * @author guer
 */
public interface UserApi {

    /**
     * 根据用户id查询用户
     *
     * @param userId
     *         用户id
     * @return 用户
     */
    UserDTO findOne(Long userId);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    ListObject<UserDTO> findList(UserSearchParams searchParams);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    List<UserDTO> findAll(UserSearchParams searchParams);

    /**
     * 通过用户ID获取权限关键字列表
     *
     * @param userId
     *         用户id
     * @return 权限关键字列表
     */
    List<String> permissionKeys(Long userId);

    /**
     * 通过用户ID获取职务信息列表
     *
     * @param userId
     *         用户id
     * @return 职务信息列表
     */
    List<PositionDataDTO> getPosition(Long userId);

    /**
     * 通过用户ID获取职务信息关键字列表
     *
     * @param userId
     *         用户id
     * @return 职务信息关键字列表
     */
    Set<String> getPositionKeys(Long userId);

    /**
     * 添加用户
     *
     * @param user
     *         用户
     * @return 用户
     */
    UserDTO add(UserModifyDTO user);
}
