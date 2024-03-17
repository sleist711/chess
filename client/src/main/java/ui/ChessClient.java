package ui;

import dataAccess.ResponseException;

import java.util.Arrays;

public class ChessClient {
    private String visitorName = null;
    ServerFacade server;
    State state = State.SIGNEDOUT;
    GameState gameState = GameState.NOTINPLAY;
    private String serverUrl;



    //this class tells it what to do between the server and each request for
    //each potential action

    //default constructor
    public ChessClient()
    {

    }

    //constructor with url
    public ChessClient(String serverUrl)
    {
        server = new ServerFacade(serverUrl);
    }

    static void printPrompt() {
        System.out.print("\n" + EscapeSequences.RESET_TEXT_COLOR + ">>> " + EscapeSequences.SET_TEXT_COLOR_GREEN);
    }



}

