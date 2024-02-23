package dataAccess;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Collection;
public class MemoryUserDAO implements UserDAO{

    private int nextID = 1;
    //creates a map of users to usernames
    //final public HashMap<String, UserData> users = new HashMap<>();

    //clears out the map
    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public boolean checkForUser(String username) {
        boolean userExists = false;

        //if user is already there, return true
        if (users.containsKey(username)) {
            userExists = true;
        }

        //otherwise, return and you're good to go
        return userExists;
    }

    public void createUser(String username, String password, String email)
    {
        //create userdata model
        UserData newUser = new UserData(username, password, email);
        //put it in the hashmap
        users.put(username, newUser);
    }




}
