package handlers;

import request.GameRequest;
import request.RegistrationRequest;
import result.Result;
import service.GameService;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {

    public static Object handle(Request request, Response response) throws Exception
    {
        GameRequest gameRequest = GameRequest.convertToRequest(request);

        //call the right service
        String responseMessage = GameService.createGame(gameRequest);

        if(responseMessage.equals("{ \"message\": \"Error: unauthorized\" }"))
        {
            response.status(401);
        }
        else if (responseMessage.equals("{ \"message\": \"Error: bad request\" }"))
        {
            response.status(400);
        }
        else if (responseMessage.equals("{ \"message\": \"Error: Something went wrong.\" }"))
        {
            response.status(500);
        }
        else {
            response.status(200);
        }


        String createGameResult = Result.convertToResult(responseMessage);
        response.body(createGameResult);
        return createGameResult;
    }

}
