package com.turing.flea.dao.impl;

import com.turing.flea.dao.GoodsMessageDao;
import com.turing.flea.entity.GoodsMessage;
import com.turing.flea.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GoodsMessageDao 的 JDBC 实现
 *
 * 负责人: 雨
 * 写法: 见 UserDaoImpl 的类注释(同一套模板)
 * 每个函数的具体 SQL 见 dao/GoodsMessageDao 接口里的注释
 */
public class GoodsMessageDaoImpl implements GoodsMessageDao {

    @Override
    public int insert(GoodsMessage message) {
        // TODO 待实现 (负责人: 雨)
        //throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.insert 负责人: 待分配  ");
        String sql = "insert into goods_message(goods_id, user_id, content) values(?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, message.getGoodsId());
            ps.setInt(2, message.getUserId());
            ps.setString(3, message.getContent());

            if (ps.executeUpdate() > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<GoodsMessage> findByGoodsId(int goodsId) {
        // TODO 待实现 (负责人: 雨)
        //throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.findByGoodsId 负责人: 待分配");
        String sql = "select m.*, u.nickname from goods_message m "
                + "join `user` u on m.user_id = u.id "
                + "where m.goods_id = ? order by m.create_time asc";

        List<GoodsMessage> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, goodsId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GoodsMessage m = new GoodsMessage();
                    m.setId(rs.getInt("id"));
                    m.setGoodsId(rs.getInt("goods_id"));
                    m.setUserId(rs.getInt("user_id"));
                    m.setContent(rs.getString("content"));
                    m.setCreateTime(rs.getTimestamp("create_time"));
                    m.setUserNickname(rs.getString("nickname"));
                    list.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<GoodsMessage> findByUserId(int userId) {
        // TODO 待实现 (负责人: 雨)
        //throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.findByUserId 负责人: 待分配");
        String sql = "select m.*, g.title from goods_message m "
        + "join goods g on m.goods_id = g.id "
                + "where m.user_id = ? order by m.create_time desc";

        List<GoodsMessage> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GoodsMessage m = new GoodsMessage();
                    m.setId(rs.getInt("id"));
                    m.setGoodsId(rs.getInt("goods_id"));
                    m.setUserId(rs.getInt("user_id"));
                    m.setContent(rs.getString("content"));
                    m.setCreateTime(rs.getTimestamp("create_time"));
                    m.setUserNickname(rs.getString("nickname"));
                    list.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteById(int messageId) {
        // TODO 待实现 (负责人: 雨)
        //throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.deleteById 负责人: 待分配");
        String sql = "delete from goods_message where id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, messageId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}