package com.turing.flea.service;

import com.turing.flea.common.Session;
import com.turing.flea.dao.UserDao;
import com.turing.flea.dao.impl.UserDaoImpl;
import com.turing.flea.entity.User;

/**
 * 用户业务层: 注册 / 登录 / 个人信息修改 / 密码修改
 *
 * 负责人: xyz
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
     * 负责人: xyz
     * 功能: 登录
     *       1. 调用 userDao.findByAccount(account) 按账号查用户
     *       2. 查不到 -> 返回 null (view 提示: 账号或密码错误)
     *       3. 查到了但密码对不上 -> 返回 null (同样提示: 账号或密码错误, 不要提示"密码错"给外面的人看)
     *       4. 都对 -> 返回这个 user (view 会把它放进 Session)
     * 参数: account 账号; password 密码
     * 返回值: 登录成功的用户; 失败返回 null
     */
    public User login(String account, String password) {
        if (isBlank(account) || isBlank(password)) {
            return null;
        }
        User user = userDao.findByAccount(account);
        if (user == null) {
            return null;
        }
        return password.equals(user.getPassword()) ? user : null;
    }

    /**
     * 负责人: xyz
     * 功能: 注册
     *       1. 调用 userDao.existsAccount(account), 账号已存在 -> 返回 false (view 提示: 账号已存在)
     *       2. 不存在 -> 调用 userDao.insert(user), 返回是否成功
     *       3. 昵称没填时用账号当昵称(数据库里 nickname 不能为空)
     * 参数: user 注册界面填好的用户(account/password/nickname/contact)
     * 返回值: 注册成功返回 true; 账号已存在或插入失败返回 false
     */
    public boolean register(User user) {
        if (user == null || isBlank(user.getAccount()) || isBlank(user.getPassword())) {
            return false;
        }
        if (userDao.existsAccount(user.getAccount())) {
            return false;
        }
        if (isBlank(user.getNickname())) {
            user.setNickname(user.getAccount().trim());
        }
        return userDao.insert(user) > 0;
    }

    /**
     * 负责人: xyz
     * 功能: 按账号查用户 (聊天要按账号找聊天对象、加好友要按账号找人都用这个)
     * 参数: account 账号
     * 返回值: 查到的用户; 没有返回 null
     */
    public User findByAccount(String account) {
        if (isBlank(account)) {
            return null;
        }
        return userDao.findByAccount(account.trim());
    }

    /**
     * 负责人: xyz
     * 功能: 按id查用户 (界面要显示卖家/买家昵称时用)
     * 参数: id 用户id
     * 返回值: 查到的用户; 没有返回 null
     */
    public User getById(int id) {
        if (id <= 0) {
            return null;
        }
        return userDao.findById(id);
    }

    /**
     * 负责人: xyz
     * 功能: 修改个人信息(昵称、联系信息)
     *       1. 调用 userDao.update(user)
     *       2. 成功的话, 把 Session 里的当前用户也更新一下(否则界面上还是旧昵称)
     * 参数: user 里面要有 id / nickname / contact
     * 返回值: 修改成功返回 true, 否则 false
     */
    public boolean updateProfile(User user) {
        if (user == null || user.getId() <= 0 || isBlank(user.getNickname())) {
            return false;
        }
        if (!userDao.update(user)) {
            return false;
        }
        refreshSession(user);
        return true;
    }

    /**
     * 负责人: xyz
     * 功能: 修改个人信息 (updateProfile 的旧名字, 保留给已经按老注释写好的界面调用)
     * 参数: user 里面要有 id / nickname / contact
     * 返回值: 修改成功返回 true, 否则 false
     */
    public boolean updateInfo(User user) {
        return updateProfile(user);
    }

    /**
     * 负责人: xyz
     * 功能: 修改密码
     *       1. 调用 userDao.findById(userId) 拿到当前用户, 比对 oldPassword
     *       2. 原密码不对 -> 返回 false (view 提示: 原密码错误)
     *       3. 对 -> 调用 userDao.updatePassword(userId, newPassword)
     * 参数: userId 用户id; oldPassword 原密码; newPassword 新密码
     * 返回值: 修改成功返回 true, 原密码错误或失败返回 false
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        if (userId <= 0 || isBlank(oldPassword) || isBlank(newPassword)) {
            return false;
        }
        User user = userDao.findById(userId);
        if (user == null || !oldPassword.equals(user.getPassword())) {
            return false;
        }
        return userDao.updatePassword(userId, newPassword);
    }

    /**
     * 负责人: xyz
     * 功能: 判断账号是否已存在 (注册界面点注册时查, 也可以一边输入一边查)
     * 参数: account 账号
     * 返回值: 存在返回 true, 不存在返回 false
     */
    public boolean isAccountExists(String account) {
        return !isBlank(account) && userDao.existsAccount(account.trim());
    }

    /**
     * 负责人: xyz
     * 功能: 判断账号是否已存在 (isAccountExists 的旧名字, 两种叫法都能用)
     * 参数: account 账号
     * 返回值: 存在返回 true, 不存在返回 false
     */
    public boolean existsAccount(String account) {
        return isAccountExists(account);
    }

    /**
     * 负责人: xyz
     * 功能: 改完个人信息后, 把 Session 里存的那份也同步一下
     *       (只同步昵称和联系信息, 不动 id/账号/密码)
     * 参数: user 刚保存成功的用户
     * 返回值: 无
     */
    private void refreshSession(User user) {
        User current = Session.getCurrentUser();
        if (current != null && current.getId() == user.getId()) {
            current.setNickname(user.getNickname());
            current.setContact(user.getContact());
        }
    }

    /**
     * 负责人: xyz
     * 功能: 判断字符串是不是空的(trim 之后), 参数校验用小工具
     * 参数: text 要判断的字符串
     * 返回值: null/空串/只有空格 返回 true
     */
    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}