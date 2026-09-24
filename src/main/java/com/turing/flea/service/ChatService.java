package com.turing.flea.service;

import com.turing.flea.dao.ChatDao;
import com.turing.flea.dao.impl.ChatDaoImpl;
import com.turing.flea.entity.Chat;

import java.util.List;

/**
 * 聊天业务层: 单聊 / 监听消息 / 发送消息 / 聊天记录 / 消息提示
 *
 * 负责人: 待分配
 *
 * 技术方案(不用 Socket, 只用 Swing Timer 轮询):
 *   聊天窗口打开时 new Timer(3000, ...) 每3秒调用一次 pollUnread(我),
 *   把新消息追加到聊天记录区, 然后调用 markRead 标记已读;
 *   聊天窗口没打开时, 主窗口也可以用 Timer 调 unreadCount(我) 决定要不要弹"你有新消息"
 */
public class ChatService {

    /** 聊天数据访问对象 */
    private ChatDao chatDao = new ChatDaoImpl();

    /**
     * 负责人: 待分配
     * 功能: 发送消息
     *       1. 内容为空 -> 返回 false
     *       2. 封装 Chat(sendId, recvId, content), 调用 chatDao.insert(...)
     * 参数: fromId 发送者id; toId 接收者id; content 消息内容
     * 返回值: 发送成功返回 true, 否则 false
     */
    public boolean send(int fromId, int toId, String content) {
        throw new UnsupportedOperationException("待实现: ChatService.send 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查两个人的聊天记录 (聊天窗口打开时先把历史记录显示出来)
     *       1. 调用 chatDao.findHistory(userA, userB)
     * 参数: userA 一方id; userB 另一方id
     * 返回值: 消息列表(按时间正序), 没有返回空集合
     */
    public List<Chat> history(int userA, int userB) {
        throw new UnsupportedOperationException("待实现: ChatService.history 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 拉取发给我的未读消息 (Swing Timer 每3秒调一次, 实现"监听消息")
     *       1. 调用 chatDao.findUnread(Session.currentUserId())
     * 参数: userId 我
     * 返回值: 未读消息列表, 没有返回空集合
     */
    public List<Chat> pollUnread(int userId) {
        throw new UnsupportedOperationException("待实现: ChatService.pollUnread 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 把对方发给我的消息标记成已读 (显示完就必须调用, 不然会一直被轮询出来重复显示)
     *       1. 调用 chatDao.markRead(Session.currentUserId(), otherId)
     * 参数: userId 我; otherId 聊天对象
     * 返回值: 标记成功返回 true, 否则 false
     */
    public boolean markRead(int userId, int otherId) {
        throw new UnsupportedOperationException("待实现: ChatService.markRead 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 我有多少条未读消息 (主窗口"消息提示": 聊天框没打开时提醒有新消息)
     *       1. 调用 chatDao.countUnread(Session.currentUserId())
     * 参数: userId 我
     * 返回值: 未读条数, 没有返回 0
     */
    public int unreadCount(int userId) {
        throw new UnsupportedOperationException("待实现: ChatService.unreadCount 负责人: 待分配");
    }
}