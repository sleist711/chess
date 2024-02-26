package service;

import dataAccess.*;
import model.AuthData;
import server.RegistrationRequest;

public class RegistrationService extends Service {

    public static Object register(RegistrationRequest req) throws Exception {

        if(userAccess.checkForUser(req.username))
        {
            throw(new DataAccessException("Error: Already Taken"));
        }
        if(req.username==null || req.password==null || req.email==null)
        {
            throw(new BadRequestException("Error: Bad Request"));
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
                return authAccess.createAuth(req.username);
            }
            //error if username and pw don't match
            else {
                throw (new BadRequestException("Error: unauthorized"));
            }
        }
        //if the user doesn't exist
        else {
            throw (new BadRequestException("Error: unauthorized"));
        }
    }

    public static void logout(String authToken) throws Exception
    {
        String username = RegistrationService.authAccess.getUser(authToken);
        AuthData userToAccess = new AuthData(authToken, username);

       //check that user exists
        if (authAccess.auth.containsKey(userToAccess))
        {
            authAccess.auth.remove(userToAccess, username); //invalidate the old auth token
        }
        else
        {
            throw(new DataAccessException("Error: unauthorized"));
        }
    }
}

