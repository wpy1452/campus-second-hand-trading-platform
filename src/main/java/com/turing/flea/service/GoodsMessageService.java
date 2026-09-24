package com.turing.flea.service;

import com.turing.flea.dao.GoodsMessageDao;
import com.turing.flea.dao.impl.GoodsMessageDaoImpl;
import com.turing.flea.entity.GoodsMessage;

import java.util.List;

/**
 * 留言业务层: 商品留言 / 查看留言列表 / 我的留言管理 (拓展功能)
 *
 * 负责人: 待分配
 */
public class GoodsMessageService {

    /** 留言数据访问对象 */
    private GoodsMessageDao goodsMessageDao = new GoodsMessageDaoImpl();

    /**
     * 负责人: 待分配
     * 功能: 发表留言 (商品详情界面 -> 留言输入框 + 留言按钮)
     *       1. 内容为空 -> 返回 false
     *       2. 封装 GoodsMessage(goodsId, userId, content), 调用 goodsMessageDao.insert(...)
     * 参数: goodsId 商品id; userId 留言人(当前登录用户); content 留言内容
     * 返回值: 留言成功返回 true, 否则 false
     */
    public boolean add(int goodsId, int userId, String content) {
        throw new UnsupportedOperationException("待实现: GoodsMessageService.add 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查某个商品的留言列表 (商品详情界面的留言区, 要显示留言人昵称)
     *       1. 调用 goodsMessageDao.findByGoodsId(goodsId)
     * 参数: goodsId 商品id
     * 返回值: 留言列表, 没有返回空集合
     */
    public List<GoodsMessage> listByGoods(int goodsId) {
        throw new UnsupportedOperationException("待实现: GoodsMessageService.listByGoods 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查我的全部留言 (我的留言管理界面, 要显示是哪个商品的留言)
     *       1. 调用 goodsMessageDao.findByUserId(Session.currentUserId())
     * 参数: userId 用户id
     * 返回值: 留言列表, 没有返回空集合
     */
    public List<GoodsMessage> myMessages(int userId) {
        throw new UnsupportedOperationException("待实现: GoodsMessageService.myMessages 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 删除留言 (只能删自己的留言)
     *       1. 用 findById 的方式或直接在 dao 里判断 user_id, 确认这条留言是当前用户的
     *       2. 调用 goodsMessageDao.deleteById(messageId)
     * 参数: messageId 留言id
     * 返回值: 删除成功返回 true, 不是自己的留言或失败返回 false
     */
    public boolean delete(int messageId) {
        throw new UnsupportedOperationException("待实现: GoodsMessageService.delete 负责人: 待分配");
    }
}