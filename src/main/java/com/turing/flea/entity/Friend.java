package com.turing.flea.entity;

import java.util.Date;

/**
 * 好友关系 (对应 friend 表) —— 拓展功能
 *
 * 数据的设计:
 *   id         主键
 *   userId     用户id -> user.id
 *   friendId   好友id -> user.id
 *   createTime 添加时间
 *
 * 补充说明:
 *   加好友时建议【双向】各插一条记录 (A->B 和 B->A), 这样查好友列表只要
 *   "select ... where user_id = ?" 一句 SQL, 不用写 or 判断
 *   (user_id, friend_id) 在数据库里是唯一键, 重复添加会插入失败, 业务层先查再加
 *
 * 负责人: 待分配
 */
public class Friend {

    /** 主键 */
    private int id;
    /** 用户id -> user.id */
    private int userId;
    /** 好友id -> user.id */
    private int friendId;
    /** 添加时间 */
    private Date createTime;

    /* ------------------- getter / setter (已写好, 直接调用) ------------------- */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getFriendId() { return friendId; }

    public void setFriendId(int friendId) { this.friendId = friendId; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}