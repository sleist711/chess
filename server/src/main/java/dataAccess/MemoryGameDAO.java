package dataAccess;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
import request.GameRequest;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{

    private int nextID = 1;

    public GameData createGame(GameRequest req)
    {

        ChessGame newGame = new ChessGame();
        GameData newGameData = new GameData(nextID, req.whiteUsername, req.blackUsername, req.gameName, newGame);
        games.put(nextID, newGameData);
        nextID+=1;
        return newGameData;
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

    public boolean checkForGame(Integer gameID)
    {
        boolean gameExists = false;
        if(games.containsKey(gameID))
        {
            gameExists = true;
        }
        return gameExists;
    }

    public void joinGame(GameRequest req, String playerColor)
    {
        //see which color they want to be
        //if they want to be black
        if(playerColor.equals("BLACK"))
        {
            //figure out how to set these
            //maybe just create a whole new game object and replace it
            String currentWhiteUsername = games.get(req.gameID).whiteUsername();
            String currentGameName = games.get(req.gameID).gameName();
            ChessGame currentGame = games.get(req.gameID).game();
            GameData newGame = new GameData(req.gameID, currentWhiteUsername, req.blackUsername, currentGameName, currentGame);

            //now replace the game with the new one
            games.replace(req.gameID, newGame);

        }
        else if(playerColor.equals("WHITE"))
        {
            //games.get(req.gameID).whiteUsername = req.whiteUsername;
            String currentBlackUsername = games.get(req.gameID).blackUsername();
            String currentGameName = games.get(req.gameID).gameName();
            ChessGame currentGame = games.get(req.gameID).game();
            GameData newGame = new GameData(req.gameID, req.whiteUsername, currentBlackUsername, currentGameName, currentGame);
            //now replace the game with the new one
            games.replace(req.gameID, newGame);
        }
        else
        {
            //add them as an observer
        }

    }



    @Override
    public void clear() {
        games.clear();
    }
}
