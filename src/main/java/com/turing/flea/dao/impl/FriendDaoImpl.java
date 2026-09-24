package com.turing.flea.dao.impl;

import com.turing.flea.dao.FriendDao;
import com.turing.flea.entity.User;

import java.util.List;

/**
 * FriendDao 的 JDBC 实现 —— 拓展功能
 *
 * 负责人: 待分配
 * 写法: 见 UserDaoImpl 的类注释(同一套模板)
 * 每个函数的具体 SQL 见 dao/FriendDao 接口里的注释
 */
public class FriendDaoImpl implements FriendDao {

    @Override
    public boolean insert(int userId, int friendId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: FriendDaoImpl.insert 负责人: 待分配");
    }

    @Override
    public boolean exists(int userId, int friendId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: FriendDaoImpl.exists 负责人: 待分配");
    }

    @Override
    public List<User> findFriends(int userId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: FriendDaoImpl.findFriends 负责人: 待分配");
    }
}