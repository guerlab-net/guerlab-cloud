package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.entity.User;
import net.guerlab.web.result.ListObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 用户服务
 *
 * @author guer
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * 默认头像文件夹路径
     */
    String DEFAULT_AVATAR_PATH = "avatar";

    /**
     * 默认头像地址
     */
    String DEFAULT_AVATAR = "/" + DEFAULT_AVATAR_PATH + "/default.png";

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
    default User selectByUsername(String username) {
        String value = StringUtils.trimToNull(username);
        if (value == null) {
            return null;
        }

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUsername(value);

        return selectOne(searchParams);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param phone
     *         手机号码
     * @return 用户
     */
    default User selectByPhone(String phone) {
        String value = StringUtils.trimToNull(phone);
        if (value == null) {
            return null;
        }

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setPhone(value);

        return selectOne(searchParams);
    }

    /**
     * 通过邮箱地址查询用户
     *
     * @param email
     *         邮箱地址
     * @return 用户
     */
    default User selectByEmail(String email) {
        String value = StringUtils.trimToNull(email);
        if (value == null) {
            return null;
        }

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setEmail(value);

        return selectOne(searchParams);
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
     * 删除头像
     *
     * @param userId
     *         用户ID
     */
    void deleteAvatar(Long userId);

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
