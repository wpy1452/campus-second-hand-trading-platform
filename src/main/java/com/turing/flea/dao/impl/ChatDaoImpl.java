package com.turing.flea.dao.impl;

import com.turing.flea.dao.ChatDao;
import com.turing.flea.entity.Chat;

import java.util.List;

/**
 * ChatDao 的 JDBC 实现
 *
 * 负责人: 待分配
 * 写法: 见 UserDaoImpl 的类注释(同一套模板)
 * 注意: 查两个人之间的聊天记录时, 两个参数要 set 四遍 (send_id/recv_id 各两次)
 * 每个函数的具体 SQL 见 dao/ChatDao 接口里的注释
 */
public class ChatDaoImpl implements ChatDao {

    @Override
    public int insert(Chat chat) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: ChatDaoImpl.insert 负责人: 待分配");
    }

    @Override
    public List<Chat> findHistory(int userA, int userB) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: ChatDaoImpl.findHistory 负责人: 待分配");
    }

    @Override
    public List<Chat> findUnread(int recvId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: ChatDaoImpl.findUnread 负责人: 待分配");
    }

    @Override
    public boolean markRead(int recvId, int sendId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: ChatDaoImpl.markRead 负责人: 待分配");
    }

    @Override
    public int countUnread(int recvId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: ChatDaoImpl.countUnread 负责人: 待分配");
    }
}