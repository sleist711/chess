package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    Integer gameID;


    public JoinObserver(String authToken) {
        super(authToken);
    }

    public void setGameID(Integer id)
    {
        this.gameID = id;
    }

    public Integer getGameID()
    {
        return this.gameID;
    }
}
