package server.handlers;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import server.requests.RegistrationRequest;
import server.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class LoginHandler {

    public static Object handle(Request request, Response response) {

        Object newUser;
        try {
            var loginUser = new Gson().fromJson(request.body(), RegistrationRequest.class);
            newUser = RegistrationService.login(loginUser);
            response.status(200);
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
    }
}
