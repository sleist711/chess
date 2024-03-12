package ui;

import com.sun.nio.sctp.NotificationHandler;

import java.util.Scanner;

import javax.management.Notification;

public class PreLogin {
    private final ChessClient client;

    public PreLogin(String serverUrl)
    {
        client = new ChessClient(serverUrl, this);
    }

    public void run() {
        System.out.println("\"\\uD83D\\uDC36 Welcome to chess. Sign in to start.\"");
        System.out.println(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while(!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try{
                result = client.eval(line);
                System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
            }
            catch(Throwable e)
            {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();


    }

    public ChessClient(String serverUrl)
    {
        server = new ServerFacade(serverUrl);
    }










    public void notify(Notification notification) {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + notification.message());
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("\n" + EscapeSequences.RESET_TEXT_COLOR + ">>> " + EscapeSequences.SET_TEXT_COLOR_GREEN);
    }
}
