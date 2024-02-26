package server.handlers;

import com.google.gson.Gson;
import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import server.GameRequest;
import server.Result;
import service.GameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {

    public static Object handle(Request request, Response response)
    {
        Object joinedGame;
        try {
            var gameToJoin = new Gson().fromJson(request.body(), GameRequest.class);

            if(gameToJoin == null)
            {
                gameToJoin = new GameRequest();
            }
            gameToJoin.authToken = request.headers("Authorization");

            GameService.joinGame(gameToJoin, gameToJoin.authToken);
            response.status(200);
        }
        catch(AlreadyTakenException alreadyTaken)
        {
            joinedGame = Result.convertToResult(alreadyTaken.getMessage());
            response.status(403);
            return new Gson().toJson(joinedGame);
        }
        catch(BadRequestException noColor)
        {
            joinedGame = Result.convertToResult(noColor.getMessage());
            response.status(400);
            return new Gson().toJson(joinedGame);
        }
        catch(DataAccessException wrongAuth)
        {
            joinedGame = Result.convertToResult(wrongAuth.getMessage());
            response.status(401);
            return new Gson().toJson(joinedGame);
        }
        catch(Exception otherException)
        {
            joinedGame = Result.convertToResult(otherException.getMessage());
            response.status(500);
            return new Gson().toJson(joinedGame);
        }
        return "";
    }
}
