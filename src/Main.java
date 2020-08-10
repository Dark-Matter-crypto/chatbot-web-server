import csc2b.server.WebServer;

public class Main {

    public static void main(String[] args) {

        //Connect to port 1234
        WebServer server = new WebServer(1234);
        //Run server
        server.runServer();

        // Cat image url:
        // http://localhost:1234/docs/cat.jpg

        // Sky image url:
        // http://localhost:1234/docs/sky.jpg
    }
}
