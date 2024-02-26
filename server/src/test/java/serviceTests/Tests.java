package serviceTests;

import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import model.GameData;
import server.requests.AuthRequest;
import server.requests.GameRequest;
import server.requests.RegistrationRequest;
import service.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void loginUserDoesntExist() {
        ClearService.clear();
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        Exception exception = assertThrows(BadRequestException.class, () -> {
            RegistrationService.login(newRequest);
        });

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void loginSuccess() throws Exception {
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);

        Object returned = RegistrationService.login(secondRequest);
        if(returned ==null)

        {
            throw (new Exception());
        }
    }

    @Test
    public void registerSuccess() throws Exception
    {
        ClearService.clear();
        //make a reg request
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationService.register(newRequest);

        if(!RegistrationService.userAccess.users.containsKey("Sydney"))
        {
            throw(new Exception());
        }
    }
    @Test
    public void noDoubleUsers() throws Exception
    {
        ClearService.clear();
        //check that it won't register the same user twice
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "Sydney";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        RegistrationService.register(firstRequest);

        //try to add the same user again
        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";
        secondRequest.email = "sydney@gmail.com";

        Exception exception = assertThrows(DataAccessException.class, () -> {
            RegistrationService.register(secondRequest);});

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void clearAll() throws Exception
    {
        ClearService.clear();
        //put something in the database
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "Sydney";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        RegistrationService.register(firstRequest);

        ClearService.clear();

        if(!ClearService.userAccess.users.isEmpty())
        {
            throw(new Exception());
        }
    }

    @Test
    public void logoutSuccess() throws Exception
    {
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        String myAuthToken = RegistrationService.authAccess.getAuth("Sydney");
        AuthRequest authRequest = new AuthRequest();
        authRequest.authToken = myAuthToken;
        //will throw an exception if not successful
        RegistrationService.logout(authRequest.authToken);
    }

    @Test
    public void logoutFail() throws Exception
    {
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        String myAuthToken = "12345";
        AuthRequest authRequest = new AuthRequest();
        authRequest.authToken = myAuthToken;

        Exception exception = assertThrows(DataAccessException.class, () -> {
            RegistrationService.logout(authRequest.authToken);});

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void createGameSuccess() throws Exception
    {
        //create user and log in
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        GameRequest newGameRequest = new GameRequest();
        newGameRequest.gameName = "myGame";
        newGameRequest.authToken = RegistrationService.authAccess.getAuth("Sydney");

        GameService.createGame(newGameRequest);

        if(GameService.gameAccess.games.isEmpty())
        {
            throw(new Exception());
        }

    }

    @Test
    public void createGameFail() throws Exception
    {
        //create user and log in
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        GameRequest newGameRequest = new GameRequest();
        newGameRequest.gameName = "myGame";
        newGameRequest.authToken = "1234";

        Exception exception = assertThrows(DataAccessException.class, () -> {
            GameService.createGame(newGameRequest);});

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void listGamesSuccess() throws Exception{
        //create user and log in
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        GameRequest newGameRequest = new GameRequest();
        newGameRequest.gameName = "myGame";
        newGameRequest.authToken = RegistrationService.authAccess.getAuth("Sydney");

        GameRequest secondGameRequest = new GameRequest();
        secondGameRequest.gameName = "mySecondGame";
        secondGameRequest.authToken = RegistrationService.authAccess.getAuth("Sydney");

        GameService.createGame(newGameRequest);
        GameService.createGame(secondGameRequest);

        Collection<GameData> responseString = GameService.listGames(secondGameRequest, secondGameRequest.authToken);

        if(responseString.isEmpty())
        {
            throw(new Exception());
        }

    }

    @Test
    public void listGamesFail() throws Exception
    {
        //create user and log in
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        GameRequest newGameRequest = new GameRequest();
        newGameRequest.gameName = "myGame";
        newGameRequest.authToken = RegistrationService.authAccess.getAuth("Sydney");

        GameRequest secondGameRequest = new GameRequest();
        secondGameRequest.gameName = "mySecondGame";
        secondGameRequest.authToken = RegistrationService.authAccess.getAuth("Sydney");

        GameRequest listGameRequest = new GameRequest();
        listGameRequest.authToken = "1234";

        GameService.createGame(newGameRequest);
        GameService.createGame(secondGameRequest);

        Exception exception = assertThrows(BadRequestException.class, () -> {
            GameService.listGames(listGameRequest, listGameRequest.authToken);});

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void joinGameFail() throws Exception
    {
        //create user and log in
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        GameRequest gameRequest = new GameRequest();
        gameRequest.playerColor = "Purple";

        Exception exception = assertThrows(DataAccessException.class, () -> {
            GameService.joinGame(gameRequest, gameRequest.playerColor);});

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void joinGameSuccess() throws Exception
    {
        //create user and log in
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        RegistrationService.login(secondRequest);

        GameRequest makeGame = new GameRequest();
        makeGame.gameName = "myGame";
        makeGame.authToken = RegistrationService.authAccess.getAuth("Sydney");
        GameService.createGame(makeGame);

        GameRequest gameRequest = new GameRequest();
        gameRequest.playerColor = "WHITE";
        gameRequest.gameID = 4;
        gameRequest.authToken = RegistrationService.authAccess.getAuth("Sydney");

        //will throw an exception if not successful
        GameService.joinGame(gameRequest, gameRequest.authToken);
    }

}
