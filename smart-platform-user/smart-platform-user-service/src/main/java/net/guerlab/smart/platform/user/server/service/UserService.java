package net.guerlab.smart.platform.user.server.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.server.entity.User;
import net.guerlab.web.result.ListObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;

/**
 * 用户服务
 *
 * @author guer
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * 默认头像地址
     */
    String DEFAULT_AVATAR = "/avatar/default.png";

    /**
     * 查询分页数据
     *
     * @param searchParams
     *         搜索条件
     * @return 用户列表
     */
    ListObject<User> queryPage(UserSearchParams searchParams);

    /**
     * 查询全部数据
     *
     * @param searchParams
     *         搜索条件
     * @return 用户列表
     */
    Collection<User> queryAll(UserSearchParams searchParams);

    /**
     * 通过用户名查询用户
     *
     * @param username
     *         用户名
     * @return 用户
     */
    default User findByUsername(String username) {
        String value = StringUtils.trimToNull(username);
        if (value == null) {
            return null;
        }

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUsername(value);

        return selectOne(searchParams);
    }

    /**
     * 通过用户名查询用户
     *
     * @param username
     *         用户名
     * @return 用户
     */
    default Optional<User> findByUsernameOptional(String username) {
        String value = StringUtils.trimToNull(username);
        if (value == null) {
            return Optional.empty();
        }

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUsername(value);

        return selectOneOptional(searchParams);
    }

    /**
     * 修改密码
     *
     * @param userId
     *         用户ID
     * @param newPassword
     *         新密码
     */
    void updatePassword(Long userId, String newPassword);

    /**
     * 检查密码是否错误
     *
     * @param user
     *         用户
     * @param password
     *         密码
     * @return 密码是否错误
     */
    boolean checkPasswordError(User user, String password);

    /**
     * 根据用户id获取权限列表
     *
     * @param userId
     *         用户id
     * @return 权限列表
     */
    Collection<String> getPermissionKeys(Long userId);

    /**
     * 判断是否是管理员
     *
     * @param userId
     *         用户id
     * @return 是否是管理员
     */
    boolean isAdmin(Long userId);

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }
}
