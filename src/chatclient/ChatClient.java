/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author syamcode
 */
public class ChatClient {
    /**
     * @param args the command line arguments
     */
    Socket socket;
    DataOutputStream outStream;
//    DataInputStream inpStream;
    public void startClient(String host, int port) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Connecting...");
        try {
            socket = new Socket(host, port);
            System.out.println("Server Connected "+socket);
//            inpStream = new DataInputStream(System.in);
            outStream = new DataOutputStream(socket.getOutputStream());
            boolean con = true;
            while(con) {
                String str = keyboard.nextLine();
                outStream.writeUTF(str);
                outStream.flush();
                con = !str.equals("/quit");     
            }
            close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) { 
        ChatClient client = new ChatClient();
        client.startClient("localhost", 5001);
    }
    public void close() throws IOException {
        if(outStream!=null) {
            outStream.close();
        }
        if(socket!=null) {
            socket.close();
        }
//        if(inpStream!=null) {
//            inpStream.close();
//        }
    }
}
