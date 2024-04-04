package WebSocket;

import dataAccess.MySQLAuthDAO;
import dataAccess.MySQLUserDAO;
import dataAccess.ResponseException;

import javax.websocket.Endpoint;
import com.google.gson.Gson;
import spark.Spark;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try{
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            //might need to fix here
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    notificationHandler.notify(serverMessage);
                }
            });

        }
        catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    public void joinObserver(String auth, String username) throws ResponseException
    {
        try {
            var command = new UserGameCommand(auth);
            command.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            command.setUsername(username);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        }
        catch(IOException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(String authToken, String username, String color) throws ResponseException {

        try {
            //create command message
            UserGameCommand newCommand = new UserGameCommand(authToken);
            newCommand.setUsername(username);
            newCommand.setPlayerColor(color);
            newCommand.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);

            //send message to server
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch (IOException ex)
        {
            throw new ResponseException(ex.getMessage());
        }
    }

    public void leaveGame(String visitorName) throws ResponseException {
        return;
    }
}
