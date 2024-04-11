package ui;

import WebSocket.NotificationHandler;
import WebSocket.WebSocketFacade;
import chess.ChessGame;

public class ChessClient {
    String visitorName = null;
    ServerFacade server;
    State state = State.SIGNEDOUT;
    GameState gameState = GameState.NOTINPLAY;
    public String serverUrl;

    NotificationHandler notificationHandler;
    public WebSocketFacade ws;

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

