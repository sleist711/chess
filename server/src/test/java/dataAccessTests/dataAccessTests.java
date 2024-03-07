package dataAccessTests;

import dataAccess.AuthDAO;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.ResponseException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.requests.AuthRequest;
import server.requests.GameRequest;
import server.requests.RegistrationRequest;
import service.ClearService;
import service.GameService;
import service.RegistrationService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static service.Service.*;

public class dataAccessTests {

    //MySQLAuthDAO tests

    @Test
    public void authClear() {
        try{authAccess.clear();}
        catch(ResponseException ex)
        {
            fail();
        }
    }

    @Test
    public void authRemovePass() {

        try
        {
            AuthData user1 = authAccess.createAuth("sydney");
            authAccess.remove(user1, "sydney");
        }
        catch(ResponseException ex)
        {
            fail();
        }
    }

    @Test
    public void authRemoveFail() {

        try
        {
            AuthData user1 = authAccess.createAuth("sydney");
            authAccess.remove(user1, "notSydney");
        }
        catch(ResponseException ex)
        {
            String expectedMessage = "Error";
            String exMessage = ex.getMessage();
            assertTrue(exMessage.contains(expectedMessage));
        }
    }

    @Test public void createAuthSuccess()
    {
        String username = "sydney";
        AuthData authdata = null;
        try
        {
            authdata = authAccess.createAuth(username);
        }
        catch(ResponseException ex)
        {
            fail();
        }
        if(authdata.authToken().isEmpty()) {
            fail();

        }
    }

    @Test
    public void createAuthFail()
    {
        //make sure that this is actually failing!
        String username = "";
        AuthData authdata = null;
        try
        {
            authdata = authAccess.createAuth(username);
        }
        catch(ResponseException ex)
        {
            String expectedMessage = "Error";
            String actualMessage = ex.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Test
    public void getUserPass()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";

        String gottenUser = null;
        String authToken = null;

        try
        {
            authToken = authAccess.createAuth(req.username).authToken();
            gottenUser = authAccess.getUser(authToken);
        }
        catch(ResponseException ex)
        {
            fail();
        }
        assertTrue(!gottenUser.isEmpty());
    }

    @Test
    public void getUserFail()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";

        String gottenUser = null;
        String authToken = "abc";

        try
        {
            gottenUser = authAccess.getUser(authToken);
        }
        catch(ResponseException ex)
        {
            String exmessage = ex.getMessage();
            String expectedMessage = "Error";
            assertTrue(exmessage.contains(expectedMessage));
        }
    }

    @Test
    public void checkAuthTokenPass()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";

        boolean authTokenValid = false;
        String authToken = null;

