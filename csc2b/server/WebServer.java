package csc2b.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static ServerSocket ss;
    private static int port;
    private static boolean serverRunning = false;

    //Constructor
    public WebServer(int port){
        this.port = port;

        System.out.println("Binding to port: " + port);
        try {
            ss = new ServerSocket(port);
            serverRunning = true;
            System.out.println("Waiting for connections. . .\n");
        }
        catch (IOException e) {
            System.err.println("Failed to bind to port.");
        }
    }

    //Method to start running the server
    public static void runServer(){
        while(serverRunning){
            try {
                Socket clientConnection = ss.accept();
                WebServerHandler serverHandler = new WebServerHandler(clientConnection);
                Thread thread = new Thread(serverHandler);
                thread.start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
