# 校园二手交易平台

## 介绍

校园二手交易平台，提供一个轻量化的校园闲置物品流转渠道。平台支持学生注册登录、发布闲置商品、浏览与搜索商品、商品留言、买卖双方一对一聊天、创建与管理交易记录等核心功能。卖方可以管理自己发布的商品（下架、删除），买方可以查看订单状态并取消交易。

本项目是一个基于 Java Swing 的桌面应用程序，所有数据均保存在远程 MySQL 数据库中，无需依赖 Web 服务器。系统采用 C/S（客户端/服务器）两层架构，客户端与数据库通过 JDBC 直连，业务逻辑在客户端 Service 层完成，数据库负责数据持久化与并发控制。系统分为用户端与管理员端，用户端支持登录注册、商品发布与浏览、留言、聊天、交易管理

---

## 软件架构

### 架构说明

本项目采用经典的分层架构设计，代码位于 `src/main/java/com/turing/flea` 目录下，具体分层如下：

- **视图层（`com.turing.flea.view`）**：负责图形用户界面（GUI）的展示与交互，包含 `LoginView`（登录窗口）、`RegisterView`（注册窗口）、`MainView`（主窗口/商品列表）、`GoodsDetailView`（商品详情窗口）、`PublishGoodsView`（发布商品窗口）、`ChatView`（聊天窗口）、`TradeView`（交易窗口）、`ProfileView`（个人中心窗口）、`AddFriendDialog`（添加好友对话框）、`ChangePasswordDialog`（修改密码对话框）、`EditGoodsView`（编辑商品窗口）、`EditProfileDialog`（编辑个人信息对话框）、`MyMessageView`（我的留言窗口）、`MyTradeView`（我的交易窗口）等。

- **业务层（`com.turing.flea.service`）**：负责处理核心业务逻辑，包含 `UserService`（用户认证与注册）、`GoodsService`（商品管理）、`ChatService`（聊天消息收发与轮询）、`TradeService`（交易创建、取消与状态流转）、`GoodsMessageService`（商品留言管理）、`FriendService`（好友管理）等。业务层负责参数校验、权限判断、多表联动及事务边界控制。

- **数据访问层（`com.turing.flea.dao` + `com.turing.flea.dao.impl`）**：负责与底层数据存储进行交互。接口定义在 `dao` 包中（`UserDao`、`GoodsDao`、`ChatDao`、`TradeDao`、`GoodsMessageDao`、`FriendDao`），实现类位于 `dao.impl` 包中。每条方法对应一条 SQL，全部使用 `PreparedStatement` 预编译执行，防止 SQL 注入。

- **实体层（`com.turing.flea.entity`）**：对应系统业务实体，包含 `User`（用户）、`Goods`（商品）、`Chat`（聊天消息）、`Trade`（交易）、`GoodsMessage`（商品留言）、`Friend`（好友关系）等 POJO 类。

- **工具层（`com.turing.flea.util`）**：提供系统通用工具，包含 `DBUtil`（数据库连接获取与资源关闭）、`SessionUtil`（全局会话管理，保存当前登录用户信息）。

- **公共层（`com.turing.flea.common`）**：存放常量枚举与通用返回结果等公共类。

- **程序入口（`com.turing.flea.Main`）**：系统启动类，负责初始化并拉起登录界面。

> 注：本系统为 C/S 桌面应用，全局会话管理由 `SessionUtil` 静态变量实现，而非 Web 应用中的 `HttpSession`。

### 技术栈

| 技术    | 说明                                       | 版本      |
| ------- | ------------------------------------------ | --------- |
| Java SE | 核心语言，负责业务逻辑与集合/IO/多线程处理 | JDK 8+    |
| Swing   | 桌面 GUI 窗口开发                          | JDK 内置  |
| JDBC    | 数据库连接与操作                           | —         |
| MySQL   | 数据持久化                                 | 5.7 / 8.0 |
| Maven   | 项目构建与依赖管理                         | 3.6+      |
| Git     | 团队协作与版本控制                         | —         |

### 目录结构

```text
campus-second-hand-trading-platform
├── documents/                  # 项目开发相关文档
├── sql/                        # 数据库建表脚本（init.sql）
├── src/main/java/com/turing/flea/
│   ├── Main.java               # 程序入口
│   ├── common/                 # 常量与枚举
│   ├── dao/                    # 数据访问层接口
│   │   └── impl/               # DAO 接口实现类
│   ├── entity/                 # 实体类
│   ├── service/                # 业务逻辑层
│   ├── util/                   # 工具类（DBUtil、SessionUtil）
│   └── view/                   # Swing 界面层
├── src/main/resources/
│   ├── db.properties           # 数据库连接配置
│   └── sql/init.sql            # 建库建表脚本
├── pom.xml                     # Maven 依赖配置
└── README.md
```

------

## 安装教程

### 1. 环境准备

| 工具  | 版本要求            | 说明     |
| :---- | :------------------ | :------- |
| JDK   | 8 或 11 及以上      | 运行环境 |
| Maven | 3.6+                | 项目构建 |
| MySQL | 5.7 或 8.0          | 数据存储 |
| IDEA  | 2020+（社区版即可） | 开发工具 |
| Git   | 2.x                 | 版本控制 |

