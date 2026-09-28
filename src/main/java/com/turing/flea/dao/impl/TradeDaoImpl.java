package com.turing.flea.dao.impl;

import com.turing.flea.common.TradeStatus;
import com.turing.flea.dao.TradeDao;
import com.turing.flea.entity.Trade;
import com.turing.flea.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * TradeDao 的 JDBC 实现
 * 负责人: 亦妄辰
 *
 * 每个方法都是同一套: 拿连接 -> 设参数 -> 执行 -> 关资源
 * 具体 SQL 见 dao/TradeDao 接口里每个方法的注释
 */
public class TradeDaoImpl implements TradeDao {

    @Override
    public int insert(Trade trade) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 加了 RETURN_GENERATED_KEYS 才能拿到数据库自动生成的 id
            ps = conn.prepareStatement(
                    "insert into trade(goods_id, buyer_id, seller_id, amount, status) values(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, trade.getGoodsId());
            ps.setInt(2, trade.getBuyerId());
            ps.setInt(3, trade.getSellerId());
            ps.setInt(4, trade.getAmount());
            ps.setInt(5, trade.getStatus().getCode());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    trade.setId(id);      // 把新 id 塞回对象里
                    return id;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return -1;
    }

    @Override
    public boolean updateStatus(int tradeId, TradeStatus status) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("update trade set status = ? where id = ?");
            ps.setInt(1, status.getCode());
            ps.setInt(2, tradeId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, ps, conn);
        }
    }

    @Override
    public Trade findById(int tradeId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 要显示商品标题, 所以 join 一下 goods 表
            ps = conn.prepareStatement(
                    "select t.*, g.title as goods_title from trade t join goods g on t.goods_id = g.id where t.id = ?");
            ps.setInt(1, tradeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return toTrade(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    @Override
    public Trade findActiveByGoodsId(int goodsId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // status 1=未支付 4=退货中, 这两个都算"这条交易还没结束"
            ps = conn.prepareStatement(
                    "select t.*, g.title as goods_title from trade t join goods g on t.goods_id = g.id "
                            + "where t.goods_id = ? and t.status in (1,4) order by t.id desc limit 1");
            ps.setInt(1, goodsId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return toTrade(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    @Override
    public List<Trade> findByUserId(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Trade> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            // 我买的(buyer) + 我卖的(seller) 都算我参与的交易
            ps = conn.prepareStatement(
                    "select t.*, g.title as goods_title from trade t join goods g on t.goods_id = g.id "
                            + "where t.buyer_id = ? or t.seller_id = ? order by t.create_time desc");
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(toTrade(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;    // 查不到就是空 list, 不要返回 null
    }

    // 把结果集里的一行装成一个 Trade 对象, 上面三个查询都要用
    private Trade toTrade(ResultSet rs) throws Exception {
        Trade t = new Trade();
        t.setId(rs.getInt("id"));
        t.setGoodsId(rs.getInt("goods_id"));
        t.setBuyerId(rs.getInt("buyer_id"));
        t.setSellerId(rs.getInt("seller_id"));
        t.setAmount(rs.getInt("amount"));
        t.setStatus(TradeStatus.of(rs.getInt("status")));
        t.setCreateTime(rs.getTimestamp("create_time"));
        t.setUpdateTime(rs.getTimestamp("update_time"));
        t.setGoodsTitle(rs.getString("goods_title"));
        return t;
    }
}
