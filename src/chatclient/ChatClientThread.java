/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author syamcode
 */
class ChatClientThread extends Thread{
    Socket socket;
    ChatClient client;
    DataInputStream inpStream;
    
    public ChatClientThread(ChatClient _client, Socket _socket) {
        client = _client;
        socket = _socket;
        open();
        start();
    }
    
    public void open() {
        try {
            inpStream = new DataInputStream(socket.getInputStream());
        }
        catch(Exception e) {
            System.out.println(e);
            client.stop();
        }
    }
    public void close() {
        try{
            if(inpStream!=null) {
                inpStream.close();
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public void run() {
        while(true) {
            try{
                client.handle(inpStream.readUTF());
            }
            catch(Exception e) {
                System.out.println(e);
                client.stop();
            }
        }
    }
}
