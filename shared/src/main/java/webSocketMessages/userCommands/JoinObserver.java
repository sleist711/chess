package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    Integer gameID;


    public JoinObserver(String authToken) {
        super(authToken);
    }

    public Integer getGameID()
    {
        return this.gameID;
    }
}
