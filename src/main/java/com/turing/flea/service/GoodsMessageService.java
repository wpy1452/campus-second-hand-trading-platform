package com.turing.flea.service;

import com.turing.flea.common.Session;
import com.turing.flea.dao.GoodsMessageDao;
import com.turing.flea.dao.impl.GoodsMessageDaoImpl;
import com.turing.flea.entity.GoodsMessage;

import java.util.Collections;
import java.util.List;

/**
 * 留言业务层: 商品留言 / 查看留言列表 / 我的留言管理 (拓展功能)
 *
 * 负责人: 雨
 */
public class GoodsMessageService {

    /** 留言数据访问对象 */
    private GoodsMessageDao goodsMessageDao = new GoodsMessageDaoImpl();

    /**
     * 负责人: 雨
     * 功能: 发表留言 (商品详情界面 -> 留言输入框 + 留言按钮)
     *       1. 内容为空 -> 返回 false
     *       2. 封装 GoodsMessage(goodsId, userId, content), 调用 goodsMessageDao.insert(...)
     * 参数: goodsId 商品id; userId 留言人(当前登录用户); content 留言内容
     * 返回值: 留言成功返回 true, 否则 false
     */
    public boolean add(int goodsId, int userId, String content) {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        GoodsMessage message = new GoodsMessage();
        message.setGoodsId(goodsId);
        message.setUserId(userId);
        message.setContent(content.trim());

        return goodsMessageDao.insert(message) > 0;
    }

    /**
     * 负责人: 雨
     * 功能: 查某个商品的留言列表 (商品详情界面的留言区, 要显示留言人昵称)
     *       1. 调用 goodsMessageDao.findByGoodsId(goodsId)
     * 参数: goodsId 商品id
     * 返回值: 留言列表, 没有返回空集合
     */
    public List<GoodsMessage> listByGoods(int goodsId) {
        List<GoodsMessage> list = goodsMessageDao.findByGoodsId(goodsId);
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * 负责人: 雨
     * 功能: 查我的全部留言 (我的留言管理界面, 要显示是哪个商品的留言)
     *       1. 调用 goodsMessageDao.findByUserId(Session.currentUserId())
     * 参数: userId 用户id
     * 返回值: 留言列表, 没有返回空集合
     */
    public List<GoodsMessage> myMessages(int userId) {
        List<GoodsMessage> list = goodsMessageDao.findByUserId(userId);
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * 负责人: 雨
     * 功能: 删除留言 (只能删自己的留言)
     *       1. 用 findById 的方式或直接在 dao 里判断 user_id, 确认这条留言是当前用户的
     *       2. 调用 goodsMessageDao.deleteById(messageId)
     * 参数: messageId 留言id
     * 返回值: 删除成功返回 true, 不是自己的留言或失败返回 false
     */
    public boolean delete(int messageId) {
        List<GoodsMessage> mine = goodsMessageDao.findByUserId(Session.currentUserId());
        if (mine == null || mine.size() == 0 || mine.get(0).getUserId() != com.turing.flea.common.Session.currentUserId()) {
            return false;
        }
        return goodsMessageDao.deleteById(messageId);
    }
}