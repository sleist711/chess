package dataAccess;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
import request.GameRequest;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{

    private int nextID = 0;

    public Integer createGame(GameRequest req)
    {
        nextID+=1;
        ChessGame newGame = new ChessGame();
        GameData newGameData = new GameData(nextID, req.whiteUsername, req.blackUsername, req.gameName, newGame);
        games.put(nextID, newGameData);
        return nextID;
    }

    public String listGames(GameRequest req)
    {
        String returnString = "{ \"games\": ";
        for (Map.Entry<Integer, GameData> entry : games.entrySet())
        {
            returnString += "[{\"gameID\":" + entry.getKey() + ", \"whiteUsername\":\""
                    + entry.getValue().whiteUsername() + "\", \"blackUsername\":\""
                    + entry.getValue().blackUsername()+"\", \"gameName:\""
                    + entry.getValue().gameName() +"\"} ] ";
        }
        return returnString;
    }


    @Override
    public void clear() {
        games.clear();
    }
}
