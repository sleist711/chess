package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    public Resign(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
    }

    public Integer getGameID()
    {
        return gameID;
    }

    Integer gameID;
}
