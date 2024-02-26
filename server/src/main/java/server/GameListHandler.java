package server;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import service.GameService;
import spark.Request;
import spark.Response;
import java.util.Map;

public class GameListHandler {

    public static Object handle(Request request, Response response)
    {
        Object gameList;

        try {
            var listRequest = new Gson().fromJson(request.body(), GameRequest.class);
            String authToken = request.headers("Authorization");

            response.type("application/json");
            var list = GameService.listGames(listRequest, authToken).toArray();
            response.status(200);
            return new Gson().toJson(Map.of("games", list));
        }
        catch(BadRequestException wrongAuth)
        {
            gameList = Result.convertToResult(wrongAuth.getMessage());
            response.status(401);
            return new Gson().toJson(gameList);
        }
        catch(Exception otherException)
        {
            gameList = Result.convertToResult(otherException.getMessage());
            response.status(500);
            return new Gson().toJson(gameList);
        }
    }

}
