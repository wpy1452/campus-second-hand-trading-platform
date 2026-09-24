package com.turing.flea.dao;

import com.turing.flea.entity.User;

/**
 * 用户表 user 的数据访问接口 (数据访问层 dao)
 *
 * 这一层只做一件事: 用 JDBC 读写数据库, 把 ResultSet 变成 User 对象。
 * 【不要】在这一层做业务判断(比如"账号已存在就不能注册"), 那是 service 层的事。
 *
 * 实现要求: 用 PreparedStatement, 不要拼字符串(防止 SQL 注入)
 * 统一模板见 util/DBUtil 的类注释
 *
 * 负责人: 待分配
 */
public interface UserDao {

    /**
     * 负责人: 待分配
     * 功能: 新增一个用户 (注册)
     *       SQL: insert into user(account, password, nickname, contact) values(?,?,?,?)
     *       插入成功后把数据库生成的自增主键 set 回 user 对象
     * 参数: user 要新增的用户(不需要填 id)
     * 返回值: 新增用户的id; 失败返回 -1
     */
    int insert(User user);

    /**
     * 负责人: 待分配
     * 功能: 判断账号是否已经存在
     *       SQL: select count(*) from user where account = ?
     * 参数: account 账号
     * 返回值: 已存在返回 true, 不存在返回 false
     */
    boolean existsAccount(String account);

    /**
     * 负责人: 待分配
     * 功能: 按账号查用户 (登录时用)
     *       SQL: select * from user where account = ?
     * 参数: account 账号
     * 返回值: 查到的用户; 没有这个账号返回 null
     */
    User findByAccount(String account);

    /**
     * 负责人: 待分配
     * 功能: 按id查用户 (商品详情页显示卖家昵称/联系方式时用)
     *       SQL: select * from user where id = ?
     * 参数: id 用户id
     * 返回值: 查到的用户; 没有返回 null
     */
    User findById(int id);

    /**
     * 负责人: 待分配
     * 功能: 修改个人信息(昵称、联系信息)
     *       SQL: update user set nickname = ?, contact = ? where id = ?
     * 参数: user 里面要有 id / nickname / contact
     * 返回值: 影响行数>=1 返回 true, 否则 false
     */
    boolean updateInfo(User user);

    /**
     * 负责人: 待分配
     * 功能: 修改密码
     *       SQL: update user set password = ? where id = ?
     * 参数: userId 用户id; newPassword 新密码
     * 返回值: 修改成功返回 true, 否则 false
     */
    boolean updatePassword(int userId, String newPassword);
}