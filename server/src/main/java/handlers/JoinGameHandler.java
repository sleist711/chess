package handlers;

import request.GameRequest;
import result.Result;
import service.GameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {

    public static Object handle(Request request, Response response) throws Exception
    {
        GameRequest gameRequest = GameRequest.convertToRequest(request);

        //call the right service
        String responseMessage = GameService.joinGame(gameRequest);

        if(responseMessage.equals("{ \"message\": \"Error: already taken\" }"))
        {
            response.status(403);
        }
        else if(responseMessage.equals("{ message: Error: bad request }"))
        {
            response.status(400);
        }
        else if(responseMessage.equals("{ message: Error: unauthorized }"))
        {
            response.status(401);
        }
        else if(responseMessage.equals("{ \"message\": \"Error: description\" }"))
        {
            response.status(500);
        }
        else
        {
            response.status(200);
        }

        //String joinGameResult = Result.convertToResult(responseMessage);
        //response.body(joinGameResult);
        //return joinGameResult;
        return "";
    }

}
