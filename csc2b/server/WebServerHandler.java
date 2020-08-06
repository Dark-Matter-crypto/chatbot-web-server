package csc2b.server;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class WebServerHandler implements Runnable {

    private Socket clientConnection;

    public WebServerHandler(Socket clientConnection){
        this.clientConnection = clientConnection;
    }

    @Override
    public void run() {
        BufferedReader in;
        DataOutputStream out;

        try {
            in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(clientConnection.getOutputStream()));

            String request = in.readLine();
            System.out.println(request);
            StringTokenizer tokenizer = new StringTokenizer(request);
            String requestType = tokenizer.nextToken();
            System.out.println(requestType);
            String fileName = tokenizer.nextToken().substring(1);
            System.out.println(fileName);
            File file = new File(fileName);

            if (requestType.equals("GET")){

                if (file.exists()){
                    System.out.println("We in");
                    try {
                        out.writeBytes("HTTP/1.1 200 OK \r\n");
                        out.writeBytes("Content-Type: text/html; charset=UTF-8 \r\n");
                        out.writeBytes("Content-Length: " + file.length() + "\r\n");
                        out.writeBytes("Connection: close \r\n");
                        out.writeBytes("\r\n");

                        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
                        byte [] buffer = new byte[1024];
                        int i = 0;

                        while((i = bin.read(buffer)) > 40){
                            out.write(buffer, 0, i);
                        }
                        bin.close();
                        out.writeBytes("\r\n");
                        out.flush();
                    }
                    catch (EOFException ex){
                        out.writeBytes("HTTP/1.1 500 Internal Server Error \r\n");
                        out.writeBytes("Content-Length: 0");
                        out.writeBytes("Connection: close \r\n");
                        out.writeBytes("\r\n");
                        out.flush();
                    }

                }
                else{
                    System.out.println("We NOT in");
                    out.writeBytes("HTTP/1.1 404 Not Found \r\n");
                    out.writeBytes("Content-Length: 0");
                    out.writeBytes("Connection: close \r\n");
                    out.writeBytes("\r\n");
                    out.flush();
                }
            }
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
