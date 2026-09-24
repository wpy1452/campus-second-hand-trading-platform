package com.turing.flea.dao.impl;

import com.turing.flea.dao.UserDao;
import com.turing.flea.entity.User;
import com.turing.flea.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * UserDao 的 JDBC 实现
 *
 * 负责人: 待分配
 * 写法: 每个方法都是同一套模板 —— 拿连接 -> PreparedStatement 设参数 -> 执行 -> 处理结果 -> finally 关闭
 *   Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
 *   try { ... } catch (SQLException e) { e.printStackTrace(); } finally { DBUtil.close(rs, ps, conn); }
 * 建议: 把 ResultSet -> User 的代码抽成一个私有方法 rsToUser(rs), 免得每个查询都复制一遍
 * 每个函数的具体 SQL 见 dao/UserDao 接口里的注释
 */
public class UserDaoImpl implements UserDao {

    @Override
    public int insert(User user) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: UserDaoImpl.insert 负责人: 待分配");
    }

    @Override
    public boolean existsAccount(String account) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: UserDaoImpl.existsAccount 负责人: 待分配");
    }

    @Override
    public User findByAccount(String account) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: UserDaoImpl.findByAccount 负责人: 待分配");
    }

    @Override
    public User findById(int id) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: UserDaoImpl.findById 负责人: 待分配");
    }

    @Override
    public boolean updateInfo(User user) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: UserDaoImpl.updateInfo 负责人: 待分配");
    }

    @Override
    public boolean updatePassword(int userId, String newPassword) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: UserDaoImpl.updatePassword 负责人: 待分配");
    }
}