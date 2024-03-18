import ui.*;

import java.io.IOException;

public class Main {
    static ServerFacade facade;
    public static void main(String[] args) throws IOException {
        Integer port = 8080;
        System.out.println("Running client on " + port);

        new Repl(port).preLoginUI();

    }
}