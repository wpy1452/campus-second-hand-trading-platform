package com.turing.flea.dao.impl;

import com.turing.flea.common.TradeStatus;
import com.turing.flea.dao.TradeDao;
import com.turing.flea.entity.Trade;

import java.util.List;

/**
 * TradeDao 的 JDBC 实现
 *
 * 负责人: 待分配
 * 写法: 见 UserDaoImpl 的类注释(同一套模板)
 * 每个函数的具体 SQL 见 dao/TradeDao 接口里的注释
 */
public class TradeDaoImpl implements TradeDao {

    @Override
    public int insert(Trade trade) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: TradeDaoImpl.insert 负责人: 待分配");
    }

    @Override
    public boolean updateStatus(int tradeId, TradeStatus status) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: TradeDaoImpl.updateStatus 负责人: 待分配");
    }

    @Override
    public Trade findById(int tradeId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: TradeDaoImpl.findById 负责人: 待分配");
    }

    @Override
    public Trade findActiveByGoodsId(int goodsId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: TradeDaoImpl.findActiveByGoodsId 负责人: 待分配");
    }

    @Override
    public List<Trade> findByUserId(int userId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: TradeDaoImpl.findByUserId 负责人: 待分配");
    }
}