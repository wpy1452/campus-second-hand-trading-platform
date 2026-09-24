package com.turing.flea.service;

import com.turing.flea.dao.UserDao;
import com.turing.flea.dao.impl.UserDaoImpl;
import com.turing.flea.entity.User;

/**
 * 用户业务层: 注册 / 登录 / 个人信息修改 / 密码修改
 *
 * 负责人: 待分配
 *
 * service 层的规矩(全组统一):
 *   1. 只做业务判断, 【不和用户交互】: 不接收界面输入、不弹窗、不 System.out.print
 *      判断结果用返回值告诉 view, 由 view 决定弹什么提示文字
 *   2. 需要读写数据库时调用 dao 层, 不直接写 SQL
 *   3. 界面上的格式校验归 view (比如"账号长度不能超过10位、不能有特殊字符"),
 *      跟数据有关的判断归 service (比如"账号是否已存在、密码是否正确")
 */
public class UserService {

    /** 用户数据访问对象 */
    private UserDao userDao = new UserDaoImpl();

    /**
     * 负责人: 待分配
     * 功能: 登录
     *       1. 调用 userDao.findByAccount(account) 按账号查用户
     *       2. 查不到 -> 返回 null (view 提示: 账号或密码错误)
     *       3. 查到了 but 密码对不上 -> 返回 null (同样提示: 账号或密码错误, 不要提示"密码错"给外面的人看)
     *       4. 都对 -> 返回这个 user (view 会把它放进 Session)
     * 参数: account 账号; password 密码
     * 返回值: 登录成功的用户; 失败返回 null
     */
    public User login(String account, String password) {
        throw new UnsupportedOperationException("待实现: UserService.login 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 注册
     *       1. 调用 userDao.existsAccount(account), 账号已存在 -> 返回 false (view 提示: 账号已存在)
     *       2. 不存在 -> 调用 userDao.insert(user), 返回是否成功
     * 参数: user 注册界面填好的用户(account/password/nickname/contact)
     * 返回值: 注册成功返回 true; 账号已存在或插入失败返回 false
     */
    public boolean register(User user) {
        throw new UnsupportedOperationException("待实现: UserService.register 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 判断账号是否已存在 (注册界面可以一边输入一边查, 也可以点注册时查)
     * 参数: account 账号
     * 返回值: 存在返回 true, 不存在返回 false
     */
    public boolean existsAccount(String account) {
        throw new UnsupportedOperationException("待实现: UserService.existsAccount 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 修改个人信息(昵称、联系信息)
     *       1. 调用 userDao.updateInfo(user)
     *       2. 成功的话, 把 Session 里的当前用户也更新一下(否则界面上还是旧昵称)
     * 参数: user 里面要有 id / nickname / contact
     * 返回值: 修改成功返回 true, 否则 false
     */
    public boolean updateInfo(User user) {
        throw new UnsupportedOperationException("待实现: UserService.updateInfo 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 修改密码
     *       1. 调用 userDao.findById(userId) 拿到当前用户, 比对 oldPassword
     *       2. 原密码不对 -> 返回 false (view 提示: 原密码错误)
     *       3. 对 -> 调用 userDao.updatePassword(userId, newPassword)
     * 参数: userId 用户id; oldPassword 原密码; newPassword 新密码
     * 返回值: 修改成功返回 true, 原密码错误或失败返回 false
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("待实现: UserService.changePassword 负责人: 待分配");
    }
}