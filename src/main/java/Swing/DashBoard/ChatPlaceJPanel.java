/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swing.DashBoard;

import Controller.Controller;
import Controller.ReceiveMessage;
import com.vdurmont.emoji.EmojiParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author black
 */
public class ChatPlaceJPanel extends javax.swing.JPanel {
    Container f = this;
    String username = "";
    DefaultListModel<Message> listItems = new DefaultListModel<>();
    
    boolean choosing = false;
    
    boolean isOnline = true;

    String groupName = "";
    /**
     * Creates new form ChatPlaceJPanel
     */
    public ChatPlaceJPanel(String username, String groupName) {
        initComponents();
        this.username = username;
        this.groupName = groupName;
        if(username.equals("") && !groupName.equals("")) {
            jLabel2.setText(groupName);
            add_user.setVisible(true);
        }
        else {
            jLabel2.setText(username);
            add_user.setVisible(false);
        }
        jList1.setModel(listItems);
        jList1.setCellRenderer(new Message("", true, "", false));

        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                Message mes = jList1.getSelectedValue();
                if(mes != null &&mes.isFile == true) {
                    mes.downloadData(mes.message);
                }
                
                //jList1.clearSelection();
            }
        });
        
        
        if(!username.equals("")) {
            CheckOnline checkOnline = new CheckOnline(username);
            checkOnline.start();
        }
        jDialog1.setSize(400, 200);
    }
    
    class CheckOnline extends Thread {
        String username;
        public CheckOnline(String username) {
            this.username = username;
        }
        
        public void run() {
            while(true) {
                if(Controller.listOnlineUser.containsKey(username)) {
                    jLabel14.setText("Status: Online");
                    isOnline = true;
                }
                else {
                    jLabel14.setText("Status: Offline");
                    isOnline = false;
                }
            }
        }
    }
    
    
    public void addMessageToList(String message, boolean isHost,String user, boolean isFile) {
        Message mesItem = new Message(user, isHost, message, isFile);
        listItems.addElement(mesItem);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        MessagePlace = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        add_user = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        edt_message = new javax.swing.JTextArea();
        btn_send = new javax.swing.JButton();
        btn_emoji = new javax.swing.JButton();
        btn_file = new javax.swing.JButton();

        jDialog1.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tạo Nhóm");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Tên người dùng:");

        jTextField1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField1.setBorder(null);

        jButton1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton1.setText("Xác nhận");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDialog1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 167, Short.MAX_VALUE)
            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDialog1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setText("Name of user 1");

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Status: Online");

        add_user.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        add_user.setText("Thêm thành viên");
        add_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                add_userMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add_user)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(add_user))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jList1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jList1.setModel(listItems);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(jList1);

        edt_message.setColumns(20);
        edt_message.setRows(5);
        jScrollPane3.setViewportView(edt_message);

        btn_send.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon-send.png"))); // NOI18N
        btn_send.setText("Gửi");
        btn_send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_sendMousePressed(evt);
            }
        });

        btn_emoji.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon-emoji.png"))); // NOI18N
        btn_emoji.setToolTipText("");

        btn_file.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon-file.png"))); // NOI18N
        btn_file.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_fileMousePressed(evt);
            }
        });

        javax.swing.GroupLayout MessagePlaceLayout = new javax.swing.GroupLayout(MessagePlace);
        MessagePlace.setLayout(MessagePlaceLayout);
        MessagePlaceLayout.setHorizontalGroup(
            MessagePlaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MessagePlaceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MessagePlaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(MessagePlaceLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_send, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                    .addGroup(MessagePlaceLayout.createSequentialGroup()
                        .addComponent(btn_emoji, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_file, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        MessagePlaceLayout.setVerticalGroup(
            MessagePlaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessagePlaceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(MessagePlaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_emoji, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_file, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MessagePlaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btn_send, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(MessagePlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(MessagePlace, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sendMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_sendMousePressed
        // TODO add your handling code here:
        String message = edt_message.getText().trim();

        System.out.println(message);
        if(message.equals("")) {
            return;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.start();
    }//GEN-LAST:event_btn_sendMousePressed

    private void btn_fileMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_fileMousePressed
        // TODO add your handling code here:
        if(choosing) return;
        ImportDate importDate = new ImportDate();
        importDate.start();
        
    }//GEN-LAST:event_btn_fileMousePressed

    private void add_userMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_userMousePressed
        // TODO add your handling code here:
       jDialog1.setVisible(true);
    }//GEN-LAST:event_add_userMousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
        
         String user2 = jTextField1.getText().trim();
        if(!user2.equals("") && Controller.listOnlineUser.containsKey(user2)) {
            try {
                Controller.receiveMessage.addUserToGroup(user2, groupName);

            }catch (Exception e) {
                JOptionPane.showMessageDialog(f.getParent(), "Hệ thống đã xảy ra lỗi, xin vui lỏng thử lại sau!");
            }
        }
        else {
            JOptionPane.showMessageDialog(f.getParent(), "Người dùng không tồn tại hoặc không online");
        }
        jTextField1.setText("");
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton1MousePressed

    class ImportDate extends Thread {
        public void run (){
            try {
                choosing = true;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result;
                result = fileChooser.showOpenDialog(f.getParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    // user selects a file
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    Controller.receiveMessage.sendFile(selectedFile.getAbsolutePath(), username, selectedFile.getName());
                    addMessageToList(selectedFile.getName(), true, username, true);
                }
                choosing = false;

            }catch (Exception e) {
                e.printStackTrace();
                choosing = false;
                JOptionPane.showMessageDialog(f.getParent(), "Hệ thống đã xảy ra lỗi, xin vui lỏng thử lại sau!");
            }
        }
    }
    
    
    class SendMessage extends Thread{
        public void run() {
            try {
                System.out.println("press nè");
                
                if(!isOnline) {
                    JOptionPane.showMessageDialog(f.getParent(), "Không thể gửi tin nhắn đến " + username + "!");
                    return;
                }
                
                String message = edt_message.getText().trim();
                if(!username.equals("")) {
                    Controller.receiveMessage.sendMessage(message, username);
                    addMessageToList(message, true, username, false);
                }
                else {
                    System.out.println("group mes");
                    Controller.receiveMessage.sendGroupMessage(message, groupName);
                    addMessageToList(message, true, groupName, false);
                }


                edt_message.setText("");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(f.getParent(), "Không thể gửi tin nhắn đến " + username + "!");
            }
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MessagePlace;
    private javax.swing.JButton add_user;
    private javax.swing.JButton btn_emoji;
    private javax.swing.JButton btn_file;
    private javax.swing.JButton btn_send;
    private javax.swing.JTextArea edt_message;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<Message> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
