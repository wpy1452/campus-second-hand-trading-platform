-- ==========================================================================
-- 校园二手交易平台  建库建表脚本
-- 技术栈: MySQL + JDBC        字符集: utf8mb4 (昵称里可能有 emoji)
-- 用法: 在 Navicat / 命令行里整体执行本文件, 会生成 6 张表 + 测试数据
-- ==========================================================================

DROP DATABASE IF EXISTS flea_market;
CREATE DATABASE flea_market DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE flea_market;

-- --------------------------------------------------------------------------
-- 1. user 用户表: 注册 / 登录 / 个人信息修改 / 密码修改 都读写这张表
-- --------------------------------------------------------------------------
CREATE TABLE `user` (
  `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '用户id, 主键',
  `account`     VARCHAR(20)  NOT NULL                COMMENT '账号: 唯一, 长度<=10且不能有特殊字符(注册时校验)',
  `password`    VARCHAR(50)  NOT NULL                COMMENT '密码: 教学项目直接存明文',
  `nickname`    VARCHAR(20)  NOT NULL                COMMENT '昵称: 商品详情页/聊天窗口展示的名字',
  `contact`     VARCHAR(50)  NOT NULL DEFAULT ''     COMMENT '联系信息: 手机号/微信/QQ, 联系卖家时展示',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- --------------------------------------------------------------------------
-- 2. goods 商品表: 发布 / 修改 / 下架 / 删除 / 浏览 / 搜索 都读写这张表
-- --------------------------------------------------------------------------
CREATE TABLE `goods` (
  `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '商品id, 主键',
  `title`       VARCHAR(50)  NOT NULL                COMMENT '标题: 搜索时按这个字段模糊匹配',
  `description` VARCHAR(500) NOT NULL DEFAULT ''     COMMENT '描述',
  `price`       INT          NOT NULL DEFAULT 0      COMMENT '价格: 单位是分, 9.90元存 990 (不要用float/double, 会有精度误差)',
  `image_path`  VARCHAR(255) NOT NULL DEFAULT ''     COMMENT '图片路径: 图片在本机上的路径, 界面用 new ImageIcon(path) 显示',
  `seller_id`   INT          NOT NULL                COMMENT '卖家id -> user.id',
  `status`      TINYINT      NOT NULL DEFAULT 1      COMMENT '商品状态: 1在售 2已售出 3已下架 4待处理/草稿',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_seller` (`seller_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- --------------------------------------------------------------------------
-- 3. goods_message 商品留言表: 留言模块(商品留言 / 我的留言管理)
-- --------------------------------------------------------------------------
CREATE TABLE `goods_message` (
  `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `goods_id`    INT          NOT NULL                COMMENT '商品id -> goods.id',
  `user_id`     INT          NOT NULL                COMMENT '留言人id -> user.id',
  `content`     VARCHAR(300) NOT NULL                COMMENT '留言内容',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  PRIMARY KEY (`id`),
  KEY `idx_goods` (`goods_id`),
  KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品留言表';

-- --------------------------------------------------------------------------
-- 4. chat 聊天表: 聊天模块(单聊 / 监听消息 / 聊天记录 / 消息提示)
-- --------------------------------------------------------------------------
CREATE TABLE `chat` (
  `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `send_id`     INT          NOT NULL                COMMENT '发送者id -> user.id',
  `recv_id`     INT          NOT NULL                COMMENT '接收者id -> user.id',
  `content`     VARCHAR(500) NOT NULL                COMMENT '消息内容',
  `is_read`     TINYINT      NOT NULL DEFAULT 0      COMMENT '是否已读: 0未读 1已读 (聊天窗口每3秒轮询未读消息)',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `idx_recv_read` (`recv_id`, `is_read`),
  KEY `idx_pair` (`send_id`, `recv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天消息表';

-- --------------------------------------------------------------------------
-- 5. trade 交易表: 交易模块(创建 / 取消 / 完成 / 退货)
-- --------------------------------------------------------------------------
CREATE TABLE `trade` (
  `id`          INT      NOT NULL AUTO_INCREMENT COMMENT '交易id',
  `goods_id`    INT      NOT NULL                COMMENT '商品id -> goods.id',
  `buyer_id`    INT      NOT NULL                COMMENT '买家id -> user.id',
  `seller_id`   INT      NOT NULL                COMMENT '卖家id -> user.id',
  `amount`      INT      NOT NULL DEFAULT 0      COMMENT '交易金额: 单位是分, 创建交易时从 goods.price 抄过来',
  `status`      TINYINT  NOT NULL DEFAULT 1      COMMENT '交易状态: 1未支付 2已完成 3已取消 4退货中 5已退货',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '状态变更时间',
  PRIMARY KEY (`id`),
  KEY `idx_goods` (`goods_id`),
  KEY `idx_buyer` (`buyer_id`),
  KEY `idx_seller` (`seller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易表';

-- --------------------------------------------------------------------------
-- 6. friend 好友表: 好友添加 / 好友列表 (拓展功能)
-- --------------------------------------------------------------------------
CREATE TABLE `friend` (
  `id`          INT      NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id`     INT      NOT NULL                COMMENT '用户id -> user.id',
  `friend_id`   INT      NOT NULL                COMMENT '好友id -> user.id',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pair` (`user_id`, `friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友关系表';

-- ==========================================================================
-- 测试数据: 方便组员联调, 两个账号的密码都是 123456
-- ==========================================================================
INSERT INTO `user` (`account`, `password`, `nickname`, `contact`) VALUES
('xiaoming', '123456', '小明', '13800000001'),
('xiaohong', '123456', '小红', 'wechat: xiaohong');

INSERT INTO `goods` (`title`, `description`, `price`, `image_path`, `seller_id`, `status`) VALUES
('九成新自行车', '搬宿舍急出, 车况良好', 15000, '', 1, 1),
('考研数学全套', '带笔记, 可小刀', 3000, '', 1, 1),
('小台灯', '用了半年, 一切正常', 1500, '', 2, 1);