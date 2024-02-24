package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;

public interface AuthDAO {
    final public HashMap<AuthData, String> auth = new HashMap<>();

    public void clear();
    public String createAuth(String username);

    //returns the current authToken for that user
    public String getAuth(String username);

    public String getUser(String authToken);

    public boolean checkAuthToken(String authToken);


}
