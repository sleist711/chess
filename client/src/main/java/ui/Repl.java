package ui;

import java.util.Scanner;

public class Repl
{
    PreLogin preLogin;
    PostLogin postLogin;
    Gameplay gamePlay;


    public Repl(String serverUrl)
    {
        preLogin = new PreLogin(serverUrl);
        postLogin = new PostLogin(serverUrl);
        gamePlay = new Gameplay();

    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to chess.");
        System.out.print(preLogin.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        String line = "";

        //while we're running prelogin, signed out
        while (preLogin.state == State.SIGNEDOUT) {
            ChessClient.printPrompt();
            line = scanner.nextLine();
            try {
                result = preLogin.eval(line);
                //if it's quit
                if(line.equals("quit"))
                {
                    System.exit(0);
                }
                System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
            }
            catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }

        //now we're in postlogin
        while(postLogin.gameState != GameState.INPLAY)
        {
            ChessClient.printPrompt();
            line = scanner.nextLine();
            try {
                result = postLogin.eval(line);
                //if it's quit
                if(line.equals("quit"))
                {
                    System.exit(0);
                }
                System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
            }
            catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }


        System.out.println();
    }
}
