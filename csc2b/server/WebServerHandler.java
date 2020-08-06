package csc2b.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WebServerHandler implements Runnable {

    private Socket clientConnection;

    public WebServerHandler(Socket clientConnection){
        this.clientConnection = clientConnection;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            DataInputStream dis = new DataInputStream(clientConnection.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Closing Connection. . . ");
            if (clientConnection != null){
                try {
                    clientConnection.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
