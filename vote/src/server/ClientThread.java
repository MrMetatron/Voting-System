package server;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket toClientSocket=null;//�Ự�׽���
    private ObjectOutputStream out;
    private DataInputStream in;
    
    private ServerPanel sPane;
    
    public ClientThread(Socket toClientSocket,int clientCounts, ServerPanel serverPanel) { //���캯��
        this.toClientSocket=toClientSocket;
        sPane = serverPanel;
    }    
    @Override
    public void run(){
        ThreadIn threadIn = new ThreadIn(toClientSocket, in, sPane);
        ThreadOut threadOut = new ThreadOut(toClientSocket, out, sPane);
        
        threadIn.start();
        threadOut.start();
        
        System.out.println("Start successfully");
        
    } //end run
} //end class
