package csc2b.server;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class WebServerHandler implements Runnable {
    private Socket clientConnection;
    private final String ERROR_404_FileName = "docs/404.html";
    private final String ERROR_500_FileName = "docs/500.html";

    //Constructor
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
            StringTokenizer tokenizer = new StringTokenizer(request);
            String requestType = tokenizer.nextToken();
            String fileName = tokenizer.nextToken().substring(1);
            File file = new File(fileName);

            if (requestType.equals("GET")){

                if (file.exists()){
                    try {
                        out.writeBytes("HTTP/1.1 200 OK \r\n");
                        out.writeBytes("Content-Type: image/jpeg \r\n");
                        out.writeBytes("Content-Length: " + file.length() + "\r\n");
                        out.writeBytes("Connection: close \r\n");
                        out.writeBytes("\r\n");

                        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
                        byte [] buffer = new byte[1024];
                        int i = 0;

                        while((i = bin.read(buffer)) > 0){
                            out.write(buffer, 0, i);
                        }
                        out.writeBytes("\r\n");
                        out.flush();
                        bin.close();
                    }
                    catch (EOFException ex){
                        FileInputStream fin = new FileInputStream(ERROR_500_FileName);
                        out.writeBytes("HTTP/1.1 500 Internal Server Error \r\n");
                        out.writeBytes("Content-Type: text/html \r\n");
                        out.writeBytes("Content-Length: " + fin.available() + "\r\n");
                        out.writeBytes("Connection: close \r\n");
                        out.writeBytes("\r\n");

                        BufferedInputStream bin = new BufferedInputStream(fin);
                        byte [] buffer = new byte[1024];
                        int i = 0;

                        while((i = bin.read(buffer)) > 0){
                            out.write(buffer, 0, i);
                        }
                        out.writeBytes("\r\n");
                        out.flush();
                        bin.close();
                    }
                }
                else{
                    FileInputStream fin = new FileInputStream(ERROR_404_FileName);
                    out.writeBytes("HTTP/1.1 404 File Not Found \r\n");
                    out.writeBytes("Content-Type: text/html \r\n");
                    out.writeBytes("Content-Length: " + fin.available() + "\r\n");
                    out.writeBytes("Connection: close \r\n");
                    out.writeBytes("\r\n");

                    BufferedInputStream bin = new BufferedInputStream(fin);
                    byte [] buffer = new byte[1024];
                    int i = 0;

                    while((i = bin.read(buffer)) > 0){
                        out.write(buffer, 0, i);
                    }
                    out.writeBytes("\r\n");
                    out.flush();
                    bin.close();
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
