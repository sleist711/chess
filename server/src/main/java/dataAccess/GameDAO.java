package dataAccess;

import model.GameData;
import request.GameRequest;

import java.util.HashMap;

public interface GameDAO {
    public void clear();
    HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame(GameRequest req);

    public String listGames(GameRequest req);

    public boolean checkForGame(Integer gameID);

    public void joinGame(GameRequest req, String userColor);
}
