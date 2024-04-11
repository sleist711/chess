package dataAccess;

import clientShared.ResponseException;
import model.UserData;
import requests.RegistrationRequest;

import java.util.HashMap;

public interface UserDAO {
    final public HashMap<String, UserData> users = new HashMap<>();

    public void clear() throws ResponseException;
    public boolean checkForUser(String username);

    public UserData createUser(RegistrationRequest regRequest) throws ResponseException;

    public String getPassword(String username) throws ResponseException;


}
