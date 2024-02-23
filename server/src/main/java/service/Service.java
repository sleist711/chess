package service;

import dataAccess.*;

public class Service {
    public static final UserDAO userAccess = new MemoryUserDAO();
    public static final GameDAO gameAccess = new MemoryGameDAO();
    public static final AuthDAO authAccess = new MemoryAuthDAO();
}
