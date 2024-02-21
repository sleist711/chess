package handlers;

import request.ClearRequest;
import request.RegistrationRequest;
import result.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class RegistrationHandler
{
    public static Object handle(Request request, Response response) throws Exception {
    {
        //turn the request into json string
        //make it a java object
        RegistrationRequest regRequest = RegistrationRequest.convertToRequest(request);

        //call the right service
        RegistrationService regService = new RegistrationService();
        regService.register(regRequest);

        //working right here - it's going to return the authoken, need to convert it to be a response


        //convert response to json
        //String regResult = Result.convertToResult("You did it!");
        //send http response
        //response.status(200);
        //response.body(clearResult);
        return regResult;
    }
}
