package server.handlers;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import server.requests.RegistrationRequest;
import server.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class RegistrationHandler {
    public static Object handle(Request request, Response response)
    {
        Object newUser;
        try {
            var registerUser = new Gson().fromJson(request.body(), RegistrationRequest.class);
            newUser = RegistrationService.register(registerUser);
        }
        catch(DataAccessException noUserException)
        {
            newUser = Result.convertToResult(noUserException.getMessage());
            response.status(403);
        }
        catch(BadRequestException noInputException)
        {
            response.status(400);
            newUser = Result.convertToResult(noInputException.getMessage());
        }
        catch(Exception otherException)
        {
            response.status(500);
            newUser = Result.convertToResult(otherException.getMessage());
        }
        return new Gson().toJson(newUser);
    }
}
