import ui.*;

public class ClientRun {
    public static void main(String[] args) throws Exception {
        Integer port = 8080;
        new Repl(port).preLoginUI();

    }
}