package service;

import dataAccess.BadRequestException;
import dataAccess.AlreadyTakenException;
import dataAccess.DataAccessException;
import model.GameData;
import request.GameRequest;
import request.RegistrationRequest;

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



    public static String listGames(GameRequest req)
    {
        String responseMessage = "";

        try
        {
            if (req.authToken == null) {
                BadRequestException nullAuth = new BadRequestException("The authToken field is empty.");
                throw(nullAuth);
            }

            //checks the authToken
            if (authAccess.checkAuthToken(req.authToken)) {
                //lists games
                responseMessage = gameAccess.listGames(req);
                return responseMessage;
            }

            //if it's the wrong auth token
            else
            {
                BadRequestException wrongAuth = new BadRequestException("That is the wrong authToken.");
                throw(wrongAuth);
            }
        }
        catch(BadRequestException nullAuth)
        {
            responseMessage = "{ message: Error: unauthorized }";
            return responseMessage;
        }
        catch(Exception otherException)
        {
            responseMessage = "{ message: Error: Something went wrong. }";
            return responseMessage;
        }

    }

    public static String joinGame(GameRequest req)
    {
        String responseMessage = "";

        try {
            //check the auth token
            if (authAccess.checkAuthToken(req.authToken)) {
                //check that the game exists
                if (gameAccess.checkForGame(req.gameID)) {
                    String userColor = "";
                    //see what color they want to be
                    if (req.playerColor == null) {
                        //add them as an observer
                    }
                    else if (req.playerColor.equals("BLACK")) {
                        if (gameAccess.games.get(req.gameID).blackUsername() != null) {
                            //throw an error because they want to be black but there's already a player
                            AlreadyTakenException alreadyTaken = new AlreadyTakenException("Black is already taken");
                            throw (alreadyTaken);
                        }
                    } else if (req.playerColor.equals("WHITE")) {
                        if (gameAccess.games.get(req.gameID).whiteUsername() != null) {
                            //throw an error because they want to be white but there's already a player
                            AlreadyTakenException alreadyTaken = new AlreadyTakenException("White is already taken");
                            throw (alreadyTaken);
                        }
                    }
                    //make sure that they don't want to be both colors
                    else if (!req.playerColor.equals("WHITE") && !req.playerColor.equals("BLACK") && req.playerColor != null) {
                        //throw an error if it's not black or white
                        BadRequestException noColor = new BadRequestException("You need to choose a color.");
                        throw (noColor);
                    }

                    //if color is specified, add user as a player
                    //otherwise, add user as observer
                    gameAccess.joinGame(req, req.playerColor);

                }
                //if game doesn't exist
                else {
                    BadRequestException noGame = new BadRequestException("That game doesn't exist.");
                    throw (noGame);
                }
            }
            //check if authToken is null
            //check if authToken is wrong
            else if (!authAccess.checkAuthToken(req.authToken)) {
                DataAccessException wrongAuth = new DataAccessException("That authToken is invalid.");
                throw (wrongAuth);
            } else {
                Exception otherException = new Exception();
                throw (otherException);
            }
        }
        catch(AlreadyTakenException alreadyTaken)
        {
            //403
            responseMessage = "{ \"message\": \"Error: already taken\" }";
        }
        catch (BadRequestException badRequest)
        {
            //400
            responseMessage = "{ message: Error: bad request }";
        }
        catch(DataAccessException wrongAuth)
        {
            //401
            responseMessage = "{ message: Error: unauthorized }";
        }
        catch(Exception otherException)
        {
            //500
            responseMessage = "{ \"message\": \"Error: description\" }";
        }
        return responseMessage;
    }

}
