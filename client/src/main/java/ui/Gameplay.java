package ui;

import WebSocket.WebSocketFacade;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataAccess.ResponseException;
import server.requests.RegistrationRequest;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static service.Service.gameAccess;

public class Gameplay extends ChessClient{

    public Gameplay(String serverUrl)
    {
        super(serverUrl);
    }

    public String eval(String input) throws Exception {
        //try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                //case "redraw" -> redrawBoard(params);
                case "move" -> movePiece(params);
                case "resign" -> resign(params);
                case "leave" -> leaveGame(params);

                default -> help();
            };
       // }
        //catch (ResponseException ex) {
           // return ex.getMessage();
        //}
    }

    public String leaveGame(String ... params) throws Exception
    {
        String authToken = params[0];
        Integer gameID = Integer.valueOf(params[1]);

        //websocket
        ws = new WebSocketFacade(server.serverUrl, notificationHandler);
        ws.leaveGame(authToken, gameID);

        //transition back to postLogin
        state = State.SIGNEDIN;
        return String.format("You left game %d", gameID);
   }
    public String resign(String ... params) throws Exception
    {
        String authToken = params[0];
        Integer gameID = Integer.valueOf(params[1]);

        //websocket
        ws = new WebSocketFacade(server.serverUrl, notificationHandler);
        ws.resign(authToken, gameID);

        return String.format("You resigned from game %d", gameID);
    }

    public String movePiece(String ... params) throws Exception
    {
        String authToken = params[0];
        Integer gameID = Integer.valueOf(params[1]);
        ChessPiece.PieceType pieceType = ChessPiece.PieceType.valueOf(params[2]);
        ChessPosition startPosition = ChessPosition.convertToPosition(params[3]);
        ChessPosition endPosition = ChessPosition.convertToPosition(params[4]);
        ChessPiece.PieceType promotionType = ChessPiece.PieceType.valueOf(params[5]);

        //websocket
        ws = new WebSocketFacade(server.serverUrl, notificationHandler);
        ws.movePiece(authToken, gameID, startPosition, endPosition, promotionType);

        return String.format("You made the move %s to %s with your %s", startPosition.toString(), endPosition.toString(), pieceType.toString());
    }

    public String help()
    {
        return """
                redraw < BLACK | WHITE | OBSERVER > - the chess board
                leave <AUTH> <ID> - your current game
                move <AUTH> <ID> <START POSITION> <END POSITION> <PROMOTION TYPE> - one of your chess pieces
                resign <AUTH> <ID> - your current game
                highlight - possible moves for a piece
                help - with possible commands
                """;
    }

    /*public String redrawBoard(String ... params) throws ResponseException
    {
        if(params.length == 1)
        {
            if (params.equalsIgnoreCase("black"))
            {
                var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

                //draw it flipped with the board
                ChessBoard.drawSquaresFlipped(out, );
            }
            else if(params.equalsIgnoreCase("white") || params.equalsIgnoreCase("observer"))
            {
                //draw the board with white on the bottom
            }
        }
        throw new ResponseException("Expected different input");
    }
    */



}
