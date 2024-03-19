import ui.*;

import java.io.IOException;

public class Main {
    static ServerFacade facade;
    public static void main(String[] args) throws IOException {
        Integer port = 8080;
        new Repl(port).preLoginUI();

    }
}