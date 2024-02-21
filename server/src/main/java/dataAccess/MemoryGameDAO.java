package dataAccess;
import model.GameData;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    private int nextID = 1;

    HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void clear() {
        games.clear();
    }
}
