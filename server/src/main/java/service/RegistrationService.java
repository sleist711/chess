package service;

import dataAccess.*;
import request.RegistrationRequest;

public class RegistrationService extends Service {

    public String register(RegistrationRequest req)
    {
        String responseMessage = "";
        //gets user, checks if it exists
        //put a try catch here. It's going to throw an error if the user
        //already exists
        try {
            if(userAccess.checkForUser(req.username))
            {
                DataAccessException noUserException = new DataAccessException("That user already exists");
                throw(noUserException);
            }
            if(req.username.equals("") || req.password.equals("") || req.email.equals(""))
            {
                BadRequestException noInputException = new BadRequestException("You're missing some information");
                throw(noInputException);
            }
            //I think I need to throw one more type of error

        }
        catch(DataAccessException noUserException)
        {
            responseMessage = "{ message: Error: Already taken}";
            return responseMessage;
        }
        catch(BadRequestException noInputException)
        {
            responseMessage = "{ message: Error: Bad Request}";
            return responseMessage;
        }
        catch(Exception otherException)
        {
            responseMessage = "{ message : Error: Something happened. Try again }";
            return responseMessage;
        }
        //creates user
        userAccess.createUser(req.username, req.password, req.email);

        //creates auth
        String authToken = authAccess.createAuth(req.username);
        responseMessage = "{ username:" + req.username + ", authToken:" + authToken + "}";
        //returns message
        return responseMessage;
    }
}
