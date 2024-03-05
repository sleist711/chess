package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;

public interface AuthDAO {
    final public HashMap<AuthData, String> auth = new HashMap<>();

    public void clear() throws ResponseException;
    public AuthData createAuth(String username) throws ResponseException;

    public  String getUser(String authToken) throws ResponseException;

    public boolean checkAuthToken(String authToken);
    public String getAuth(String username) throws ResponseException;



}
