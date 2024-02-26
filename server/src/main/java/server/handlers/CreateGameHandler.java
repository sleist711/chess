package server.handlers;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import server.requests.GameRequest;
import server.Result;
import service.GameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {

    public static Object handle(Request request, Response response)
    {
        Object newGame;
        try
        {
            var gameRequest = new Gson().fromJson(request.body(), GameRequest.class);
            if(gameRequest == null)
            {
                gameRequest = new GameRequest();
            }
            gameRequest.authToken = request.headers("Authorization");

            newGame = GameService.createGame(gameRequest);
            response.status(200);
        }
        catch(DataAccessException noAuth)
        {
            newGame = Result.convertToResult(noAuth.getMessage());
            response.status(401);
        }
        catch(BadRequestException nullAuth)
        {
            newGame = Result.convertToResult(nullAuth.getMessage());
            response.status(400);
        }
        catch(Exception otherException)
        {
            newGame = Result.convertToResult(otherException.getMessage());
            response.status(500);
        }
        return new Gson().toJson(newGame);
    }

}
