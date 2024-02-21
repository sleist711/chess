package service;

import dataAccess.*;
import request.RegistrationRequest;

public class RegistrationService {

    private final UserDAO userAccess = new MemoryUserDAO();
    private final GameDAO gameAccess = new MemoryGameDAO();
    private final AuthDAO authAccess = new MemoryAuthDAO();
    public String register(RegistrationRequest req)
    {
        //gets user, checks if it exists
        //put a try catch here. It's going to throw an error if the user
        //already exists
        userAccess.checkForUser(req.username);

        //creates user
        userAccess.createUser(req.username, req.password, req.email);

        //creates auth
        String authToken = authAccess.createAuth(req.username);

        //returns authtoken
        return authToken;
    }
}
