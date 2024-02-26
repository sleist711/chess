package dataAccess;
import model.UserData;
import request.RegistrationRequest;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Collection;

import static service.Service.authAccess;

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
