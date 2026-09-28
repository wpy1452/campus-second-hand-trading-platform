package com.turing.flea.dao.impl;

import com.turing.flea.dao.UserDao;
import com.turing.flea.entity.User;
import com.turing.flea.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * UserDao 的 JDBC 实现
 *
 * 负责人: xyz
 * 写法: 每个方法都是同一套模板 —— 拿连接 -> PreparedStatement 设参数 -> 执行 -> 处理结果 -> finally 关闭
 *   Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
 *   try { ... } catch (SQLException e) { e.printStackTrace(); } finally { DBUtil.close(rs, ps, conn); }
 * 页面上的提示由 view 负责, 这里只返回 成功/失败/对象/null
 */
public class UserDaoImpl implements UserDao {

    /**
     * 查询用户时统一用到的列。
     * 不写 select * 是为了让 ResultSet 的列固定下来, 表以后加字段也不影响这里。
     */
    private static final String COLUMNS = "id, account, password, nickname, contact, create_time";

    @Override
    public int insert(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // user 是 MySQL 的关键字, 表名要加反引号
            ps = conn.prepareStatement(
                    "insert into `user`(account, password, nickname, contact) values(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getAccount());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            ps.setString(4, user.getContact() == null ? "" : user.getContact());

            if (ps.executeUpdate() <= 0) {
                return -1;
            }
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user.setId(id);          // 把自增主键回填给调用方
                return id;
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }

    @Override
    public boolean existsAccount(String account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("select count(*) from `user` where account = ?");
            ps.setString(1, account);
            rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }

    @Override
    public User findByAccount(String account) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("select " + COLUMNS + " from `user` where account = ?");
            ps.setString(1, account);
            rs = ps.executeQuery();
            return rs.next() ? rsToUser(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }

    @Override
    public User findById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("select " + COLUMNS + " from `user` where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next() ? rsToUser(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }

    @Override
    public boolean update(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("update `user` set nickname = ?, contact = ? where id = ?");
            ps.setString(1, user.getNickname());
            ps.setString(2, user.getContact() == null ? "" : user.getContact());
            ps.setInt(3, user.getId());
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, ps, conn);
        }
    }

    @Override
    public boolean updateInfo(User user) {
        // 旧名字的入口, 实现在 update 里, 保证两种叫法行为一致
        return update(user);
    }

    @Override
    public boolean updatePassword(int userId, String newPassword) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("update `user` set password = ? where id = ?");
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, ps, conn);
        }
    }

    /**
     * 负责人: xyz
     * 功能: 把当前这一行 ResultSet 装成一个 User 对象 (查询方法公用, 免得每处都复制一遍)
     * 参数: rs 已经指向某一行的结果集
     * 返回值: 装好的 User
     */
    private User rsToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setAccount(rs.getString("account"));
        user.setPassword(rs.getString("password"));
        user.setNickname(rs.getString("nickname"));
        user.setContact(rs.getString("contact"));
        user.setCreateTime(rs.getTimestamp("create_time"));
        return user;
    }
}