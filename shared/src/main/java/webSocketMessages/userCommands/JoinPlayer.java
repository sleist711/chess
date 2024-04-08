package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand{
    private final Integer gameID;
    private final ChessGame.TeamColor playerColor;

    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor color) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = color;
    }

    public ChessGame.TeamColor getTeamColor()
    {
        return this.playerColor;
    }

    public Integer getGameID()
    {
        return this.gameID;
    }
}
