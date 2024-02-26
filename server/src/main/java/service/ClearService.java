package service;

public class ClearService extends Service{
    public static void clear()
    {
        //wipes out all data
        userAccess.clear();
        gameAccess.clear();
        authAccess.clear();

    }
}
