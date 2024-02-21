package dataAccess;

public interface UserDAO {
    public void clear();
    public void checkForUser(String username);

    public void createUser(String username, String password, String email);
}
