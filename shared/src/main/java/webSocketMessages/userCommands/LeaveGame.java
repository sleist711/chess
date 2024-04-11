package webSocketMessages.userCommands;

public class LeaveGame extends UserGameCommand {
    public LeaveGame(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;

    }

    Integer gameID;

    public Integer getGameID()
    {
        return this.gameID;
    }
}
