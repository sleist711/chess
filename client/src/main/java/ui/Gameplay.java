package ui;

import WebSocket.WebSocketFacade;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.Arrays;

public class Gameplay extends ChessClient{

    public Gameplay(String serverUrl)
    {
        super(serverUrl);
    }

    public String eval(String input) throws Exception {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "move" -> movePiece(params);
                case "resign" -> resign(params);
                case "leave" -> leaveGame(params);

                default -> help();
            };
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
        ChessPiece.PieceType pieceType = null;

        switch(params[2].toLowerCase())
        {
            case "pawn":
                pieceType = ChessPiece.PieceType.PAWN;
                break;
            case "rook":
                pieceType = ChessPiece.PieceType.ROOK;
                break;
            case "knight":
                pieceType = ChessPiece.PieceType.KNIGHT;
                break;
            case "queen":
                pieceType = ChessPiece.PieceType.QUEEN;
                break;
            case "king":
                pieceType = ChessPiece.PieceType.KING;
                break;
            case "bishop":
                pieceType = ChessPiece.PieceType.BISHOP;
                break;
        }
        ChessPosition startPosition = ChessPosition.convertToPosition(params[3]);
        ChessPosition endPosition = ChessPosition.convertToPosition(params[4]);

        ChessPiece.PieceType promotionType = null;
        switch(params[5].toLowerCase())
        {
            case "pawn":
                pieceType = ChessPiece.PieceType.PAWN;
                break;
            case "rook":
                pieceType = ChessPiece.PieceType.ROOK;
                break;
            case "knight":
                pieceType = ChessPiece.PieceType.KNIGHT;
                break;
            case "queen":
                pieceType = ChessPiece.PieceType.QUEEN;
                break;
            case "king":
                pieceType = ChessPiece.PieceType.KING;
                break;
            case "bishop":
                pieceType = ChessPiece.PieceType.BISHOP;
                break;
        }

        //websocket
        ws = new WebSocketFacade(server.serverUrl, notificationHandler);
        ws.movePiece(authToken, gameID, startPosition, endPosition, promotionType);

        return String.format("You made the move %s to %s with your %s", startPosition.toString(), endPosition.toString(), pieceType.toString());
    }

    public String help()
    {
        return """
                redraw <AUTH> <ID> <BLACK | WHITE | OBSERVER> - the chess board
                leave <AUTH> <ID> - your current game
                move <AUTH> <ID> <PIECE> <START POSITION> <END POSITION> <PROMOTION TYPE> - one of your chess pieces
                resign <AUTH> <ID> - your current game
                highlight - possible moves for a piece
                help - with possible commands
                """;
    }

}
