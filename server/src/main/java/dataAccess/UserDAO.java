package dataAccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    final public HashMap<String, UserData> users = new HashMap<>();

    public void clear();
    public boolean checkForUser(String username);

    public void createUser(String username, String password, String email);
}
