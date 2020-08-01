package Controller;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler extends Thread {
    private Socket socket;
    private String username;
    private BufferedWriter bw;
//    private final String GET_LIST_ONLINE_FRIEND = "GET_LIST_ONLINE_FRIEND";


    public RequestHandler(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.username = username;

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
            System.out.println("hihi");
            bw.write(Command.GET_LIST_ONLINE_FRIEND);
            bw.newLine();
            bw.flush();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {

            System.out.println("Start talking to server!");

            String sendMessage;
            do {
                Scanner sc = new Scanner(System.in);
                sendMessage = sc.nextLine().trim();

                bw.write(sendMessage);
                bw.newLine();
                bw.flush();

                if(sendMessage.equalsIgnoreCase("quit")) {
                    System.out.println("Closing...");
                    this.interrupt();
                    break;
                }
            } while (true);

        }catch (Exception e) {

        }
    }
}
