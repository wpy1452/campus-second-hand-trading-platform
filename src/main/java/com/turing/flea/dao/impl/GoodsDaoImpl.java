package com.turing.flea.dao.impl;

import com.turing.flea.common.GoodsStatus;
import com.turing.flea.dao.GoodsDao;
import com.turing.flea.entity.Goods;
import com.turing.flea.util.DBUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * GoodsDao 的 JDBC 实现
 *
 * 负责人: 久违
 * 写法: 见 UserDaoImpl 的类注释(同一套模板)
 * 注意: 需要卖家昵称的方法, SQL 要 join user 表, 并把 nickname 填到 goods.setSellerNickname(...)
 *       列表查询用 while(rs.next()) 逐个装进 List<Goods>, 一条都没有时返回 new ArrayList<>() 而不是 null
 * 每个函数的具体 SQL 见 dao/GoodsDao 接口里的注释
 */
public class GoodsDaoImpl implements GoodsDao {

    @Override
    public int insert(Goods goods) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.insert 负责人: 久违");
        String sql = "insert into goods(title, description, price, image_path, seller_id, status) values(?,?,?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, goods.getTitle());
            pstmt.setString(2, goods.getDescription());
            pstmt.setInt(3, goods.getPrice());
            pstmt.setString(4, goods.getImagePath());
            pstmt.setInt(5, goods.getSellerId());
            pstmt.setInt(6, goods.getStatus().getCode());

            int row = pstmt.executeUpdate();
            if (row > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);    // 取第一列自增主键
                    goods.setId(id);                     // 回填到对象
                    return id;                           // 返回新增 id
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return -1;
    }

    @Override
    public boolean update(Goods goods) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.update 负责人: 久违");
        String sql = "update goods set title=?, description=?, price=?, image_path=? where id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, goods.getTitle());
            pstmt.setString(2, goods.getDescription());
            pstmt.setInt(3, goods.getPrice());
            pstmt.setString(4, goods.getImagePath());
            pstmt.setInt(5, goods.getId());

            int row = pstmt.executeUpdate();
            if (row > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public boolean updateStatus(int goodsId, GoodsStatus status) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.updateStatus 负责人: 久违");
        String sql = "update goods set status = ? where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status.getCode());
            pstmt.setInt(2, goodsId);

            int row = pstmt.executeUpdate();
            if (row > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public boolean deleteById(int goodsId) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.deleteById 负责人: 久违");
        String sql = "delete from goods where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, goodsId);
            int row = pstmt.executeUpdate();
            if (row > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public Goods findById(int goodsId) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.findById 负责人: 久违");
        String sql = "select g.*, u.nickname as seller_nickname from goods g join user u on g.seller_id = u.id where g.id = ?";
        List<Goods> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, goodsId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowWithNickname(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return null;
    }

    @Override
    public List<Goods> findByStatus(GoodsStatus status) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.findByStatus 负责人: 久违");
        String sql = "select * from goods g where g.status = ? order by g.create_time desc";
        List<Goods> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status.getCode());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<Goods> findBySellerId(int sellerId) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.findBySellerId 负责人: 久违");
        String sql = "select * from goods g where g.seller_id = ? order by g.create_time desc";
        List<Goods> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sellerId);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<Goods> searchOnSale(String keyword) {
        // TODO 待实现 (负责人: 久违)
        //throw new UnsupportedOperationException("待实现: GoodsDaoImpl.searchOnSale 负责人: 久违");
        if(keyword==null){
            keyword="";
        }
        String sql = "select * from goods g where g.status = 1 and (g.title like ? or g.description like ?)";
        List<Goods> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return list;
    }

    /*
    * 负责人：久违
    * 功能：将rs获取的内容封装成goods对象，便于添加到list表中。
    * 参数：ResultSet rs
    * 返回值：goods商品对象
    * */
    private Goods mapRow(ResultSet rs) throws SQLException {
        Goods goods = new Goods();
        goods.setId(rs.getInt("id"));
        goods.setTitle(rs.getString("title"));
        goods.setDescription(rs.getString("description"));
        goods.setPrice(rs.getInt("price"));
        goods.setSellerId(rs.getInt("seller_id"));
        goods.setImagePath(rs.getString("image_path"));
        goods.setStatus(GoodsStatus.of(rs.getInt("status")));
        goods.setCreateTime(rs.getTimestamp("create_time"));
        goods.setUpdateTime(rs.getTimestamp("update_time"));
        return goods;
    }

    /*
     * 负责人：久违
     * 功能：将rs获取的内容封装成goods对象，便于添加到list表中。
     *      增加商家名的设置
     * 参数：ResultSet rs
     * 返回值：goods商品对象
     * */
    private Goods mapRowWithNickname(ResultSet rs) throws SQLException {
        Goods g = mapRow(rs);
        g.setSellerNickname(rs.getString("seller_nickname"));
        return g;
    }
}