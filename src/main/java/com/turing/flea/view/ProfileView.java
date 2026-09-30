package com.turing.flea.view;

import com.turing.flea.entity.Goods;
import com.turing.flea.entity.User;
import com.turing.flea.service.FriendService;
import com.turing.flea.service.GoodsService;
import com.turing.flea.common.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import com.turing.flea.common.GoodsStatus;


/**
 * 个人中心界面
 *
 * 对应需求: 用户昵称 / 账号 / 联系信息 / 我的商品JTable / 好友列表 / 修改信息按钮 / 修改密码按钮
 *   个人信息修改: 更新 user 表          密码修改: 校验原密码后更新
 *   我的商品管理: 下架 / 删除 / 修改     我的商品条目: 点击进入商品详情
 *
 * 负责人: 云
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
    public void initView() {// 1. 顶部：个人信息区
        nicknameLabel = new JLabel("昵称");
        accountLabel = new JLabel("账号");
        contactLabel = new JLabel("联系方式");
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("我的信息"));
        infoPanel.add(nicknameLabel);
        infoPanel.add(accountLabel);
        infoPanel.add(contactLabel);

        String[] columns = {"商品ID", "标题", "价格(元)", "状态"};
        goodsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        myGoodsTable = new JTable(goodsTableModel);
        JScrollPane goodsScroll = new JScrollPane(myGoodsTable);
        goodsScroll.setBorder(BorderFactory.createTitledBorder("我的商品"));

        offShelfButton = new JButton("下架");
        deleteButton = new JButton("删除");
        editButton = new JButton("修改");
        JPanel goodsBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        goodsBtnPanel.add(offShelfButton);
        goodsBtnPanel.add(deleteButton);
        goodsBtnPanel.add(editButton);

        friendListModel = new DefaultListModel<>();
        friendList = new JList<>(friendListModel);
        JScrollPane friendScroll = new JScrollPane(friendList);
        friendScroll.setBorder(BorderFactory.createTitledBorder("好友列表"));

        editInfoButton = new JButton("修改信息");
        changePasswordButton = new JButton("修改密码");
        addFriendButton = new JButton("添加好友");
        myMessageButton = new JButton("我的留言");
        JPanel functionPanel = new JPanel(new GridLayout(1, 4, 10, 5));
        functionPanel.add(editInfoButton);
        functionPanel.add(changePasswordButton);
        functionPanel.add(addFriendButton);
        functionPanel.add(myMessageButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(infoPanel, BorderLayout.NORTH);
        leftPanel.add(friendScroll, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(goodsScroll, BorderLayout.CENTER);
        rightPanel.add(goodsBtnPanel, BorderLayout.NORTH);
        rightPanel.add(functionPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        offShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOffShelfClick();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteClick();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditClick();
            }
        });
        editInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditInfoClick();
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onChangePasswordClick();
            }
        });
        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddFriendClick();
            }
        });
        myMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onMyMessageClick();
            }
        });
        myGoodsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onGoodsRowClick();
                }
            }
        });

        loadUserInfo();
        loadMyGoods();
        loadFriends();

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
        User user = Session.getCurrentUser();
        if (user == null) {
            System.out.println("当前无用户登陆");
            return;
        }
        nicknameLabel.setText("昵称：" + user.getNickname());
        accountLabel.setText("账号：" + user.getAccount());
        contactLabel.setText("联系方式：" + user.getContact());
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
        GoodsService service = new GoodsService();
        List<Goods> list = service.myGoods(Session.currentUserId());

        if (list == null){
            System.out.println("loadMyGoods:返回商品列表为空");
            return;
        }

        goodsTableModel.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            Goods g = list.get(i);
            goodsTableModel.addRow(new Object[]{
                    g.getId(),
                    g.getTitle(),
                    g.getPrice()/100.0,
                    statusToText(g.getStatus())
            });
        }
    }
    private String statusToText(GoodsStatus status) {
        if (status == GoodsStatus.SALE) {
            return "在售";
        } else if (status == GoodsStatus.SOLD) {
            return "已售出";
        } else if (status == GoodsStatus.OFF) {
            return "已下架";
        } else if (status == GoodsStatus.DRAFT) {
            return "待处理";
        }
        return "未知";
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
        friendListModel.clear();           // 先清空，保证 UI 可用
        try {
            FriendService service = new FriendService();
            List<User> list = service.listFriends(Session.currentUserId());
            if (list == null) {
                System.out.println("loadFriends:返回好友列表为空");
                return;
            }
            for (User f : list) {
                friendListModel.addElement(f.getNickname() + "(" + f.getAccount() + ")");
            }
        } catch (UnsupportedOperationException ex) {
            // 好友功能暂未实现，忽略即可，界面照常打开
            System.out.println("好友功能未实现，跳过加载: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            // 其他异常也不影响主界面显示
        }
    }

    /**
     * 负责人: 待分配
     * 功能: 点击我的商品表格里某一行: new GoodsDetailView(商品id).setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onGoodsRowClick() {
        int row = myGoodsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一件商品");
            return;
        }
        int goodsId = Integer.parseInt(myGoodsTable.getValueAt(row, 0).toString());
        new GoodsDetailView(goodsId).setVisible(true);
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
        int row = myGoodsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一件商品");
            return;
        }
        int goodsId = Integer.parseInt(myGoodsTable.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this, "确定下架该商品吗?", "下架确认", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        GoodsService service = new GoodsService();
        boolean success = service.offShelf(goodsId);
        if (success) {
            JOptionPane.showMessageDialog(this, "下架成功");
            loadMyGoods();
        } else {
            JOptionPane.showMessageDialog(this, "下架失败");
        }
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
        int row = myGoodsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一件商品");
            return;
        }
        int goodsId = Integer.parseInt(myGoodsTable.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this, "确定删除该商品吗? 删除后不可恢复", "删除确认", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        GoodsService service = new GoodsService();
        boolean success = service.delete(goodsId);
        if (success) {
            JOptionPane.showMessageDialog(this, "删除成功");
            loadMyGoods();
        } else {
            JOptionPane.showMessageDialog(this, "该商品有未完成的交易，不能删除");
        }
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【修改】: new EditGoodsView(商品id).setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onEditClick() {
        int row = myGoodsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一件商品");
            return;
        }
        int goodsId = Integer.parseInt(myGoodsTable.getValueAt(row, 0).toString());
        new EditGoodsView(goodsId).setVisible(true);
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【修改信息】: 弹出 EditProfileDialog, 关闭后重新 loadUserInfo()
     * 参数: 无
     * 返回值: 无
     */
    public void onEditInfoClick() {
        new EditProfileDialog(this).setVisible(true);
        loadUserInfo();
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【修改密码】: 弹出 ChangePasswordDialog
     * 参数: 无
     * 返回值: 无
     */
    public void onChangePasswordClick() {
        new ChangePasswordDialog(this).setVisible(true);
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【添加好友】(拓展): 弹出 AddFriendDialog, 关闭后重新 loadFriends()
     * 参数: 无
     * 返回值: 无
     */
    public void onAddFriendClick() {
        try {
            new AddFriendDialog(this).setVisible(true);
            loadFriends();
        } catch (UnsupportedOperationException ex) {
            JOptionPane.showMessageDialog(this, "好友功能暂未实现");
        }
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【我的留言】(拓展): new MyMessageView().setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onMyMessageClick() {
        new MyMessageView().setVisible(true);
    }
}