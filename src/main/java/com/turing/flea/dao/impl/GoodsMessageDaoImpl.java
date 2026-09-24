package com.turing.flea.dao.impl;

import com.turing.flea.dao.GoodsMessageDao;
import com.turing.flea.entity.GoodsMessage;

import java.util.List;

/**
 * GoodsMessageDao 的 JDBC 实现
 *
 * 负责人: 待分配
 * 写法: 见 UserDaoImpl 的类注释(同一套模板)
 * 每个函数的具体 SQL 见 dao/GoodsMessageDao 接口里的注释
 */
public class GoodsMessageDaoImpl implements GoodsMessageDao {

    @Override
    public int insert(GoodsMessage message) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.insert 负责人: 待分配");
    }

    @Override
    public List<GoodsMessage> findByGoodsId(int goodsId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.findByGoodsId 负责人: 待分配");
    }

    @Override
    public List<GoodsMessage> findByUserId(int userId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.findByUserId 负责人: 待分配");
    }

    @Override
    public boolean deleteById(int messageId) {
        // TODO 待实现 (负责人: 待分配)
        throw new UnsupportedOperationException("待实现: GoodsMessageDaoImpl.deleteById 负责人: 待分配");
    }
}