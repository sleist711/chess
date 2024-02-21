package service;
import dataAccess.*;
import request.ClearRequest;

public class ClearService{

    private final UserDAO userAccess = new MemoryUserDAO();
    private final GameDAO gameAccess = new MemoryGameDAO();
    private final AuthDAO authAccess = new MemoryAuthDAO();

    public void clear(ClearRequest request)
    {
        //wipes out all data
        userAccess.clear();
        gameAccess.clear();
        authAccess.clear();
    }
}
