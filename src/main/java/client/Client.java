package client;

import Dao.AccountsDao;
import Dao.ConnectionsDao;
import Model.AccountsEntity;
import io.github.cdimascio.dotenv.Dotenv;
import Controller.RequestHandler;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Client {
    
    public static Socket socket;
    
    public void connectToServer(int port) {
        System.out.println("Connecting to server...");
        while (socket == null) {
            try {
            socket = new Socket("localhost",1310);
            }catch (Exception e) {

            }
        }
    }




    
//    public static void main(String[] args) {
////        Dotenv dotenv = Dotenv.load();
////        int Port = Integer.parseInt(dotenv.get("PORT"));
//        try {
//            AccountsDao accountsDao = new AccountsDao();
//            ConnectionsDao connectionsDao = new ConnectionsDao();
////            accountsDao.createAccount("user1", "123123");
////            accountsDao.createAccount("user2", "123123");
////            accountsDao.createAccount("user3", "123123");
////            accountsDao.createAccount("user4", "123123");
////            accountsDao.createAccount("user5", "123123");
////            accountsDao.createAccount("user6", "123123");
//
//            Scanner sc = new Scanner(System.in);
//            System.out.printf("username: ");
//            String username = sc.nextLine();
//            System.out.printf("password: ");
//            String password = sc.nextLine();
//
//
//            // login
//            AccountsEntity accountsEntity = accountsDao.login(username, password);
//
//            if(accountsEntity == null) {
//                return;
//            }
//
//
//
//
//            System.out.println("Connecting to server...");
//            Socket s =  null;
//            while (s == null) {
//                try {
//                    s = new Socket("localhost",1310);
//                }catch (Exception e) {
//                    System.out.println("waiting for server respone!");
//                }
//            }
//            while (true) {
//                String choose = sc.nextLine();
//                switch (choose) {
//                    case "1":
//                        System.out.println("Ket ban voi!");
//                        String user2 = sc.nextLine();
//                        createRequest(user2, accountsEntity);
//                        break;
//                    case "2":
//                        RequestHandler requestHandler = new RequestHandler(s, username);
//                        requestHandler.start();
//                        break;
//                    case "3":
//                        System.out.println("Danh sach ban be!");
//                        List<String> friend = connectionsDao.getListFriend(accountsEntity.getAccountId());
//                        for (int i = 0; i < friend.size(); i++) {
//                            System.out.println(friend.get(i));
//                        }
//                        break;
//                    case "4":
//                        try {
//                            s = new Socket("localhost",1310);
//                        }catch (Exception e) {
//                            System.out.println("waiting for server respone!");
//                        }
//                    case "5":
//
//                    default:
//                        return;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    static void createRequest(String username, AccountsEntity me) {
//        AccountsEntity accountsEntity = AccountsDao.getAccountByUsername(username);
//
//        ConnectionsDao connectionsDao = new ConnectionsDao();
//
//        Date date = new Date();
//        date.getTime();
//
//        String result = connectionsDao.createConnection(me.getAccountId(), accountsEntity.getAccountId(), String.valueOf(date.getTime()));
//
//        System.out.println(result);
//    }

}
