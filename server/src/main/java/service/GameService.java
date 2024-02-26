package service;

import dataAccess.BadRequestException;
import dataAccess.AlreadyTakenException;
import dataAccess.DataAccessException;
import model.GameData;
import request.GameRequest;
import request.RegistrationRequest;

import java.util.Collection;
import java.util.HashMap;

import static service.Service.authAccess;
import static service.Service.gameAccess;

public class GameService extends Service{

    public static Object createGame(GameRequest req) throws Exception
    {

        Object newGame;

        if(req.authToken == null)
        {
            BadRequestException nullAuth = new BadRequestException("Error: bad request");
            throw(nullAuth);
        }
        //checks the authToken
        if (authAccess.checkAuthToken(req.authToken)) {
            //creates a new game
            newGame = gameAccess.createGame(req);
        }
        else
        {
            DataAccessException noAuth = new DataAccessException("Error: unauthorized");
            throw(noAuth);
        }
        return newGame;

    }



    public static Collection<GameData> listGames(GameRequest req, String authToken) throws Exception
    {

        if (authToken == null) {
            BadRequestException nullAuth = new BadRequestException("Error: unauthorized");
            throw(nullAuth);
        }

        //checks the authToken
        if (authAccess.checkAuthToken(authToken)) {
            //lists games
            return gameAccess.listGames(req);

        }
        //if it's the wrong auth token
        else
        {
            BadRequestException wrongAuth = new BadRequestException("Error: unauthorized");
            throw(wrongAuth);
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
                if (req.playerColor.equals("BLACK")) {
                    //problem is with the null i think
                    if (gameAccess.games.get(req.gameID).blackUsername() != null) {
                        //throw an error because they want to be black but there's already a player
                        AlreadyTakenException alreadyTaken = new AlreadyTakenException("Error: already taken");
                        throw (alreadyTaken);
                    }
                    gameAccess.joinGame(req, req.playerColor);

                }
                else if (req.playerColor.equals("WHITE")) {
                    if (gameAccess.games.get(req.gameID).whiteUsername() != null)
                    {
                        //throw an error because they want to be white but there's already a player
                        AlreadyTakenException alreadyTaken = new AlreadyTakenException("Error: already taken");
                        throw (alreadyTaken);
                    }
                    gameAccess.joinGame(req, req.playerColor);
                }
                //make sure that they don't want to be both colors
                else if (!req.playerColor.equals("WHITE") && !req.playerColor.equals("BLACK") && req.playerColor != null)
                {
                    //throw an error if it's not black or white
                    BadRequestException noColor = new BadRequestException("Error: bad request");
                    throw (noColor);
                }

            }
            //if game doesn't exist
            else {
                BadRequestException noGame = new BadRequestException("Error: bad request");
                throw (noGame);
            }
        }
        //check if authToken is null
        //check if authToken is wrong
        else {
            DataAccessException wrongAuth = new DataAccessException("Error: unauthorized");
                throw (wrongAuth);
        }

    }

}
