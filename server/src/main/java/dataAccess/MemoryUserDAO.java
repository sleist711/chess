package dataAccess;
import model.UserData;
import server.requests.RegistrationRequest;

public class MemoryUserDAO implements UserDAO{

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public boolean checkForUser(String username) {
        boolean userExists = false;

        if (users.containsKey(username)) {
            userExists = true;
        }

        return userExists;
    }

    public UserData createUser(RegistrationRequest regRequest) {
        UserData newUser = new UserData(regRequest.username, regRequest.password, regRequest.email);
        users.put(newUser.username(), newUser);
        return newUser;
    }
}
