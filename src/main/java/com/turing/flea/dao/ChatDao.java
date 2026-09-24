package com.turing.flea.dao;

import com.turing.flea.entity.Chat;

import java.util.List;

/**
 * 聊天表 chat 的数据访问接口 (数据访问层 dao)
 *
 * 说明: 聊天是"每3秒轮询数据库", 不用 Socket, 所以这个接口的任务就是查/写 chat 表。
 *       A 和 B 之间的聊天记录查询条件要写双向:
 *       (send_id = ? and recv_id = ?) or (send_id = ? and recv_id = ?)
 *
 * 负责人: 待分配
 */
public interface ChatDao {

    /**
     * 负责人: 待分配
     * 功能: 新增一条消息 (点"发送")
     *       SQL: insert into chat(send_id, recv_id, content) values(?,?,?)
     * 参数: chat 要发送的消息(is_read 默认 false)
     * 返回值: 新增消息的id; 失败返回 -1
     */
    int insert(Chat chat);

    /**
     * 负责人: 待分配
     * 功能: 查两个人的聊天记录 (聊天记录区)
     *       SQL: select * from chat
     *            where (send_id = ? and recv_id = ?) or (send_id = ? and recv_id = ?)
     *            order by create_time asc
     * 参数: userA 一方id; userB 另一方id
     * 返回值: 消息列表(按时间正序), 没有返回空集合
     */
    List<Chat> findHistory(int userA, int userB);

    /**
     * 负责人: 待分配
     * 功能: 查"发给我的未读消息" (聊天窗口每3秒调用一次, 就是 Swing Timer 的轮询)
     *       SQL: select c.*, u.nickname from chat c join user u on c.send_id = u.id
     *            where c.recv_id = ? and c.is_read = 0 order by c.create_time asc
     * 参数: recvId 我的用户id
     * 返回值: 未读消息列表, 没有返回空集合
     */
    List<Chat> findUnread(int recvId);

    /**
     * 负责人: 待分配
     * 功能: 把某个人发给我的消息标记成已读 (显示完聊天记录后调用, 否则会一直重复轮询出来)
     *       SQL: update chat set is_read = 1 where recv_id = ? and send_id = ? and is_read = 0
     * 参数: recvId 我的用户id; sendId 对方id
     * 返回值: 影响行数>=1 返回 true, 否则 false
     */
    boolean markRead(int recvId, int sendId);

    /**
     * 负责人: 待分配
     * 功能: 统计我有多少条未读消息 (主窗口的"消息提示": 聊天框没打开时显示新消息提示)
     *       SQL: select count(*) from chat where recv_id = ? and is_read = 0
     * 参数: recvId 我的用户id
     * 返回值: 未读条数, 没有返回 0
     */
    int countUnread(int recvId);
}