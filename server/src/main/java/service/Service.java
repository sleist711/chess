package service;

import clientShared.ResponseException;
import dataAccess.*;

public class Service {
    public static final UserDAO userAccess;
    static {
        try {
            userAccess = new MySQLUserDAO();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }
    public static final GameDAO gameAccess;
    static {
        try {
            gameAccess = new MySQLGameDAO();
        }
        catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }
    public static final AuthDAO authAccess;

    static {
        try {
            authAccess = new MySQLAuthDAO();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }


}
