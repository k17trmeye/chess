import server.Server;

public class ServerRun {
    public static void main(String[] args) {
        Server server = new Server();
        var port = server.run(8080);
    }
}