package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage{

    Integer gameID;
    ChessGame game;
    public LoadGame(ServerMessageType type) {
        super(type);
    }

    public void setGame (ChessGame chessgame)
    {
        this.game = chessgame;
    }

    public void setGameID (Integer id)
    {
        this.gameID = id;
    }
}
