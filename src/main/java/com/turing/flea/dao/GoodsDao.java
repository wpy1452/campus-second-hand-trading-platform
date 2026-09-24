package com.turing.flea.dao;

import com.turing.flea.common.GoodsStatus;
import com.turing.flea.entity.Goods;

import java.util.List;

/**
 * 商品表 goods 的数据访问接口 (数据访问层 dao)
 *
 * 只负责读写 goods 表; "能不能下架""能不能删除"这类判断在 GoodsService 里做。
 * 需要卖家昵称的地方, 用 join 一次查出来, 填到 Goods.sellerNickname 里:
 *   select g.*, u.nickname as seller_nickname from goods g join user u on g.seller_id = u.id ...
 *
 * 负责人: 待分配
 */
public interface GoodsDao {

    /**
     * 负责人: 待分配
     * 功能: 新增商品 (发布商品)
     *       SQL: insert into goods(title, description, price, image_path, seller_id, status) values(?,?,?,?,?,?)
     *       插入成功后把自增主键 set 回 goods 对象
     * 参数: goods 要发布的商品(status 传 GoodsStatus.SALE)
     * 返回值: 新增商品的id; 失败返回 -1
     */
    int insert(Goods goods);

    /**
     * 负责人: 待分配
     * 功能: 修改商品信息(标题、描述、价格、图片)
     *       SQL: update goods set title=?, description=?, price=?, image_path=? where id=?
     * 参数: goods 里面要有 id 和被改的字段
     * 返回值: 修改成功返回 true, 否则 false
     */
    boolean update(Goods goods);

    /**
     * 负责人: 待分配
     * 功能: 只改商品状态 (下架/重新上架/卖出)
     *       SQL: update goods set status = ? where id = ?
     * 参数: goodsId 商品id; status 目标状态
     * 返回值: 修改成功返回 true, 否则 false
     */
    boolean updateStatus(int goodsId, GoodsStatus status);

    /**
     * 负责人: 待分配
     * 功能: 删除商品
     *       SQL: delete from goods where id = ?
     *       注意: 有交易记录的商品不要删, 这个判断在 GoodsService 里做
     * 参数: goodsId 商品id
     * 返回值: 删除成功返回 true, 否则 false
     */
    boolean deleteById(int goodsId);

    /**
     * 负责人: 待分配
     * 功能: 按id查商品(详情页), 要 join user 表把卖家昵称一起查出来
     *       SQL: select g.*, u.nickname as seller_nickname from goods g join user u on g.seller_id = u.id where g.id = ?
     * 参数: goodsId 商品id
     * 返回值: 商品, 没有返回 null
     */
    Goods findById(int goodsId);

    /**
     * 负责人: 待分配
     * 功能: 按状态查商品列表 (主窗口默认显示在售商品)
     *       SQL: select ... where g.status = ? order by g.create_time desc
     * 参数: status 商品状态(传 GoodsStatus.SALE)
     * 返回值: 商品列表, 一条都没有返回空集合(不要返回 null)
     */
    List<Goods> findByStatus(GoodsStatus status);

    /**
     * 负责人: 待分配
     * 功能: 查某个卖家的全部商品 (我的商品管理, 各种状态都要查出来)
     *       SQL: select ... where g.seller_id = ? order by g.create_time desc
     * 参数: sellerId 卖家id
     * 返回值: 商品列表, 没有返回空集合
     */
    List<Goods> findBySellerId(int sellerId);

    /**
     * 负责人: 待分配
     * 功能: 关键词搜索【在售】商品 (模糊匹配标题)
     *       SQL: select ... where g.status = 1 and (g.title like ? or g.description like ?)
     *       关键词要拼成 "%关键词%"
     * 参数: keyword 搜索框里输入的关键字
     * 返回值: 匹配到的商品列表, 没有返回空集合
     */
    List<Goods> searchOnSale(String keyword);
}