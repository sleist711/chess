package dataAccess;

import model.GameData;
import server.GameRequest;

import java.util.Collection;
import java.util.HashMap;

public interface GameDAO {
    public void clear();
    HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame(GameRequest req);

    public Collection<GameData> listGames(GameRequest req);

    public boolean checkForGame(Integer gameID);

    public void joinGame(GameRequest req, String userColor);
}
