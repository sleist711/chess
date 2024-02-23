package service;

import dataAccess.*;

public class Service {
    public final UserDAO userAccess = new MemoryUserDAO();
    public final GameDAO gameAccess = new MemoryGameDAO();
    public final AuthDAO authAccess = new MemoryAuthDAO();
}
