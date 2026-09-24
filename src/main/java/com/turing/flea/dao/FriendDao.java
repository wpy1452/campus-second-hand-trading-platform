package com.turing.flea.dao;

import com.turing.flea.entity.User;

import java.util.List;

/**
 * 好友表 friend 的数据访问接口 (数据访问层 dao) —— 拓展功能
 *
 * 负责人: 待分配
 */
public interface FriendDao {

    /**
     * 负责人: 待分配
     * 功能: 新增一条好友关系
     *       SQL: insert into friend(user_id, friend_id) values(?,?)
     *       注意: 加好友要插两条 (A->B, B->A), 由 FriendService 调用两次本方法
     * 参数: userId 我; friendId 好友
     * 返回值: 插入成功返回 true; 已经加过(唯一键冲突)返回 false
     */
    boolean insert(int userId, int friendId);

    /**
     * 负责人: 待分配
     * 功能: 判断两个人是不是已经是好友
     *       SQL: select count(*) from friend where user_id = ? and friend_id = ?
     * 参数: userId 我; friendId 对方
     * 返回值: 是好友返回 true, 否则 false
     */
    boolean exists(int userId, int friendId);

    /**
     * 负责人: 待分配
     * 功能: 查我的好友列表 (个人中心里的好友列表)
     *       SQL: select u.* from friend f join user u on f.friend_id = u.id where f.user_id = ?
     * 参数: userId 我的用户id
     * 返回值: 好友用户列表, 没有返回空集合
     */
    List<User> findFriends(int userId);
}