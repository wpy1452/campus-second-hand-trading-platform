package com.turing.flea.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 个人中心界面
 *
 * 对应需求: 用户昵称 / 账号 / 联系信息 / 我的商品JTable / 好友列表 / 修改信息按钮 / 修改密码按钮
 *   个人信息修改: 更新 user 表          密码修改: 校验原密码后更新
 *   我的商品管理: 下架 / 删除 / 修改     我的商品条目: 点击进入商品详情
 *
 * 负责人: 待分配
 */
public class ProfileView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 昵称, 显示当前登录用户的昵称 */
    private JLabel nicknameLabel;
    /** 账号 */
    private JLabel accountLabel;
    /** 联系信息 */
    private JLabel contactLabel;
    /** 我的商品表格 */
    private JTable myGoodsTable;
    /** 我的商品表格模型, 表头: 商品id / 标题 / 价格 / 状态 */
    private DefaultTableModel goodsTableModel;
    /** 好友列表 */
    private JList<String> friendList;
    /** 好友列表模型, 每个元素是好友昵称(账号) */
    private DefaultListModel<String> friendListModel;
    /** 下架按钮 */
    private JButton offShelfButton;
    /** 删除按钮 */
    private JButton deleteButton;
    /** 修改按钮 (进入修改商品界面) */
    private JButton editButton;
    /** 修改信息按钮 (弹 EditProfileDialog) */
    private JButton editInfoButton;
    /** 修改密码按钮 (弹 ChangePasswordDialog) */
    private JButton changePasswordButton;
    /** 添加好友按钮 (拓展功能, 弹 AddFriendDialog) */
    private JButton addFriendButton;
    /** 我的留言按钮 (拓展功能, 打开 MyMessageView) */
    private JButton myMessageButton;

    /**
     * 负责人: 待分配
     * 功能: 创建个人中心窗口, 初始化界面, 加载用户信息 / 我的商品 / 好友列表
     * 参数: 无
     * 返回值: 无
     */
    public ProfileView() {
        super("个人中心");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面, 并依次调用 loadUserInfo() / loadMyGoods() / loadFriends()
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 显示当前登录用户的昵称/账号/联系信息
     *       1. 从 Session.getCurrentUser() 取, 填到三个 Label 上
     * 参数: 无
     * 返回值: 无
     */
    public void loadUserInfo() {
        throw new UnsupportedOperationException("待实现: ProfileView.loadUserInfo 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 加载我的商品 (全部状态都要显示)
     *       1. 调用 GoodsService.myGoods(Session.currentUserId())
     *       2. 清空表格后逐行 addRow: 商品id / 标题 / 价格(元) / 状态中文
     * 参数: 无
     * 返回值: 无
     */
    public void loadMyGoods() {
        throw new UnsupportedOperationException("待实现: ProfileView.loadMyGoods 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 加载好友列表(拓展)
     *       1. 调用 FriendService.listFriends(Session.currentUserId())
     *       2. 每个好友显示成 "昵称(账号)"
     * 参数: 无
     * 返回值: 无
     */
    public void loadFriends() {
        throw new UnsupportedOperationException("待实现: ProfileView.loadFriends 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击我的商品表格里某一行: new GoodsDetailView(商品id).setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onGoodsRowClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onGoodsRowClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【下架】
     *       1. 取表格选中行的商品id (没选中就提示"请先选择一件商品")
     *       2. JOptionPane 弹框确认"确定下架该商品吗?"
     *       3. 确认 -> 调用 GoodsService.offShelf(goodsId), 成功则 loadMyGoods() 刷新
     * 参数: 无
     * 返回值: 无
     */
    public void onOffShelfClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onOffShelfClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【删除】
     *       1. 取选中行的商品id, 弹框确认"确定删除该商品吗? 删除后不可恢复"
     *       2. 确认 -> 调用 GoodsService.delete(goodsId)
     *       3. true 刷新表格; false 弹提示"该商品有未完成的交易, 不能删除"
     * 参数: 无
     * 返回值: 无
     */
    public void onDeleteClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onDeleteClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【修改】: new EditGoodsView(商品id).setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onEditClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onEditClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【修改信息】: 弹出 EditProfileDialog, 关闭后重新 loadUserInfo()
     * 参数: 无
     * 返回值: 无
     */
    public void onEditInfoClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onEditInfoClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【修改密码】: 弹出 ChangePasswordDialog
     * 参数: 无
     * 返回值: 无
     */
    public void onChangePasswordClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onChangePasswordClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【添加好友】(拓展): 弹出 AddFriendDialog, 关闭后重新 loadFriends()
     * 参数: 无
     * 返回值: 无
     */
    public void onAddFriendClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onAddFriendClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【我的留言】(拓展): new MyMessageView().setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onMyMessageClick() {
        throw new UnsupportedOperationException("待实现: ProfileView.onMyMessageClick 负责人: 待分配");
    }
}