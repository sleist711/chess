package service;

import dataAccess.*;
import model.AuthData;
import request.AuthRequest;
import request.RegistrationRequest;

import javax.xml.crypto.Data;

public class RegistrationService extends Service {

    public static Object register(RegistrationRequest req) throws Exception {

        if(userAccess.checkForUser(req.username))
        {
            DataAccessException noUserException = new DataAccessException("Error: Already Taken");
            throw(noUserException);
        }
        if(req.username.equals("") || req.password.equals("") || req.email.equals(""))
        {
            BadRequestException noInputException = new BadRequestException("Error: Bad Request");
            throw(noInputException);
        }

        var newUser = userAccess.createUser(req);
        return authAccess.createAuth(newUser.username());
    }

    public static Object login(RegistrationRequest req) throws Exception
    {
        //checks to make sure that the corresponding username and password exist
        if (userAccess.users.containsKey(req.username)) {
            if ((userAccess.users.get(req.username).password()).equals(req.password)) {
                //updates the authtoken, returns the new one
               var authToken = authAccess.createAuth(req.username);
               return authToken;
            }
            //error if username and pw don't match
            else {
                BadRequestException nomatch = new BadRequestException("Error: unauthorized");
                throw (nomatch);
            }
        }
        //if the user doesn't exist
        else {
            BadRequestException nomatch = new BadRequestException("Error: unauthorized");
            throw (nomatch);
        }
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

