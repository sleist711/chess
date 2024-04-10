package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {
    public MakeMove(String authToken, Integer gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
    }

    Integer gameID;
    ChessMove move;

    public ChessMove getMove()
    {
        return move;
    }

    public Integer getGameID()
    {
        return gameID;
    }
}