        try
        {
            authToken = authAccess.createAuth(req.username).authToken();
            authTokenValid = authAccess.checkAuthToken(authToken);
        }
        catch(ResponseException ex)
        {
            fail();
        }
        assertTrue(authTokenValid);
    }

    @Test
    public void checkAuthTokenFail()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";

        boolean authTokenValid = false;
        String authToken = "abc";

        authTokenValid = authAccess.checkAuthToken(authToken);

        assertFalse(authTokenValid);
    }

    @Test
    public void getAuthPass()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";

        String gottenAuth = null;
        String authToken = null;

        try
        {
            authAccess.clear();
            authToken = authAccess.createAuth(req.username).authToken();
            gottenAuth = authAccess.getAuth(req.username);
        }
        catch(ResponseException ex)
        {
            fail();
        }
        assertTrue(authToken.equals(gottenAuth));
    }

    @Test
    public void getAuthFail()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";


        try
        {
            authAccess.createAuth(req.username);
            authAccess.getUser(req.username);
        }
        catch(ResponseException ex)
        {
            String expectedMessage = "Error";
            String exMessage = ex.getMessage();
            assertTrue(exMessage.contains(expectedMessage));
        }
    }


    /// MySQLUserDAO tests

    @Test
    public void userClear() {
        try{userAccess.clear();}
        catch(ResponseException ex)
        {
            fail();
        }
    }

    @Test
    public void checkUserPass()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";
        boolean userExists = false;

        try
        {
            userAccess.clear();
            userAccess.createUser(req);
            userExists = userAccess.checkForUser(req.username);
        }
        catch(ResponseException ex)
        {
            fail();
        }
        assertTrue(userExists);
    }

    @Test
    public void checkUserFail()
    {
        boolean userExists;

        userExists = userAccess.checkForUser("randomUser");
        assertFalse(userExists);
    }

    @Test
    public void createUserPass()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";

        try
        {
            userAccess.createUser(req);
        }
        catch(ResponseException ex)
        {
            fail();
        }
    }

    @Test
    public void createUserFail()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = null;
        req.password = "pw";
        req.email = "email";

        try
        {
            userAccess.createUser(req);
        }
        catch(ResponseException ex)
        {
            String expectedMessage = "Error";
            String exMessage = ex.getMessage();
            assertTrue(exMessage.contains(expectedMessage));
        }
    }

    @Test
    public void getPasswordPass()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "sydney";
        req.password = "pw";
        req.email = "email";

        String gottenPassword = null;
        try
        {
            userAccess.clear();
            userAccess.createUser(req);
            gottenPassword = userAccess.getPassword(req.username);

        }
        catch(ResponseException ex)
        {
            fail();
        }


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        assertTrue(encoder.matches(req.password, gottenPassword));
    }

    @Test
    public void getPasswordFail()
    {
        RegistrationRequest req = new RegistrationRequest();
        req.username = "notAUser";
        req.password = "pw";
        req.email = "email";

        try
        {
            userAccess.createUser(req);
            userAccess.getPassword(req.username);

        }
        catch(ResponseException ex)
        {
            String exmessage = ex.getMessage();
            String expectedMessage = "Error";
            assertTrue(exmessage.contains(expectedMessage));
        }
    }

    //MySQLGameDAO tests
    @Test
    public void gameClear() {
        try{gameAccess.clear();}
        catch(ResponseException ex)
        {
            fail();
        }
    }

    @Test
    public void createGamePass() {

        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "notAUser";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "WHITE";

        try
        {
            req.authToken = authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
        }
        catch(ResponseException ex)
        {
            fail();
        }
    }

    @Test
    public void createGameFail()
    {
        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "notAUser";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "WHITE";
        req.authToken = "abc";

        try
        {
            authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
        }
        catch(ResponseException ex)
        {
            String expectedMessage = "Error";
            String exMessage = ex.getMessage();

            assertTrue(exMessage.contains(expectedMessage));
        }

    }

    @Test
    public void listGamesPass() {

        try
        {
            gameAccess.clear();

            RegistrationRequest newRequest = new RegistrationRequest();
            newRequest.username = "Sydney";
            newRequest.password = "password";
            newRequest.email = "sydney@gmail.com";

            RegistrationRequest secondRequest = new RegistrationRequest();
            secondRequest.username = "Sydney";
            secondRequest.password = "password";

            String authToken = authAccess.createAuth("sydney").authToken();


            GameRequest newGameRequest = new GameRequest();
            newGameRequest.gameName = "myGame";
            newGameRequest.authToken = authToken;

            GameRequest secondGameRequest = new GameRequest();
            secondGameRequest.gameName = "mySecondGame";
            secondGameRequest.authToken = authToken;

            gameAccess.createGame(newGameRequest);
            gameAccess.createGame(secondGameRequest);

            Collection<GameData> responseString = gameAccess.listGames(secondGameRequest);

            assertFalse(responseString.isEmpty());
        }
        catch(ResponseException ex)
        {
            fail();
        }

    }

    @Test
    public void listGamesFail()
    {

        try{
            gameAccess.clear();

            RegistrationRequest newRequest = new RegistrationRequest();
            newRequest.username = "Sydney";
            newRequest.password = "password";
            newRequest.email = "sydney@gmail.com";

            String authToken = authAccess.createAuth("sydney").authToken();

            GameRequest newGameRequest = new GameRequest();
            newGameRequest.gameName = "myGame";
            newGameRequest.authToken = authToken;

            Collection<GameData> responseString = gameAccess.listGames(newGameRequest);

            assertTrue(responseString.isEmpty());
        }
        catch(ResponseException ex)
        {
            fail();
        }
    }

    @Test
    public void joinGamePass()
    {

        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "sydney";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "WHITE";
        req.gameID = 1;

        try
        {
            gameAccess.clear();

            req.authToken = authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
            gameAccess.joinGame(req, "WHITE");
        }
        catch(ResponseException | DataAccessException ex)
        {
            fail();
        }

    }

    @Test
    public void joinGameFail()
    {
        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "sydney";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "WHITE";

        try
        {
            gameAccess.clear();

            req.authToken = authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
            gameAccess.joinGame(req, "WHITE");
        }
        catch(ResponseException | DataAccessException ex)
        {
            String expectedMessage = "Error";
            String exMessage = ex.getMessage();
            assertTrue(exMessage.contains(expectedMessage));
        }

    }

    @Test
    public void existsBlackUserPass()
    {
        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "sydney";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "BLACK";
        req.gameID = 1;
        boolean blackExists = false;

        try
        {
            gameAccess.clear();

            req.authToken = authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
            gameAccess.joinGame(req, "BLACK");
            blackExists = gameAccess.existsBlackPlayer(req.gameID);

        }
        catch(ResponseException | DataAccessException ex)
        {
            fail();
        }
        assertTrue(blackExists);

    }
    @Test
    public void existsBlackUserFail()
    {
        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "sydney";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "BLACK";
        req.gameID = 1;

        try
        {
            gameAccess.clear();

            req.authToken = authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
            gameAccess.joinGame(req, "BLACK");
            gameAccess.existsBlackPlayer(0);
        }
        catch(ResponseException | DataAccessException ex)
        {
            String expectedMessage = "Error";
            String exMessage = ex.getMessage();
            assertTrue(exMessage.contains(expectedMessage));
        }

    }

    @Test
    public void existsWhiteUserPass()
    {
        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "sydney";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "WHITE";
        req.gameID = 1;
        boolean whiteExists = false;

        try
        {
            gameAccess.clear();

            req.authToken = authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
            gameAccess.joinGame(req, "WHITE");
            whiteExists = gameAccess.existsWhitePlayer(req.gameID);

        }
        catch(ResponseException | DataAccessException ex)
        {
            fail();
        }
        assertTrue(whiteExists);

    }

    @Test
    public void existsWhiteUserFail()
    {
        RegistrationRequest req1 = new RegistrationRequest();
        req1.username = "sydney";
        req1.password = "pw";
        req1.email = "email";

        GameRequest req = new GameRequest();
        req.gameName = "myGame";
        req.playerColor = "WHITE";
        req.gameID = 1;
        boolean whiteExists = false;

        try
        {
            gameAccess.clear();

            req.authToken = authAccess.createAuth(req1.username).authToken();
            gameAccess.createGame(req);
            gameAccess.joinGame(req, "WHITE");
            whiteExists = gameAccess.existsWhitePlayer(0);
        }
        catch(ResponseException | DataAccessException ex)
        {
            String expectedMessage = "Error";
            String exMessage = ex.getMessage();
            assertTrue(exMessage.contains(expectedMessage));
        }

    }



}
