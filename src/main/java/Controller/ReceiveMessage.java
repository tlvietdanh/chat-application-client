package Controller;

import Dao.AccountsDao;
import Model.AccountsEntity;
import Swing.DashBoard.ChatPlaceJPanel;
import Swing.DashBoard.DashBoard;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReceiveMessage extends Thread {
    Socket socket;
    String username;
    BufferedReader br;
    BufferedWriter bw;


    public ReceiveMessage(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.username = username;

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        SendUserInfoToServer();
        RequestListFriendOnline();
    }

    String createCommand(String type, String message) {
        return type + "," + Command.KEY + "," + message;
    }

    public boolean SendUserInfoToServer() {
        try {
            System.out.println("Send usermane to server");
            String usernameCommand = createCommand(Command.USERNAME, username);
            bw.write(usernameCommand);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean RequestListFriendOnline() {
        try {
            bw.write(Command.GET_LIST_ONLINE_FRIEND);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void sendCreateGroup(String groupname) throws Exception {
        try {
            System.out.println("Send create group request");
            String usernameCommand = createCommand(Command.CREATE_GROUP, groupname);
            bw.write(usernameCommand);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            throw e;
        }
    }

    public void getListUserOnline(String message) {
        Controller.listOnlineUser = new HashMap<>();
        if(message == null) {
            DashBoard.isUpdate = true;
            return;
        }
        String [] users = message.split("-");
        for (int i = 0; i < users.length; i++) {
            if(!users[i].equals(username) && !Controller.listOnlineUser.containsKey(users[i])) {
                AccountsEntity a = AccountsDao.getAccountByUsername(users[i]);
                Controller.listOnlineUser.put(users[i], a);
            }
        }
        DashBoard.isUpdate = true;
    }

    public boolean sendMessage(String message, String username) throws Exception {
        try {
            System.out.println("Send usermane to server");
            String usernameCommand = createCommand(Command.SEND_MESSAGE+ "-" +username, message);
            bw.write(usernameCommand);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            throw e;
        }
        return true;
    }
    
    public boolean sendMessageFile(String message, String username) throws Exception {
        try {
            System.out.println("Send usermane to server");
            String usernameCommand = createCommand(Command.SEND_MESSAGE_FILE+ "-" +username, message);
            bw.write(usernameCommand);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean sendGroupMessage(String message, String groupname) throws Exception {
        try {
            String usernameCommand = createCommand(Command.GROUP_MESSAGE+ "-" +groupname, message);
            System.out.println(usernameCommand);

            bw.write(usernameCommand);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean addUserToGroup(String username, String groupname) throws Exception {
        try {
            String usernameCommand = createCommand(Command.ADD_USER_TO_GROUP+ "-" +groupname, username);
            bw.write(usernameCommand);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            throw e;
        }
        return true;
    }
    
    public boolean getMessage(String message, String username, boolean isFile) throws Exception {
        try {
            Iterator it = DashBoard.chatComponents.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry el = (Map.Entry)it.next();
                String user = (String) el.getKey();
                System.out.println(user);

                if(user.equals(username)) {
                    ChatPlaceJPanel c = (ChatPlaceJPanel) el.getValue();
                    c.addMessageToList(message, false, user, isFile);
                }
            }
        } catch(Exception e) {
            throw e;
        }
        
        return true;
    }

    public void SendQuitMessage() {
        String usernameCommand = createCommand(Command.QUIT, "QUIT");
        try {
            bw.write(usernameCommand);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String filename, String target, String name) throws Exception {

        try {
            FileTransport fileTransport = new FileTransport(filename, username, name, false);
            fileTransport.start();
            sendMessageFile(name, target);
        } catch (Exception e) {
            throw e;
        }

    }

    public void downloadFile(String filename, String path) throws Exception {
        try {
            FileTransport fileTransport = new FileTransport(filename, username, path, true);
            fileTransport.start();
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public void run() {
        try {
            do {
                String recieveMessage;
                try {
                    recieveMessage = br.readLine();

                    String[] specificMessage = recieveMessage.split(",");
                    if(specificMessage != null && specificMessage.length == 3) {
                        if(specificMessage[1].equals(Command.KEY)) {
                            switch (specificMessage[0]){
                                case Command.GET_LIST_ONLINE_FRIEND:
                                    if(specificMessage.length < 3) {
                                        Controller.listOnlineUser = new HashMap<>();
                                        DashBoard.isUpdate = true;
                                        break;
                                    }
                                    getListUserOnline(specificMessage[2]);
                                    break;
                                case Command.ADD_USER_TO_GROUP:
                                    String groupName = specificMessage[2];
                                    if(!Controller.listOnlineUser.containsKey(groupName)) {
                                        Controller.listOnlineUser.put(groupName, null);
                                        DashBoard.isUpdate = true;
                                    }

                                    break;
                                default:
                                    System.out.println(recieveMessage);
                                    String[] cmd = specificMessage[0].split("-");
                                    System.out.println(cmd.length + " " + cmd[0].equals(Command.SEND_MESSAGE));
                                    if(cmd.length == 2 && cmd[0].equals(Command.SEND_MESSAGE)) {
                                        System.out.println(cmd[1] +  " send: " + specificMessage[2]);
                                        getMessage(specificMessage[2], cmd[1], false);
                                    }else if(cmd.length == 2 && cmd[0].equals(Command.SEND_MESSAGE_FILE)) {
                                        System.out.println(cmd[1] +  " send: " + specificMessage[2]);
                                        getMessage(specificMessage[2], cmd[1], true);
                                    } else if(cmd.length == 2 && cmd[0].equals(Command.GROUP_MESSAGE)) {
                                        System.out.println(cmd[1] +  " send: " + specificMessage[2]);
                                        getMessage(specificMessage[2], cmd[1], false);
                                    }
                                    break;
                            }
                        }
                    }


                    switch (recieveMessage) {
                        case Command.GET_LIST_ONLINE_FRIEND:
                            System.out.println("send list friend to client!");
                            break;
                        case Command.QUIT:
                            System.out.println("Server Stoped!");
                            this.interrupt();
                            break;
                        default:
                            System.out.println( "Server" + " said: " + recieveMessage);
                            break;
                    }
                } catch (Exception e) {
                    this.interrupt();
                }
            }while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class FileTransport extends Thread {
    String filename = "";
    String username = "";
    String name = "";
    boolean isDownload;
    FileTransport(String filename, String username, String name, boolean isDownload) {
        this.filename = filename;
        this.username = username;
        this.name = name;
        this.isDownload = isDownload;
    }
    String createCommand(String type, String message) {
        return type + "," + Command.KEY + "," + message;
    }


    public void run() {
        try {
            if(!isDownload) {
                System.out.println("send file " + filename);
                if(filename.equals("") || username.equals("") || name.equals("")) {
                    return;
                }
                File file = new File(filename);

                Socket fileSocket = new Socket("localhost", Controller.port);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileSocket.getOutputStream()));

                String usernameCommand = createCommand(Command.USERNAME_FILE, name);
                bw.write(usernameCommand);
                bw.newLine();
                bw.flush();

                sleep(200);


                byte[] bytes = new byte[8 * 1024];
                InputStream in = new FileInputStream(file);
                OutputStream out = fileSocket.getOutputStream();

                int count;
                while ((count = in.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }
            }
            else {
                System.out.println("down file " + filename);
                if(filename.equals("") || username.equals("") || name.equals("")) {
                    return;
                }
                File file = new File(name + "\\" + filename);

                Socket fileSocket = new Socket("localhost", Controller.port);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileSocket.getOutputStream()));

                String usernameCommand = createCommand(Command.DOWNLOAD_FILE, filename);
                bw.write(usernameCommand);
                bw.newLine();
                bw.flush();

                sleep(200);


                byte[] bytes = new byte[8 * 1024];
                InputStream in = fileSocket.getInputStream();
                OutputStream out = new FileOutputStream(file);

                int count;
                while ((count = in.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
