package csc2b.server;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServer {

    private static ServerSocket ss;
    private static final int PORT = 1234;

    public static void runServer(){
        System.out.println("Binding to port: " + PORT);
        try {
            ss = new ServerSocket(PORT);
            System.out.println("Waiting for connections. . .\n");
        }
        catch (IOException e) {
            System.err.println("Failed to bind to port.");
        }

    }
}
