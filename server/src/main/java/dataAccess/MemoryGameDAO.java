package dataAccess;
import chess.ChessGame;
import model.GameData;
import server.requests.GameRequest;

import java.util.Collection;

import static service.Service.authAccess;

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

    public Collection<GameData> listGames(GameRequest req)
    {
        return games.values();
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
            String newBlackUser = authAccess.getUser(req.authToken);
            GameData newGame = new GameData(req.gameID, currentWhiteUsername, newBlackUser, currentGameName, currentGame);

            //now replace the game with the new one
            games.replace(req.gameID, newGame);

        }
        else if(playerColor.equals("WHITE"))
        {
            String currentBlackUsername = games.get(req.gameID).blackUsername();
            String currentGameName = games.get(req.gameID).gameName();
            ChessGame currentGame = games.get(req.gameID).game();
            String newWhiteUser = authAccess.getUser(req.authToken);
            GameData newGame = new GameData(req.gameID, newWhiteUser, currentBlackUsername, currentGameName, currentGame);
            //now replace the game with the new one
            games.replace(req.gameID, newGame);
        }

    }

    @Override
    public void clear() {
        games.clear();
    }
}
