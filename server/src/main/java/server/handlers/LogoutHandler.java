package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import server.requests.AuthRequest;
import server.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class LogoutHandler {

    public static Object handle(Request request, Response response)
    {
        Object logoutUser;
        response.status(200);

        try {
            var logoutRequest = new Gson().fromJson(request.body(), AuthRequest.class);

            if(logoutRequest == null)
            {
                logoutRequest = new AuthRequest();
            }
            logoutRequest.authToken = request.headers("Authorization");

            RegistrationService.logout(logoutRequest.authToken);
        }
        catch(DataAccessException noUser)
        {
            response.status(401);
            logoutUser = Result.convertToResult(noUser.getMessage());
            return new Gson().toJson(logoutUser);
        }
        catch(Exception otherException)
        {
            response.status(500);
            logoutUser = Result.convertToResult(otherException.getMessage());
            return new Gson().toJson(logoutUser);
        }
        return "";
    }
}
