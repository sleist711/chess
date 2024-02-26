package serviceTests;

import request.AuthRequest;
import request.GameRequest;
import request.RegistrationRequest;
import service.*;
import org.junit.jupiter.api.Test;

public class Tests {


    @Test
    public void loginUserDoesntExist() throws Exception {

        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        var result = RegistrationService.login(newRequest);

        if (!result.equals("{ message: Error: unauthorized }"))
        {
            throw(new Exception());
        }

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
        var result = RegistrationService.login(secondRequest);

        if (result.equals("{ message: Error: unauthorized }") || result.equals("{ message : Error: Something happened. Try again }"))
        {
            throw(new Exception());
        }

    }

    @Test
    public void wrongPassword() throws Exception
    {
        ClearService.clear();

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "wrongpassword";

        RegistrationService.register(newRequest);
        var result = RegistrationService.login(secondRequest);

        if (!result.equals("{ message: Error: unauthorized }"))
        {
            throw(new Exception());
        }

    }
    @Test
    public void registerSuccess() throws Exception
    {
        //make a reg request
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationService.register(newRequest);

        //now check and make sure that the database actually contains that user
        if(!RegistrationService.userAccess.users.containsKey("Sydney"))
        {
            throw(new Exception());
        }
        //then you can call the service
    }
    @Test
    public void noDoubleUsers() throws Exception
    {
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

        var result = RegistrationService.register(secondRequest);
        System.out.println(result);
        if(!result.equals("{ message: Error: Already taken}"))
        {
            throw(new Exception());
        }

    }

    @Test
    public void noUsername() throws Exception
    {
        //check that it won't register someone without a username
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        var result = RegistrationService.register(firstRequest);

        System.out.println(result);
        if(!result.equals("{ message: Error: Bad Request}"))
        {
            throw(new Exception());
        }
    }

    @Test
    public void clearAll() throws Exception
    {
        //put something in the database
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "Sydney";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        RegistrationService.register(firstRequest);

        ClearService clearService = new ClearService();
        clearService.clear();

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
        RegistrationService.logout(authRequest.authToken);

        //if(!result.equals("{}"))
        {
            throw(new Exception());
        }


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
        RegistrationService.logout(authRequest.authToken);

        //if(result.equals("{}"))
        {
            throw(new Exception());
        }

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

        GameService.createGame(newGameRequest);

        if(!GameService.gameAccess.games.isEmpty())
        {
            throw(new Exception());
        }
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

        var responseString = GameService.listGames(secondGameRequest, secondGameRequest.authToken);

        if(responseString.equals("{ message: Error: unauthorized }") || responseString.equals("{ message: Error: Something went wrong. }"))
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
        secondGameRequest.authToken = "1234";

        GameService.createGame(newGameRequest);
        GameService.createGame(secondGameRequest);

        var responseString = GameService.listGames(secondGameRequest, secondGameRequest.authToken);

        if(!responseString.equals("{ message: Error: unauthorized }"))
        {
            throw(new Exception());
        }

    }

}
