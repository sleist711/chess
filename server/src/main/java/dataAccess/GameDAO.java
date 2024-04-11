package dataAccess;

import clientShared.ResponseException;
import model.GameData;
import requests.GameRequest;

import java.util.Collection;
import java.util.HashMap;

public interface GameDAO {
    public void clear() throws ResponseException;
    HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame(GameRequest req) throws ResponseException;

    public Collection<GameData> listGames(GameRequest req) throws ResponseException;

    public boolean checkForGame(Integer gameID);

    public void joinGame(GameRequest req, String userColor) throws ResponseException, DataAccessException;

    public boolean existsBlackPlayer(int gameID);
    public boolean existsWhitePlayer(int gameID);

    public String getWhitePlayer(int gameID);
    public String getBlackPlayer(int gameID);

    public void setWhitePlayer(GameRequest req, String playerName) throws ResponseException;
    public void setBlackPlayer(GameRequest req, String playerName) throws ResponseException;



    void updateGame(GameRequest req, String json) throws Exception;
}