### 2. 克隆项目

```bash
git https://github.com/wpy1452/campus-second-hand-trading-platform.git
```



### 3. 数据库

本项目使用远程数据库，不需要配置直接可以使用

### 5. 构建项目

bash

```
mvn clean package
```



构建成功后，在 `target/` 目录下生成包含所有依赖的可执行 JAR 文件。

### 6. 运行程序

bash

```
java -jar target/campus-second-hand-trading-platform-1.0-SNAPSHOT.jar
```



或直接在 IDEA 中运行 `com.turing.flea.Main` 类。

------

## 使用说明

### 1. 启动系统

运行 `Main` 类后，系统弹出 `LoginView` 登录界面。

### 2. 用户注册与登录

- **注册**：填写账号、密码、昵称、联系方式，系统校验账号是否重复，不重复则封装为 `User` 对象存入 `user` 表，注册成功后跳转登录界面。
- **登录**：输入账号密码，系统通过 `UserService.login()` 进行校验。验证成功后，`SessionUtil.setCurrentUser(user)` 保存当前登录状态并跳转至 `MainView` 主界面。

### 3. 浏览与搜索商品

在 `MainView` 中，系统读取 `goods` 表中在售商品（状态为 0），加载到 `JTable` 展示。输入关键词后点击搜索按钮，系统对商品标题执行 `LIKE` 模糊查询，展示匹配结果。

### 4. 发布商品

点击“发布商品”按钮进入 `PublishGoodsView`，填写标题、描述、价格，选择图片路径。系统做非空与价格校验后，封装为 `Goods` 对象（状态为在售），通过 `GoodsService.publish()` 插入 `goods` 表，并刷新主窗口商品列表。

### 5. 商品详情与留言

双击商品条目打开 `GoodsDetailView`，展示商品标题、描述、价格、图片、卖家昵称及状态。下方留言区加载该商品的留言列表。输入留言内容后点击提交，系统封装为 `GoodsMessage` 对象插入 `message` 表，并刷新留言区。

### 7. 创建与管理交易

在商品详情页点击“立即购买”创建交易记录。系统封装 `Trade` 对象，通过 `TradeService.create()` 开启 JDBC 事务：先插入 `trade` 表（状态为未支付），再执行原子更新 `UPDATE goods SET status = 1 WHERE id = ? AND status = 0`，判断返回值防止超卖。买家可在 `MyTradeView` 中查看订单状态并支持取消交易，取消时恢复商品状态为在售。

### 8. 个人中心

在 `ProfileView` 中查看个人资料、我的商品列表（支持在售、已售出、已下架状态筛选），并支持修改个人信息、修改密码、下架商品和删除商品。

------

## 核心业务流程图

text

```
登录流程：LoginView → UserService.login() → UserDao.findByUsername() → 密码比对 → SessionUtil.setCurrentUser() → MainView

发布商品：PublishGoodsView → 校验 → GoodsService.publish() → GoodsDao.insert() → 刷新主窗口

浏览商品：MainView → GoodsService.listOnSale() → GoodsDao.findAllOnSale() → JTable 填充

商品搜索：MainView → GoodsService.search(keyword) → GoodsDao.search() → 刷新 JTable

留言：GoodsDetailView → GoodsMessageService.addMessage() → GoodsMessageDao.insert() → 刷新留言区

聊天发送：ChatView → ChatService.send() → ChatDao.insert()
聊天接收：ChatView 定时器每 3 秒 → ChatService.pollNewMessages() → ChatDao.findUnread() + markRead() → 追加显示

交易创建：GoodsDetailView → TradeService.create() → [事务] TradeDao.insert() + GoodsDao.updateStatus(SOLD) → TradeView
交易取消：TradeView → TradeService.cancel() → [事务] TradeDao.updateStatus(CANCELLED) + GoodsDao.updateStatus(ON_SALE)
```



------

## 开发团队

本项目为 10 人团队协作完成的课程设计项目，采用前后端并行开发模式，通过 Git 分支管理与 Pull Request 流程进行代码整合。

| 角色     | 人数 | 职责                                                        |
| :------- | :--- | :---------------------------------------------------------- |
| 组长     | 1 人 | 架构设计、DBUtil/SessionUtil、交易事务、代码合并            |
| 副组长   | 1 人 | 前端架构、MainView、ChatView、公共组件                      |
| 技术官   | 4 人 | DAO 层与 Service 层开发（用户、商品、留言、聊天、交易）     |
| 产品经理 | 4 人 | Swing 界面开发（登录/注册、发布/详情、个人中心、交易/管理） |

------

## 二期规划

- 引入 Servlet/Socket 作为中间件服务端，将业务逻辑从客户端剥离，实现三层 C/S 架构
- 商品图片上传至 OSS 图床，数据库仅存储 URL
- 交易完成后支持买家评价卖家
- 商品收藏与分类筛选
- 密码加密存储（BCrypt 加盐哈希）
