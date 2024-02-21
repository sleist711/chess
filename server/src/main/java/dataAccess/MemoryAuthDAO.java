package dataAccess;
import model.AuthData;
import java.util.HashMap;
public class MemoryAuthDAO implements AuthDAO {

    private int nextID = 1;
    HashMap<Integer, AuthData> auth = new HashMap<>();

    public void clear()
    {
        auth.clear();
    }
}
