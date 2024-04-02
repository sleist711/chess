package server;

import dataAccess.*;
import server.handlers.*;
import server.requests.RegistrationRequest;
import server.webSocket.WebSocketHandler;
import service.RegistrationService;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        WebSocketHandler webSocketHandler = new WebSocketHandler();
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/connect", webSocketHandler);

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", ClearHandler::handle);
        Spark.post("/user", RegistrationHandler::handle);
        Spark.post("/session", LoginHandler::handle);
        Spark.delete("/session", LogoutHandler::handle);
        Spark.post("/game", CreateGameHandler::handle);
        Spark.get("/game", GameListHandler::handle);
        Spark.put("/game", JoinGameHandler::handle);

        //initializing database here. not sure if correct
        try{
            DatabaseManager.createDatabase();
            MySQLAuthDAO sqlAuth = new MySQLAuthDAO();
            MySQLUserDAO sqlUser = new MySQLUserDAO();
            MySQLGameDAO sqlGame = new MySQLGameDAO();

        }
        catch(DataAccessException e)
        {
            stop();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
