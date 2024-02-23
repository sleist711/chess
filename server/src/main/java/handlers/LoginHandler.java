package handlers;

import request.RegistrationRequest;
import result.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class LoginHandler {

    public static Object handle(Request request, Response response) throws Exception
    {
        //turn the request into json string
        //make it a java object
        RegistrationRequest regRequest = RegistrationRequest.convertToRequest(request);

        //call the right service
        String responseMessage = RegistrationService.login(regRequest);

        String loginResult = "";
        //no user, user and pw don't match
        if(responseMessage.equals("{ message: Error: unauthorized }"))
        {
            response.status(401);
        }
        else if(responseMessage.equals("{ message : Error: Something happened. Try again }"))
        {
            response.status(500);
        }
        else
        {
            //successful login
            response.status(200);
        }

        loginResult = Result.convertToResult(responseMessage);
        response.body(loginResult);
        return loginResult;


    }
}
