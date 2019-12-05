package net.guerlab.smart.platform.user.api;

import net.guerlab.smart.platform.user.core.domain.SimpleUserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.web.result.ListObject;

import java.util.List;

/**
 * 简单用户服务接口
 *
 * @author guer
 */
public interface SimpleUserApi {

    /**
     * 根据用户id查询用户
     *
     * @param userId
     *         用户id
     * @return 用户
     */
    SimpleUserDTO findOne(Long userId);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    ListObject<SimpleUserDTO> findList(UserSearchParams searchParams);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    List<SimpleUserDTO> findAll(UserSearchParams searchParams);
}
