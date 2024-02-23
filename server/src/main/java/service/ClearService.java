package service;
import dataAccess.*;
import request.ClearRequest;

public class ClearService extends Service{


    public void clear(ClearRequest request)
    {
        //wipes out all data
        userAccess.clear();
        gameAccess.clear();
        authAccess.clear();
    }
}
