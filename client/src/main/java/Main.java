import ui.*;

import java.io.IOException;

public class Main {
    static ServerFacade facade;
    public static void main(String[] args) throws Exception {
        Integer port = 8080;
        new Repl(port).preLoginUI();

    }
}