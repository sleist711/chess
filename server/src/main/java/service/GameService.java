package service;

import dataAccess.BadRequestException;
import dataAccess.AlreadyTakenException;
import dataAccess.DataAccessException;
import model.GameData;
import clientShared.GameRequest;
import java.util.Collection;
public class GameService extends Service{

    public static Object createGame(GameRequest req) throws Exception
    {
        Object newGame;

        if(req.authToken == null)
        {
            throw(new BadRequestException("Error: bad request"));
        }
        //checks the authToken
        if (authAccess.checkAuthToken(req.authToken)) {
            newGame = gameAccess.createGame(req);
        }
        else
        {
            throw(new DataAccessException("Error: unauthorized"));
        }
        return newGame;
    }

    public static Collection<GameData> listGames(GameRequest req, String authToken) throws Exception
    {
        if (authToken == null) {
            throw(new BadRequestException("Error: unauthorized"));
        }
        //checks the authToken
        if (authAccess.checkAuthToken(authToken)) {
            return gameAccess.listGames(req);

        }
        //if it's the wrong auth token
        else
        {
            throw(new BadRequestException("Error: unauthorized"));
        }
    }

    public static void joinGame(GameRequest req, String authToken) throws Exception
    {
        //check the auth token
        if (authAccess.checkAuthToken(authToken)) {
            //check that the game exists
            if (gameAccess.checkForGame(req.gameID)) {
                //see what color they want to be
                //if playercolor is null, add them as an observer here
                if(req.playerColor == null)
                {
                    return;
                }
                if (req.playerColor.equalsIgnoreCase("BLACK")) {
                    if (gameAccess.existsBlackPlayer(req.gameID)) {
                        //throw an error because they want to be black but there's already a player
                        throw (new AlreadyTakenException("Error: already taken"));
                    }
                    gameAccess.joinGame(req, req.playerColor);
                }

                else if (req.playerColor.equalsIgnoreCase("WHITE")) {
                    if (gameAccess.existsWhitePlayer(req.gameID))
                    {
                        //throw an error because they want to be white but there's already a player
                        throw (new AlreadyTakenException("Error: already taken"));
                    }
                    gameAccess.joinGame(req, req.playerColor);
                }

                //make sure that they don't want to be both colors
                else if (!req.playerColor.equalsIgnoreCase("WHITE") && !req.playerColor.equalsIgnoreCase("BLACK") && req.playerColor != null)
                {
                    //throw an error if it's not black or white
                    throw (new BadRequestException("Error: bad request"));
                }

            }
            //if game doesn't exist
            else {
                throw (new BadRequestException("Error: bad request"));
            }
        }
        //check if authToken is null or wrong
        else {
            throw (new DataAccessException("Error: unauthorized"));
        }
    }
}
