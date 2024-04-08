package WebSocket;

import dataAccess.ResponseException;

import javax.websocket.Endpoint;
import com.google.gson.Gson;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

    Session session;
    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try{
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    //I think I need multiple options of how to print out the message. Based on if it;s
                    //a notification or load game
                    System.out.println(message);
                    /*
                    //deserialize the new game and print it
                    ChessGame loadedGame = new Gson().fromJson(message, ChessGame.class);
                    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
                    ChessBoard.drawSquares(out, loadedGame.getBoard());
                }*/
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

    //public void joinPlayer(String authToken, String username, String color, ChessGame currentGame) throws ResponseException {
    public void joinPlayer(String authToken, String username, String color, Integer currentGame) throws ResponseException {

        try {
            //create command message
            UserGameCommand newCommand = new UserGameCommand(authToken);
            newCommand.setUsername(username);
            newCommand.setPlayerColor(color);
            newCommand.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            //newCommand.setGame(currentGame);
            newCommand.setGame(currentGame);


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


    public void onError()
    {}

}
