package service;

import clientShared.ResponseException;

public class ClearService extends Service{
    public static void clear() throws ResponseException
    {
        //wipes out all data
        userAccess.clear();
        gameAccess.clear();
        authAccess.clear();


    }
}
