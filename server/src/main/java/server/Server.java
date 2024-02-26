package server;

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
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
