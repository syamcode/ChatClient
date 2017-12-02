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
public class ChatClient implements Runnable{
    /**
     * @param args the command line arguments
     */
    Socket socket;
    DataOutputStream outStream;
    DataInputStream inpStream;
    ChatClientThread client;
    Thread thread;
    public void startClient(String host, int port) {
        
        System.out.println("Connecting...");
        try {
            socket = new Socket(host, port);
            System.out.println("Server Connected "+socket);
            start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {
        Scanner keyboard = new Scanner(System.in);
        while(thread!=null) {
            try{
               String str = keyboard.nextLine();
                outStream.writeUTF(str);
                outStream.flush(); 
            }
            catch(Exception e) {
                System.out.println(e);
                stop();
            }
            
        }
    }
    public void handle(String msg) {
        if(msg.equals("/quit")) {
            System.out.println("Bye.");
            stop();
        }
        else {
            System.out.println(msg);
        }
    }
    public void start() throws IOException {
        outStream = new DataOutputStream(socket.getOutputStream());
        if(thread==null) {
            client = new ChatClientThread(this, socket);
            thread = new Thread(this);
            thread.start();
        }
    }
    public void stop() {
        if(thread!=null) {
            thread.stop();
            thread=null;
            try{
                if(outStream!=null) {
                    outStream.close();
                }
                if(socket!=null){
                    socket.close();
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            client.close();
            client.stop();
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
