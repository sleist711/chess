package handlers;

import request.AuthRequest;
import result.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class LogoutHandler {

    public static Object handle(Request request, Response response) throws Exception {

        AuthRequest logoutRequest = AuthRequest.convertToRequest(request);

        //call the right service
        String responseMessage = RegistrationService.logout(logoutRequest);
        System.out.println(responseMessage);
        if(responseMessage.equals("{ \"message\": \"Error: unauthorized\" }"))
        {
            response.status(401);
        }
        else if(responseMessage.equals("{ \"message\": \"Error: description\" }"))
        {
            response.status(500);
        }

        //always ends up returning 500 somehow
        else
        {
            response.status(200);
        }


        String logoutResult = Result.convertToResult(responseMessage);
        response.body(logoutResult);
        return logoutResult;


    }
}
