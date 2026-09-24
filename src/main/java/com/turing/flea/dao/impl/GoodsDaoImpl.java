package com.turing.flea.dao.impl;

import com.turing.flea.common.GoodsStatus;
import com.turing.flea.dao.GoodsDao;
import com.turing.flea.entity.Goods;

import java.util.List;

/**
 * GoodsDao 的 JDBC 实现
 *
 * 负责人: 待分配
 * 写法: 见 UserDaoImpl 的类注释(同一套模板)
 * 注意: 需要卖家昵称的方法, SQL 要 join user 表, 并把 nickname 填到 goods.setSellerNickname(...)
 *       列表查询用 while(rs.next()) 逐个装进 List<Goods>, 一条都没有时返回 new ArrayList<>() 而不是 null
 * 每个函数的具体 SQL 见 dao/GoodsDao 接口里的注释
 */
public class GoodsDaoImpl implements GoodsDao {

    @Override
    public int insert(Goods goods) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.insert 负责人: 待分配");
    }

    @Override
    public boolean update(Goods goods) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.update 负责人: 待分配");
    }

    @Override
    public boolean updateStatus(int goodsId, GoodsStatus status) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.updateStatus 负责人: 待分配");
    }

    @Override
    public boolean deleteById(int goodsId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.deleteById 负责人: 待分配");
    }

    @Override
    public Goods findById(int goodsId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.findById 负责人: 待分配");
    }

    @Override
    public List<Goods> findByStatus(GoodsStatus status) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.findByStatus 负责人: 待分配");
    }

    @Override
    public List<Goods> findBySellerId(int sellerId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.findBySellerId 负责人: 待分配");
    }

    @Override
    public List<Goods> searchOnSale(String keyword) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsDaoImpl.searchOnSale 负责人: 待分配");
    }
}