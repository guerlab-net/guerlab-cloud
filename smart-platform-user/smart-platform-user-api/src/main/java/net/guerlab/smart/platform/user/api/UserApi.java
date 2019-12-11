package net.guerlab.smart.platform.user.api;

import net.guerlab.smart.platform.user.core.domain.TakeOfficeDataDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.web.result.ListObject;

import java.util.List;

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
     * 通过用户ID获取职位信息列表
     *
     * @param userId
     *         用户id
     * @return 职位信息列表
     */
    List<TakeOfficeDataDTO> getTakeOffice(Long userId);
}
