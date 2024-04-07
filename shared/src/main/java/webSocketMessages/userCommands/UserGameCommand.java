package webSocketMessages.userCommands;

import chess.ChessGame;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public UserGameCommand(String authToken) {
        this.authToken = authToken;
    }

   // public ChessGame getGame() {
        //return this.currentGame;
    //}
    public Integer getGameID()
    {
        return this.gameID;
    }


    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;
    private String username;
    private String playerColor;
    //private ChessGame currentGame;
    private Integer gameID;

   // public void setGame(ChessGame game) {
       // this.currentGame = game;
    //}
    public void setGame(Integer game)
    {
        this.gameID = game;
    }

    public String getPlayerColor()
    {
        return this.playerColor;
    }

    public void setPlayerColor(String color)
    {
        this.playerColor = color;
    }

    public String getAuthString() {
        return authToken;
    }

    public void setUsername(String username){this.username = username;}
    public String getUserName() {return username;}
    public CommandType getCommandType() {
        return this.commandType;
    }

    public void setCommandType(UserGameCommand.CommandType type)
    {
        this.commandType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
