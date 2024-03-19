package ui;

import java.util.Scanner;

public class Repl
{
    PreLogin preLogin;
    PostLogin postLogin;
    Gameplay gamePlay;
    static String authToken;
    static State state = State.SIGNEDOUT;


    public Repl(String serverUrl)
    {
        preLogin = new PreLogin(serverUrl);
        postLogin = new PostLogin(serverUrl);
        gamePlay = new Gameplay();
        authToken = "";

    }

    public void run() {
        System.out.println("Welcome to chess.");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        String line = "";


        while (!line.equals("quit"))
        {

            //while we're running prelogin, signed out
            if (state == State.SIGNEDOUT)
            {
                System.out.print(preLogin.help());
                ChessClient.printPrompt();
                line = scanner.nextLine();
                try {
                    result = preLogin.eval(line);
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + result + "\n");
                }
                catch (Throwable e) {
                    var msg = e.toString();
                    System.out.print(msg);
                }
                System.out.println();
            }
            //now we're in postlogin
            if (state == State.SIGNEDIN)
            {
                System.out.print(postLogin.help());

                ChessClient.printPrompt();
                line = scanner.nextLine();
                try {
                    result = postLogin.eval(line);
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + result + "\n");
                }
                catch (Throwable e) {
                    var msg = e.toString();
                    System.out.print(msg);
                }
                System.out.println();
            }

            if(state == State.INPLAY)
            {
                //if the game is in play. Implement this in phase 6.
            }
            if(line.equals("quit"))
            {
                System.exit(0);
            }

        }
        //System.exit(0);
    }

    public static String getAuth()
    {
        return authToken;
    }

    public static void setAuth(String newAuth)
    {
       authToken = newAuth;
    }
}
