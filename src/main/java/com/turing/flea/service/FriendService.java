package com.turing.flea.service;

import com.turing.flea.dao.FriendDao;
import com.turing.flea.dao.UserDao;
import com.turing.flea.dao.impl.FriendDaoImpl;
import com.turing.flea.dao.impl.UserDaoImpl;
import com.turing.flea.entity.User;

import java.util.List;

/**
 * 好友业务层: 好友添加 / 好友列表 (拓展功能)
 *
 * 负责人: 待分配
 */
public class FriendService {

    /** 好友数据访问对象 */
    private FriendDao friendDao = new FriendDaoImpl();
    /** 用户数据访问对象: 通过账号找好友 */
    private UserDao userDao = new UserDaoImpl();

    /**
     * 负责人: 待分配
     * 功能: 添加好友 (个人中心 -> 添加好友, 输入对方账号)
     *       1. 调用 userDao.findByAccount(account), 没这个人 -> 返回 false
     *       2. 是自己 -> 返回 false
     *       3. 调用 friendDao.exists(我, 对方), 已经是好友 -> 返回 false
     *       4. 调用 friendDao.insert(我, 对方) 和 friendDao.insert(对方, 我) 插两条 (双向)
     * 参数: userId 我; friendAccount 对方账号
     * 返回值: 添加成功返回 true, 对方不存在/已是好友/失败返回 false
     */
    public boolean addFriend(int userId, String friendAccount) {
        throw new UnsupportedOperationException("待实现: FriendService.addFriend 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查我的好友列表 (个人中心右边的好友列表)
     *       1. 调用 friendDao.findFriends(userId)
     * 参数: userId 我
     * 返回值: 好友用户列表, 没有返回空集合
     */
    public List<User> listFriends(int userId) {
        throw new UnsupportedOperationException("待实现: FriendService.listFriends 负责人: 待分配");
    }
}