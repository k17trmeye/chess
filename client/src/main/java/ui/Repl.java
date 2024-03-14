package ui;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.Scanner;

public class Repl {
    private final ServerFacade serverFacade;
    Scanner scanner = new Scanner(System.in);


    public Repl(Integer port) throws IOException {
        serverFacade = new ServerFacade(port);
        serverFacade.clear();
    }

    public void run() throws IOException {
        System.out.println(EscapeSequences.BLACK_KING + "Welcome to 240 chess. Type help to get started." + EscapeSequences.WHITE_KING);

         // Trim whitespace and convert to lowercase
        boolean running = true;

        while (running) {
            String command = scanner.nextLine().trim().toLowerCase();

            String[] parts = command.split("\\s+"); // Split input by whitespace
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                continue; // Prompt user again
            }

            switch (parts[0].toLowerCase()) {
                case "help":
                    System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account\n" +
                            "login <USERNAME> <PASSWORD> - to play chess\n" +
                            "quit - to leave UI\n" +
                            "help - list possible commands\n");
                    break;
                case "quit":
                    running = false;
                    break;
                case "register":
                    if (parts.length != 4) {
                        System.out.println("Invalid format. Correct format: register <USERNAME> <PASSWORD> <EMAIL>\n");
                        continue; // Prompt user again
                    }
                    String registerUsername = parts[1];
                    String registerPassword = parts[2];
                    String registerEmail = parts[3];
                    var authdata = serverFacade.registerUser(registerUsername, registerPassword, registerEmail);

                    if (authdata != null) {
                        System.out.println("User registered: " + authdata + "\n");
                    }
                    else {
                        System.out.println("Error registering user, already taken\n");
                    }
                    break;
                case "login":
                    if (parts.length != 3) {
                        System.out.println("Invalid format. Correct format: login <USERNAME> <PASSWORD>\n");
                        continue; // Prompt user again
                    }
                    String loginUsername = parts[1];
                    String loginPassword = parts[2];
                    var loginAuth = serverFacade.loginUser(loginUsername, loginPassword);

                    if (loginAuth != null) {
                        System.out.println("Logged in, authToken: " + loginAuth + "\n");
                    }
                    else {
                        System.out.println("Error logging in user, already taken\n");
                    }
                    break;

                default:
                    System.out.println("Invalid command, type help to get started.\n");
            }
        }
    }

}
