package dataAccess;
import model.AuthData;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    public void clear()
    {
        auth.clear();
    }

    public AuthData createAuth(String username)
    {
        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, username);

        auth.put(newAuth, username);
        return newAuth;
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

        assert authToken != null;
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
