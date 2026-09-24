package com.turing.flea.service;

import com.turing.flea.common.GoodsStatus;
import com.turing.flea.dao.GoodsDao;
import com.turing.flea.dao.TradeDao;
import com.turing.flea.dao.impl.GoodsDaoImpl;
import com.turing.flea.dao.impl.TradeDaoImpl;
import com.turing.flea.entity.Goods;
import com.turing.flea.entity.Trade;

import java.util.List;

/**
 * 商品业务层: 浏览 / 搜索 / 详情 / 发布 / 修改 / 下架 / 删除 / 我的商品管理
 *
 * 负责人: 待分配
 *
 * 商品状态的流转规则(核心业务, 界面上不要自己改状态, 一律走这里):
 *   发布        -> 在售
 *   下架        -> 已下架
 *   删除        -> 数据库里真的删掉(有交易的商品不给删)
 *   创建交易    -> 已售出 (在 TradeService.create 里改)
 *   取消交易    -> 恢复 在售 (在 TradeService.cancel 里改)
 */
public class GoodsService {

    /** 商品数据访问对象 */
    private GoodsDao goodsDao = new GoodsDaoImpl();
    /** 交易数据访问对象: 删除商品前要判断有没有交易记录 */
    private TradeDao tradeDao = new TradeDaoImpl();

    /**
     * 负责人: 待分配
     * 功能: 查在售商品列表 (主窗口一打开就调用, 把结果加载到 JTable)
     *       1. 调用 goodsDao.findByStatus(GoodsStatus.SALE)
     * 参数: 无
     * 返回值: 在售商品列表, 没有返回空集合
     */
    public List<Goods> listOnSale() {
        throw new UnsupportedOperationException("待实现: GoodsService.listOnSale 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 关键词搜索商品(只搜在售的)
     *       1. 关键字为空 -> 等同于 listOnSale()
     *       2. 否则调用 goodsDao.searchOnSale(keyword)
     * 参数: keyword 搜索框里输入的关键字
     * 返回值: 匹配到的商品列表, 没有返回空集合
     */
    public List<Goods> search(String keyword) {
        throw new UnsupportedOperationException("待实现: GoodsService.search 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查商品详情 (商品详情界面用, 需要标题/描述/价格/图片/卖家昵称/状态)
     *       1. 调用 goodsDao.findById(goodsId)
     * 参数: goodsId 商品id
     * 返回值: 商品, 没有返回 null
     */
    public Goods detail(int goodsId) {
        throw new UnsupportedOperationException("待实现: GoodsService.detail 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 发布商品
     *       1. 把 goods.status 设成 GoodsStatus.SALE
     *       2. goods.sellerId 由界面填成当前登录用户(Session.currentUserId())
     *       3. 调用 goodsDao.insert(goods)
     * 参数: goods 发布界面填好的商品
     * 返回值: 新商品id; 失败返回 -1
     */
    public int publish(Goods goods) {
        throw new UnsupportedOperationException("待实现: GoodsService.publish 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 修改商品信息
     *       1. 判断这个商品是不是当前用户发布的(sellerId == Session.currentUserId()), 不是 -> 返回 false
     *       2. 调用 goodsDao.update(goods)
     * 参数: goods 里面要有 id 和被改的字段
     * 返回值: 修改成功返回 true, 否则 false
     */
    public boolean modify(Goods goods) {
        throw new UnsupportedOperationException("待实现: GoodsService.modify 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 下架商品 (我的商品管理 -> 下架)
     *       1. 校验商品是当前用户的, 且状态是在售(已售出的不能下架, 要让买家先取消交易)
     *       2. 调用 goodsDao.updateStatus(goodsId, GoodsStatus.OFF)
     * 参数: goodsId 商品id
     * 返回值: 下架成功返回 true, 否则 false
     */
    public boolean offShelf(int goodsId) {
        throw new UnsupportedOperationException("待实现: GoodsService.offShelf 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 删除商品 (我的商品管理 -> 删除)
     *       1. 校验商品是当前用户的
     *       2. 调用 tradeDao.findActiveByGoodsId(goodsId): 有没结束的交易(未支付/退货中) -> 返回 false, 不许删
     *       3. 没有 -> 调用 goodsDao.deleteById(goodsId)
     * 参数: goodsId 商品id
     * 返回值: 删除成功返回 true, 否则 false
     */
    public boolean delete(int goodsId) {
        throw new UnsupportedOperationException("待实现: GoodsService.delete 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查我的全部商品 (个人中心 -> 我的商品管理, 在售/已下架/已售出/待处理 都要显示出来)
     *       1. 调用 goodsDao.findBySellerId(Session.currentUserId())
     * 参数: sellerId 卖家id
     * 返回值: 商品列表, 没有返回空集合
     */
    public List<Goods> myGoods(int sellerId) {
        throw new UnsupportedOperationException("待实现: GoodsService.myGoods 负责人: 待分配");
    }
}