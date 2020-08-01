/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swing.DashBoard;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import Controller.Controller;
import Model.AccountsEntity;
import java.awt.Dialog;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author black
 */
public class DashBoard extends javax.swing.JFrame {
    // tab components
    ChatPlaceJPanel ChatPlace;
    
    
    JPanel[] listComponentPanel;
    public static JPanel[] listTabPanel;

    
    public static String currentTabs = "tab_class";
    
    static Color tab_background_color = new Color(23,35,51);
    static Color tab_press_color = new Color(102,102,102);

    public static boolean isUpdate = false;
    
    public static HashMap<String, ChatPlaceJPanel> chatComponents = new HashMap<>();
    
    public static UserItems selectedItem;

    int tabWidth = 0;

    /**
     * Creates new form Home
     */
    public DashBoard() {
        
        initComponents();
        this.setLocation(100, 50);

        tabWidth = tab_bar_teacher.getSize().width;
        listTabPanel = new JPanel [] {};
                
//        initTabComponent();
        
        this.setSize(915, 535);
        tab_bar_teacher.setSize(915, 300);
        //addUserToListFriends();
        
       jScrollPane1.setSize(new Dimension(64*6, 300));
       user_avatar.setSize(64, 64);
       // set user infor
       setUserInfor();
       ListFriends.setCellRenderer(new UserItems("", ""));
       jScrollPane1.setViewportView(ListFriends);
        final Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(500);
                    }catch (Exception e) {

                    }
                    if(isUpdate) {
                        setData();
                    }
                }
            }
        };
        thread.start();
        this.setData();
