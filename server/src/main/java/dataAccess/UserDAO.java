package dataAccess;

import model.UserData;
import server.RegistrationRequest;

import java.util.HashMap;

public interface UserDAO {
    final public HashMap<String, UserData> users = new HashMap<>();

    public void clear();
    public boolean checkForUser(String username);

    public UserData createUser(RegistrationRequest regRequest);


}
