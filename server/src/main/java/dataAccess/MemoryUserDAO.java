package dataAccess;
import model.UserData;

import java.util.HashMap;
import java.util.Collection;
public class MemoryUserDAO implements UserDAO{

    private int nextID = 1;
    //creates a map of users to ints
    final private HashMap<Integer, UserData> users = new HashMap<>();

    //clears out the map
    @Override
    public void clear() {
        users.clear();
    }
}