//        if(Controller.listOnlineUser.size() > 0) {
//            ListFriends.setSelectedValue(0, true);
//        }

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("B is closing");
                Controller.receiveMessage.SendQuitMessage();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("B has closed");
            }

        });
        createGroup.setSize(400, 200);
    }

    public void setData() {
        int count = 0;
        DefaultListModel<UserItems> listFriend = new DefaultListModel<UserItems>();
        try {
            Iterator iterator = Controller.listOnlineUser.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry user = (Map.Entry) iterator.next();
                AccountsEntity a = (AccountsEntity) user.getValue();
                String username = (String) user.getKey();
                if(Controller.user.getUsername().equals(username)) {
                    iterator.next();
                }
                if(a != null) {
                    UserItems item1 = new UserItems(username, a.getAvatar());
                    listFriend.addElement(item1);
                }
                else {
                    UserItems item1 = new UserItems(username, "");
                    listFriend.addElement(item1);
                }
                
                
                // add chat place
                ChatPlaceJPanel chatPlaceJPanel = new ChatPlaceJPanel((String)user.getKey(), "");
                if(!chatComponents.containsKey(username)) {
                    chatComponents.put((String)user.getKey(), chatPlaceJPanel);
                    getContentPane().add(chatPlaceJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 600, 500));
                    chatPlaceJPanel.setVisible(false);
                }
                count ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(count < 5) {
            for (int i = 0; i < 5 - count; i++) {
                UserItems item1 = new UserItems("", "");
                listFriend.addElement(item1);
            }
        }
        ListFriends.setModel(listFriend);
        isUpdate = false;
    }

    private void setUserInfor() {
        URL url;
        Image image = null;
        try {
            url = new URL("https://i.ibb.co/pKTbbVj/icons8-male-user-64.png");
            image = ImageIO.read(url);
            if(image != null) {
                user_avatar.setIcon(new ImageIcon(image));
                user_avatar.setSize(64, 64);
            }
            else {
                ClassLoader cldr = this.getClass().getClassLoader();
                java.net.URL imageURL   = cldr.getResource("user.png");
                ImageIcon img = new ImageIcon(imageURL);
                user_avatar.setIcon(img);
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userName.setText(Controller.user.getUsername());
    }

    public static void setVisibleChatPlace() {
        if(selectedItem == null || selectedItem.currentTabs.equals("")) {
            return;
        }

        Iterator iterator = chatComponents.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry component = (Map.Entry) iterator.next();
            ChatPlaceJPanel c = (ChatPlaceJPanel) component.getValue();
            String username = (String) component.getKey();
            if(selectedItem.currentTabs.equals(username)) {
                c.setVisible(true);
            }
            else {
                c.setVisible(false);
            }
        }
    }
    

    
    
    
    public static void resetAllColor(JPanel current) {
        for(int i = 0; i < listTabPanel.length; i++) {
            if(listTabPanel[i] == current) {
                continue;
            }
            listTabPanel[i].setBackground(DashBoard.tab_background_color);
        }
    }
    
    public static void setVisibleTabs(String target_user) {
        Iterator it = chatComponents.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry user = (Map.Entry) it.next();
            String name = (String) user.getKey();
            ChatPlaceJPanel c = (ChatPlaceJPanel) user.getValue();

            if(target_user.equals(name)) {
                c.setVisible(true);
            }
            else {
                c.setVisible(false);
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

        createGroup = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        tab_bar_teacher = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        user_avatar = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListFriends = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        createGroup.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        createGroup.setResizable(false);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tạo Nhóm");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Tên nhóm:");

        jTextField1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField1.setBorder(null);

        jButton3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton3.setText("Xác nhận");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout createGroupLayout = new javax.swing.GroupLayout(createGroup.getContentPane());
        createGroup.getContentPane().setLayout(createGroupLayout);
        createGroupLayout.setHorizontalGroup(
            createGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(createGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(createGroupLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        createGroupLayout.setVerticalGroup(
            createGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 167, Short.MAX_VALUE)
            .addGroup(createGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(createGroupLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat cùng nhau");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(300, 600));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tab_bar_teacher.setBackground(new java.awt.Color(23, 35, 51));
        tab_bar_teacher.setPreferredSize(new java.awt.Dimension(300, 500));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setOpaque(false);

        user_avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user.png"))); // NOI18N

        userName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        userName.setForeground(new java.awt.Color(255, 255, 255));
        userName.setText("Name of User");

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Status: Online");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Danh sách người online");

        jScrollPane1.setBackground(new java.awt.Color(23, 35, 51));
        jScrollPane1.setForeground(new java.awt.Color(23, 35, 51));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setToolTipText("");

        ListFriends.setBackground(new java.awt.Color(23, 35, 51));
        ListFriends.setForeground(new java.awt.Color(23, 35, 51));
        ListFriends.setOpaque(false);
        ListFriends.setSelectionForeground(new java.awt.Color(23, 35, 51));
        jScrollPane1.setViewportView(ListFriends);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Tạo nhóm");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Thoát");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(user_avatar)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(user_avatar)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(userName)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tab_bar_teacherLayout = new javax.swing.GroupLayout(tab_bar_teacher);
        tab_bar_teacher.setLayout(tab_bar_teacherLayout);
        tab_bar_teacherLayout.setHorizontalGroup(
            tab_bar_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_bar_teacherLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(232, 232, 232))
        );
        tab_bar_teacherLayout.setVerticalGroup(
            tab_bar_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_bar_teacherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(tab_bar_teacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        int h = evt.getComponent().getBounds().getSize().height;
        Dimension d = new Dimension(tabWidth, h);
        tab_bar_teacher.setSize(d);
        
    }//GEN-LAST:event_formComponentResized

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
        
        createGroup.setVisible(true);
        
    }//GEN-LAST:event_jButton1MousePressed

    public static void createGroupChat(String groupname) {
        
    }
    
    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        // TODO add your handling code here:
        try {
            Controller.receiveMessage.SendQuitMessage();
            Controller.socket.close();
           
        } catch(Exception e) {
            e.printStackTrace();
        }
        Controller.listOnlineUser = new HashMap<>();
        Controller.receiveMessage = null;
        Controller.requestHandler = null;
        Controller.user = null;
        Controller.handleLogin();
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        // TODO add your handling code here:
        String groupname = jTextField1.getText();
        if(groupname.equals("") || chatComponents.containsKey(groupname)) {
            JOptionPane.showMessageDialog(evt.getComponent().getParent(), "Tên không hợp lệ hoặc đã tồn tại");
            return;
        }
        ChatPlaceJPanel chatPlaceJPanel = new ChatPlaceJPanel("", groupname);
        
        chatComponents.put(groupname, chatPlaceJPanel);
        getContentPane().add(chatPlaceJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 600, 500));
        chatPlaceJPanel.setVisible(false);
        
        try {
          Controller.receiveMessage.sendCreateGroup(groupname);
          Controller.listOnlineUser.put(groupname, null);
          this.setData();
          this.setVisibleChatPlace();

        } catch(Exception e) {
            JOptionPane.showMessageDialog(evt.getComponent().getParent(), "Đã có lỗi xảy ra, xin vui lòng thử lai sau!");
        }
    }//GEN-LAST:event_jButton3MousePressed

    

    
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<UserItems> ListFriends;
    private javax.swing.JDialog createGroup;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel tab_bar_teacher;
    private javax.swing.JLabel userName;
    private javax.swing.JLabel user_avatar;
    // End of variables declaration//GEN-END:variables
}
