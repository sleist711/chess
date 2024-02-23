package handlers;

import request.ClearRequest;
import request.RegistrationRequest;
import result.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class RegistrationHandler {
    public static Object handle(Request request, Response response) throws Exception
    {
        //turn the request into json string
        //make it a java object
        RegistrationRequest regRequest = RegistrationRequest.convertToRequest(request);

        //call the right service
        //RegistrationService regService = new RegistrationService();
        String responseMessage = RegistrationService.register(regRequest);

        String regResult;
        if (responseMessage.equals(""))
        {
            response.status(403);
        }

        else if (responseMessage.equals("{ message: Error: Bad Request}"))
        {
            response.status(400);
        }

        else if (responseMessage.equals("{ message : Error: Something happened. Try again }"))
        {
            response.status(500);
        }
        else {
            //successful registration
            response.status(200);
        }
        //successful registration
        regResult = Result.convertToResult(responseMessage);
        response.body(regResult);
        return regResult;

    }


}
