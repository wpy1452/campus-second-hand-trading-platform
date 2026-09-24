package com.turing.flea.dao;

import com.turing.flea.entity.GoodsMessage;

import java.util.List;

/**
 * 商品留言表 goods_message 的数据访问接口 (数据访问层 dao)
 *
 * 负责人: 待分配
 */
public interface GoodsMessageDao {

    /**
     * 负责人: 待分配
     * 功能: 新增一条留言 (商品详情页的留言区)
     *       SQL: insert into goods_message(goods_id, user_id, content) values(?,?,?)
     * 参数: message 要新增的留言
     * 返回值: 新增留言的id; 失败返回 -1
     */
    int insert(GoodsMessage message);

    /**
     * 负责人: 待分配
     * 功能: 查某个商品的全部留言(商品详情页的留言区), 要 join user 表查留言人昵称
     *       SQL: select m.*, u.nickname from goods_message m join user u on m.user_id = u.id
     *            where m.goods_id = ? order by m.create_time asc
     * 参数: goodsId 商品id
     * 返回值: 留言列表(按时间正序), 没有返回空集合
     */
    List<GoodsMessage> findByGoodsId(int goodsId);

    /**
     * 负责人: 待分配
     * 功能: 查某个用户的全部留言(我的留言管理), 要 join goods 表查商品标题
     *       SQL: select m.*, g.title from goods_message m join goods g on m.goods_id = g.id
     *            where m.user_id = ? order by m.create_time desc
     * 参数: userId 用户id
     * 返回值: 留言列表(按时间倒序), 没有返回空集合
     */
    List<GoodsMessage> findByUserId(int userId);

    /**
     * 负责人: 待分配
     * 功能: 删除一条留言 (我的留言管理里点删除)
     *       SQL: delete from goods_message where id = ?
     * 参数: messageId 留言id
     * 返回值: 删除成功返回 true, 否则 false
     */
    boolean deleteById(int messageId);
}