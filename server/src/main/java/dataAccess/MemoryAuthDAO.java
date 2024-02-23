package dataAccess;
import model.AuthData;
import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    private int nextID = 1;
    HashMap<String, AuthData> auth = new HashMap<>();

    public void clear()
    {
        auth.clear();
    }

    public String createAuth(String username)
    {
        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, username);

        auth.put(username, newAuth);
        //creates and returns an auth token, puts it in the map
        return authToken;
    }

}
