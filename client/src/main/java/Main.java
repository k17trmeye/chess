import chess.*;
import ui.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

public class Main {
    static ServerFacade facade;
    public static void main(String[] args) throws IOException {
        Integer port = 8080;
        System.out.println("Running client on " + port);

        new Repl(port).run();

    }
}