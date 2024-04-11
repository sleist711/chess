package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import clientShared.GameRequest;
import server.requests.GameResult;
import clientShared.RegistrationRequest;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        var serverUrl = "http://localhost:" + port;
        facade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    public void clear() {
       try
       {
           facade.clear();
       }
       catch (Exception ex)
       {
          fail();
       }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    public void loginPass()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";
        var authData = "";

        try {
            facade.register(newRequest);
            authData = facade.login(newRequest);
        }
        catch(Exception ex)
        {
            fail();
        }

        assertTrue(authData.length() > 10);
    }

    @Test
    public void loginFail()
    {

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";

        RegistrationRequest loginRequest = new RegistrationRequest();
        loginRequest.username = "player1";
        loginRequest.password = "notpassword";

        try {
            facade.register(newRequest);
            facade.login(loginRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }

    }

    @Test
    void registerPass() throws Exception {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";

        var authData = facade.register(newRequest);
        assertTrue(authData.length() > 10);
    }

    @Test
    void registerFail() throws Exception {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";

        try {
            var authData = facade.register(newRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }
    }

    @Test
    public void logoutPass()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";
        String message = "";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            facade.logout(newRequest);
        }
        catch(Exception ex)
        {
            message = ex.getMessage();
        }

        assertTrue(message.equals(""));
    }

    @Test
    public void logoutFail()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";

        try {
            facade.logout(newRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }

    }

    @Test
    public void createGamePass()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";
        int id = 0;

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "sydsgame";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            id = Integer.parseInt(facade.createGame(gameRequest));
        }
        catch(Exception ex)
        {
            fail();
        }

        assertTrue(id > 0);
    }

    @Test
    public void createGameFail()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";
        int id = 0;

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "sydsgame";

        try {
            facade.register(newRequest);
            id = Integer.parseInt(facade.createGame(gameRequest));
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }
    }

    @Test
    public void listGamePass()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";
        GameResult[] gameList = new GameResult[0];

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "sydsgame";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
            gameList = facade.listGames(gameRequest);
        }
        catch(Exception ex)
        {
            fail();
        }

        assertTrue(gameList.length == 1);
    }

    @Test
    public void listGameFail()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";
        GameResult[] gameList = new GameResult[0];

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "sydsgame";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
            facade.logout(newRequest);
            gameList = facade.listGames(gameRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }

    }

    @Test
    public void joinGameFail()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";


        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "sydsgame";

        GameRequest joinGameRequest = new GameRequest();
        joinGameRequest.gameID = 4;
        joinGameRequest.playerColor = "white";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
            facade.joinGame(joinGameRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }
    }

    @Test
    public void joinGamePass()
    {
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "player1";
        newRequest.password = "password";
        newRequest.email = "p1@gmail.com";
        String message = "";

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "sydsgame";

        GameRequest joinGameRequest = new GameRequest();
        joinGameRequest.gameID = 1;
        joinGameRequest.playerColor = "white";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
            facade.joinGame(joinGameRequest);
        }
        catch(Exception ex)
        {
            message = ex.getMessage();
        }
        assertTrue(message.equals(""));
    }


}
