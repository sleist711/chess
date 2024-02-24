package dataAccess;
import model.AuthData;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    private int nextID = 1;
    //HashMap<String, AuthData> auth = new HashMap<>();

    public void clear()
    {
        auth.clear();
    }

    public String createAuth(String username)
    {
        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, username);

        //remove any other item in the map that has the same username
        auth.remove(getAuthData(username));
        //then, replace it with the new one
        auth.put(newAuth, username);
        //creates and returns an auth token, puts it in the map
        return authToken;
    }

    public AuthData getAuthData(String username)
    {
        AuthData authToken = null;
        for (Map.Entry<AuthData, String> entry : auth.entrySet())
        {
            if(username.equals(entry.getValue()))
            {
                authToken = entry.getKey();
            }
        }

        return authToken;
    }

    public String getAuth(String username)
    {
        AuthData authToken = null;
        for (Map.Entry<AuthData, String> entry : auth.entrySet())
        {
            if(username.equals(entry.getValue()))
            {
                authToken = entry.getKey();
            }
        }

        return authToken.authToken();
    }

    public String getUser(String authToken)
    {
        String username = null;
        for (Map.Entry<AuthData, String> entry : auth.entrySet())
        {
            if(authToken.equals(entry.getKey().authToken()))
            {
                username = entry.getValue();
            }
        }
        return username;
    }

    public boolean checkAuthToken(String authToken)
    {
        for(Map.Entry<AuthData, String> entry : auth.entrySet())
        {
            if(authToken.equals(entry.getKey().authToken()))
            {
                return true;
            }
        }
        return false;
    }



}
