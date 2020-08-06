import csc2b.server.WebServer;

public class Main {

    public static void main(String[] args) {
        WebServer server = new WebServer(1234);
        server.runServer();
    }
}
