package server;

import dataAccess.*;
import server.handlers.*;
import server.requests.RegistrationRequest;
import service.RegistrationService;
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
            MySQLUserDAO sqlUser = new MySQLUserDAO();

            /*
            testing login

            RegistrationRequest request1 = new RegistrationRequest();
            request1.username = "syd";
            request1.password = "pw";
            request1.email = "askdfjh";

            //sqlUser.createUser(request1);

            RegistrationRequest login = new RegistrationRequest();
            login.username = "syd";
            login.password = "pw";
            try{
                RegistrationService.login(login);
            }
            catch(Exception ex)
            {
                stop();
            }
            */




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
