package Controller;

import Model.AccountsEntity;
import Swing.DashBoard.DashBoard;
import Swing.Login.Login;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

import javax.swing.*;

public class Controller {


    
    //public static User user = new User(0, "", "", "", "", "", "", "");
    public static User user;
    private static Login login;
    public static boolean loading = false;
    public static JButton button;
    private static DashBoard dashBoard;
    public static Socket socket;
    public static ReceiveMessage receiveMessage;
    public static RequestHandler requestHandler;
    public static HashMap<String, AccountsEntity> listOnlineUser = new HashMap<>();
    public static int port;

    public static void handleLogin() {

        if(user == null) {
            login = new Login();
            login.setVisible(true);
            
            if(dashBoard !=null) {
                dashBoard.setVisible(false);
                dashBoard.dispose();
            }
        }
        else {
            createHandler();
            dashBoard = new DashBoard();
            dashBoard.setVisible(true);
            button.setIcon(null);
            if(login!=null) {
                login.setVisible(false);
                login.dispose();
            }
        }
    }



    public static void createHandler() {
        if(socket == null) {
            return;
        }
        // create ReceiveMessage thread;
        try {
            receiveMessage = new ReceiveMessage(socket, user.getUsername());
            receiveMessage.start();
        } catch (Exception e) {

        }

    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        // ClassTabs c = new ClassTabs();
        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                handleLogin();
            }
        });
    }
    
}
