package handlers;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import request.RegistrationRequest;
import result.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class LoginHandler {

    public static Object handle(Request request, Response response) throws Exception {

        Object newUser;
        try {
            var loginUser = new Gson().fromJson(request.body(), RegistrationRequest.class);
            newUser = RegistrationService.login(loginUser);
        }
        catch(BadRequestException noMatch)
        {
            newUser = Result.convertToResult(noMatch.getMessage());
            response.status(401);
        }
        catch(Exception otherException)
        {
            response.status(500);
            newUser = Result.convertToResult(otherException.getMessage());
        }

        return new Gson().toJson(newUser);

        /*
        var pet = new Gson().fromJson(req.body(), Pet.class);
        pet = service.addPet(pet);
        webSocketHandler.makeNoise(pet.name(), pet.sound());
        return new Gson().toJson(pet);
         */



/*
        //turn the request into json string
        //make it a java object
        RegistrationRequest regRequest = RegistrationRequest.convertToRequest(request);

        //call the right service
        String responseMessage = RegistrationService.login();

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

        //loginResult = Result.convertToResult(responseMessage);
        return new Gson().toJson(responseMessage);
        //response.body(loginResult);
        //return loginResult;
*/

    }
}
