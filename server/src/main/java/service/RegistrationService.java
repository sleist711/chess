package service;

import dataAccess.*;
import model.AuthData;
import request.AuthRequest;
import request.RegistrationRequest;

import javax.xml.crypto.Data;

public class RegistrationService extends Service {

    public static String register(RegistrationRequest req)
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

    public static String login(RegistrationRequest req)
    {
        String responseMessage = "";
        //checks to make sure that the corresponding username and password exist
        try {
            if (userAccess.users.containsKey(req.username)) {
                if ((userAccess.users.get(req.username).password()).equals(req.password)) {
                    //updates the authtoken, returns the new one
                    String authToken = authAccess.createAuth(req.username);
                    responseMessage = "username:" + req.username + ", authToken: " + authToken;
                }
                //error if username and pw don't match
                else {
                    BadRequestException nomatch = new BadRequestException("That username and password don't match");
                    throw (nomatch);
                }

            }
            //if the user doesn't exist
            else {
                BadRequestException nomatch = new BadRequestException("That user doesn't exist");
                throw (nomatch);
            }
        }
        catch(BadRequestException nomatch)
        {
            responseMessage = "{ message: Error: unauthorized }";
        }
        //catch any other exception
        catch(Exception otherException)
        {
            responseMessage = "{ message : Error: Something happened. Try again }";
            return responseMessage;
        }

        return responseMessage;
    }

    public static String logout(AuthRequest req)
    {
        String responseMessage = "";

        try
        {
            //check that user exists

            String username = RegistrationService.authAccess.getUser(req.authToken);
            AuthData userToAccess = new AuthData(req.authToken, username);

            //still failing here
            if (authAccess.auth.containsKey(userToAccess))
            {
                AuthData updatedAuthData = new AuthData("", username);
                authAccess.auth.replace(updatedAuthData, username);//invalidate the auth token
            }
            else
            {
                DataAccessException noUser = new DataAccessException("That is not a valid authToken");
                throw(noUser);
            }
        }
        catch(DataAccessException noUser)
        {
            responseMessage = "{ \"message\": \"Error: unauthorized\" }";
            return responseMessage;
        }
        catch(Exception otherException)
        {
            responseMessage = "{ \"message\": \"Error: description\" }";
            return responseMessage;
        }

        responseMessage = "{}";
        return responseMessage;

    }

        //check that the auth token is valid
        //logging out just means invalidating the current auth token so that they have to log in to get a new one
}
