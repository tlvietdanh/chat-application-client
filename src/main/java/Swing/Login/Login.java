/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swing.Login;

import Controller.*;
import Dao.AccountsDao;
import Model.AccountsEntity;
import org.hibernate.Session;
import Utils.HibernateUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author black
 */
public class Login extends JFrame {
    AccountsDao accountsDao = new AccountsDao();
    Container f = this;
    boolean loading = false;
    
    boolean  countDown = false;
    int time = 0;
    
    AccountsEntity user = null;
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        username.setHorizontalAlignment(JTextField.LEFT);
        password.setHorizontalAlignment(JTextField.LEFT);
        this.setLocationRelativeTo(null);
        // create 2 term account

        TestConnection();

    }

    void TestConnection() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.close();

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối với cở sở dữ liệu. Xin vui lòng  chạy script sql và kiểm tra lại file .env!");
            Runtime.getRuntime().exit(1);
        }
    }
    
    void connectingToServer() {
        final String port = post_number.getText();
        try {
            Integer.parseInt(port);
            
            Thread thread = new Thread() {
                public void run() {
                    ConnectServer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ConnectServer.setSize(new Dimension(300, 200));
                    ConnectServer.setLocationRelativeTo(f);
                    ConnectServer.setVisible(true);
                    
                    time = 10;
                    CountDown countDown1 = new CountDown();
                    countDown1.start();
                    loading = false;
                    username.setEditable(false);
                    password.setEditable(false);
                    post_number.setEditable(false);
                    login_btn.setIcon(null);
                    login_btn.setEnabled(false);
                    jButton1.setEnabled(false);
                    while (Controller.socket == null) {
                        if(Controller.socket != null) {
                            User userInfo = new User(user.getAccountId(), user.getUsername(), user.getStatus(), user.getName(), user.getAvatar(), user.getPhone(), user.getAddress(), user.getGender());
                            Controller.user = userInfo;
                            Controller.loading = true;
                            Controller.button = login_btn;
                            Controller.handleLogin();
                            Controller.port = Integer.parseInt(post_number.getText());
                            loading = false;
                            ConnectServer.setVisible(false);
                            username.setEditable(true);
                            password.setEditable(true);
                            post_number.setEditable(true);
                            login_btn.setEnabled(true);
                            jButton1.setEnabled(true);
                            return;
                        }
                        if(countDown) {
                            countDown = false;
                            ConnectServer.setVisible(false);
                            username.setEditable(true);
                            password.setEditable(true);
                            post_number.setEditable(true);
                            login_btn.setEnabled(true);
                            jButton1.setEnabled(true);
                            if(Controller.socket == null) {
                                JOptionPane.showMessageDialog(f, "Quá thời gian. Không tìm thấy server!");
                            }
                            return;
                        }
                        try {
                            int port = Integer.parseInt(post_number.getText());
                            Controller.socket = new Socket("localhost", port);
                            if(Controller.socket != null) {
                            User userInfo = new User(user.getAccountId(), user.getUsername(), user.getStatus(), user.getName(), user.getAvatar(), user.getPhone(), user.getAddress(), user.getGender());
                            Controller.user = userInfo;
                            Controller.port = port;
                            Controller.loading = true;
                            Controller.button = login_btn;
                            Controller.handleLogin();
                            loading = false;
                            ConnectServer.setVisible(false);
                            time = 0;
                            username.setEditable(true);
                            password.setEditable(true);
                            post_number.setEditable(true);
                            login_btn.setEnabled(true);
                            btn_regis.setEnabled(true);
                            return;
                        }
                        } catch (Exception ex) {
                            System.out.println(time);
                        }
                    }
                    
                }
            };
            thread.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Port không hợp lệ, xin thử lại!");
        }
    }
    
    synchronized void sub() {
        this.time = this.time - 1;
        System.out.println(time);
    }
    
    class CountDown extends Thread {
        
        public void run() {
            while (time > 0) {                
                try {
                    this.sleep(1000);
                    sub();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    countDown = true;
                }
            }
            if(time == 0) {
                countDown = true;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ConnectServer = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        register = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btn_regis = new javax.swing.JButton();
        pass = new javax.swing.JPasswordField();
        loginPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jPanel6 = new javax.swing.JPanel();
        login_btn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        post_number = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        ConnectServer.setAlwaysOnTop(true);
        ConnectServer.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        ConnectServer.setEnabled(false);
        ConnectServer.setResizable(false);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Rolling-1s-32px.gif"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Đang kết nối đến server...");

        javax.swing.GroupLayout ConnectServerLayout = new javax.swing.GroupLayout(ConnectServer.getContentPane());
        ConnectServer.getContentPane().setLayout(ConnectServerLayout);
        ConnectServerLayout.setHorizontalGroup(
            ConnectServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConnectServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap())
        );
        ConnectServerLayout.setVerticalGroup(
            ConnectServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectServerLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        register.setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        register.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Đăng ký tài khoản");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("Tên tài khoản:");

        name.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setText("Mật khẩu:");

        btn_regis.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btn_regis.setText("Đăng ký");
        btn_regis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_regisMousePressed(evt);
            }
        });
        btn_regis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regisActionPerformed(evt);
            }
        });

        pass.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(name)
                    .addComponent(btn_regis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pass))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(btn_regis)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register.getContentPane());
        register.getContentPane().setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(200, 200, 0, 0));
        setResizable(false);

        loginPanel.setBackground(new java.awt.Color(102, 102, 102));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo186x150.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Đăng nhập");

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tên đăng nhập:");

        username.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        username.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        username.setToolTipText("Nhập vào tên tài khoản");
        username.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        username.setOpaque(false);
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Chào mừng bạn đến với Chat Thả Ga");

        jPanel5.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mật khẩu:");

        password.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        password.setOpaque(false);
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(password))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 204, 51));
        jPanel6.setOpaque(false);

        login_btn.setBackground(new java.awt.Color(204, 255, 204));
        login_btn.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        login_btn.setText("Đăng nhập");
        login_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                login_btnMouseClicked(evt);
            }
        });
        login_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(login_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Post:");

        post_number.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        post_number.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        post_number.setToolTipText("Nhập vào tên tài khoản");
        post_number.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        post_number.setOpaque(false);
        post_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                post_numberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(post_number, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(post_number, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Đăng ký tài khoản mới");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_usernameActionPerformed

    class MultithreadingDemo extends Thread
    {
        public void run()
        {
            try
            {
                
                String username1 = username.getText();
                String password1 = new String(password.getPassword());
                try {
                    AccountsEntity account = accountsDao.login(username1, password1);
                    System.out.println(user);
                    if(account != null) {
                        connectingToServer();
                        user = account;
                        return;
                    }
                    else {
                        login_btn.setIcon(null);
                        loading = false;
                        username.setEditable(true);
                        password.setEditable(true);
                        JOptionPane.showMessageDialog(f, "Tài khoản hoặc mật khẩu không đúng!");
                    }
                } catch (Exception e) {
                    login_btn.setIcon(null);
                    loading = false;
                    username.setEditable(true);
                    password.setEditable(true);
                    JOptionPane.showMessageDialog(f, "Hệ thống đang có lỗi, vui lòng thử lại sau!");
                    e.printStackTrace();
                }

            }
            catch (Exception e)
            {
                // Throwing an exception
                e.printStackTrace();
                System.out.println ("Exception is caught");
            }
        }
    }

    private void login_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_login_btnMouseClicked
        // TODO add your handling code here:
        if(loading) return;
        String username = this.username.getText();
        String password = new String(this.password.getPassword());
        try {
            Integer.parseInt(post_number.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(evt.getComponent().getParent().getParent().getParent(), "Port không hợp lệ!");
            return;
        }
        if(username.length() == 0 || password.length() ==0) {
            JOptionPane.showMessageDialog(evt.getComponent().getParent().getParent().getParent(), "Dữ liệu không hợp lệ!");
            return;
        }
        ClassLoader cldr = this.getClass().getClassLoader();
        java.net.URL imageURL   = cldr.getResource("Rolling-1s-32px.gif");
        ImageIcon img = new ImageIcon(imageURL);
        login_btn.setIcon(img);
        loading = true;
        this.username.setEditable(false);
        this.password.setEditable(false);
        MultithreadingDemo m = new MultithreadingDemo();
        m.start();

    }//GEN-LAST:event_login_btnMouseClicked

    private void login_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_login_btnActionPerformed

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void post_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_post_numberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_post_numberActionPerformed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
        register.setSize(400, 300);
        register.setVisible(true);

    }//GEN-LAST:event_jButton1MousePressed

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passActionPerformed

    private void btn_regisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_regisActionPerformed

    private void btn_regisMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_regisMousePressed
        // TODO add your handling code here:
        String Name = this.name.getText().trim();
        String password = new String(pass.getPassword());
        
        if(!Name.equals("") && !password.equals("")) {
             ClassLoader cldr = this.getClass().getClassLoader();
            java.net.URL imageURL   = cldr.getResource("Rolling-1s-32px.gif");
            ImageIcon img = new ImageIcon(imageURL);
            btn_regis.setIcon(img);
            loading = true;
            this.name.setEditable(false);
            this.pass.setEditable(false);
            ThreadRegis m = new ThreadRegis();
            m.start();
        }
        
    }//GEN-LAST:event_btn_regisMousePressed

    class ThreadRegis extends Thread
    {
        public void run()
        {
            try
            {
                
                String username1 = name.getText();
                String password1 = new String(pass.getPassword());
                try {
                    String mes =  accountsDao.createAccount(username1, password1);

                    btn_regis.setIcon(null);
                    loading = false;
                    name.setEditable(true);
                    pass.setEditable(true);
                    register.setVisible(false);
                    JOptionPane.showMessageDialog(f, mes);
                    
                } catch (Exception e) {
                    btn_regis.setIcon(null);
                    loading = false;
                    name.setEditable(true);
                    pass.setEditable(true);
                    register.setVisible(false);
                    JOptionPane.showMessageDialog(f, "Hệ thống đang có lỗi, vui lòng thử lại sau!");
                    e.printStackTrace();
                }
                name.setText("");
                pass.setText("");

            }
            catch (Exception e)
            {
                // Throwing an exception
                e.printStackTrace();
                System.out.println ("Exception is caught");
            }
        }
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ConnectServer;
    private javax.swing.JButton btn_regis;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JButton login_btn;
    private javax.swing.JTextField name;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField post_number;
    private javax.swing.JDialog register;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
