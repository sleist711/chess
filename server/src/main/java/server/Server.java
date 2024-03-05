package server;

import dataAccess.*;
import server.handlers.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

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
