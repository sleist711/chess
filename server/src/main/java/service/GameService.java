package service;

import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import model.GameData;
import request.GameRequest;
import request.RegistrationRequest;

import java.util.HashMap;

import static service.Service.authAccess;
import static service.Service.gameAccess;

public class GameService extends Service{

    public static String createGame(GameRequest req)
    {
        String responseMessage = "";
        Integer gameID = 0;

        try {
            if(req.authToken == null)
            {
                BadRequestException nullAuth = new BadRequestException("");
                throw(nullAuth);
            }
            //checks the authToken
            if (authAccess.checkAuthToken(req.authToken)) {
                //creates a new game
                gameID = gameAccess.createGame(req);
            }
            else
            {
                DataAccessException noAuth = new DataAccessException("");
                throw(noAuth);
            }
        }
        catch(DataAccessException noAuth)
        {
            //error 401
            responseMessage = "{ \"message\": \"Error: unauthorized\" }";
            return responseMessage;
        } catch(BadRequestException nullAuth)
        {
            //sooooo the problem is that the auth token is never getting to the actual service. I think it's something with the json
            //error 400
            responseMessage = "{ \"message\": \"Error: bad request\" } "+req.authToken;
           // System.out.println(req.authToken + "vs actual " + authAccess.getAuth(authAccess.getUser(req.authToken)));

            return responseMessage;
        } catch(Exception otherException)
        {
            //error 500
            	responseMessage = "{ \"message\": \"Error: Something went wrong.\" }";
                return responseMessage;
        }

        responseMessage = "{ \"gameID\": " + gameID + " }";
        return responseMessage;
    }

    public static String listGames(GameRequest req)
    {
        String responseMessage = "";

        try
        {
            if (req.authToken == null) {
                BadRequestException nullAuth = new BadRequestException("");
                throw(nullAuth);
            }

            //checks the authToken
            if (authAccess.checkAuthToken(req.authToken)) {
                //lists games
                responseMessage = gameAccess.listGames(req);
                return responseMessage;
            }
        }
        catch(BadRequestException nullAuth)
        {
            responseMessage = "{ \"message\": \"Error: unauthorized\" }";
            return responseMessage;
        }
        catch(Exception otherException)
        {
            responseMessage = "{ \"message\": \"Error: Something went wrong.\" }";
            return responseMessage;
        }

        return responseMessage;
    }

}
