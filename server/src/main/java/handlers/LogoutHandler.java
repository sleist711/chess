package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.AuthRequest;
import request.RegistrationRequest;
import result.Result;
import service.RegistrationService;
import spark.Request;
import spark.Response;

import javax.xml.crypto.Data;

public class LogoutHandler {

    public static Object handle(Request request, Response response) throws Exception {


        Object logoutUser;
        response.status(200);

        try {
            var logoutRequest = new Gson().fromJson(request.body(), AuthRequest.class);

            if(logoutRequest == null)
            {
                logoutRequest = new AuthRequest();
            }
            logoutRequest.authToken = request.headers("Authorization");


            // logoutUser = RegistrationService.logout(logoutRequest);
            //logoutUser = RegistrationService.logout(logoutRequest.authToken);
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
