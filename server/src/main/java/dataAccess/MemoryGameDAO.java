package dataAccess;
import chess.ChessGame;
import clientShared.ResponseException;
import model.GameData;
import clientShared.GameRequest;

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

    public void joinGame(GameRequest req, String playerColor) throws ResponseException {
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

    public boolean existsBlackPlayer(int gameID) {
        if (games.get(gameID).blackUsername() != null) {
            return true;
        } else {
            return false;
        }

    }
    public boolean existsWhitePlayer(int gameID)
    {
        if (games.get(gameID).whiteUsername() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getBlackPlayer(int gameID) {
        return null;
    }

    @Override
    public String getWhitePlayer(int gameID) {
        return null;
    }

    @Override
    public void clear() {
        games.clear();
    }

    public void updateGame(GameRequest gameReq, String json) throws Exception
    {
    }

    @Override
    public void setBlackPlayer(GameRequest req, String playerName) throws ResponseException {
        return;

    }

    @Override
    public void setWhitePlayer(GameRequest req, String playerName) throws ResponseException {
        return;

    }
}
