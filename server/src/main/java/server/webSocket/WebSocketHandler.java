package server.webSocket;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionGroupManager;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.io.ConnectionManager;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final ConnectionHandler connections = new ConnectionHandler();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch(command.getCommandType())
        {
            case JOIN_PLAYER -> joinPlayer(command.getUserName(), session, command.getPlayerColor());
            case JOIN_OBSERVER -> joinObserver(command.getUserName(), session);

        }
    }

    private void joinObserver(String playerName, Session session) throws IOException
    {
        connections.add(playerName, session);
        var message = String.format("%s has joined the game as an observer", playerName);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessageContents(message);
        //should make sure that this is actually broadcasting
        connections.broadcast(playerName, notification);
    }
    private void joinPlayer(String playerName, Session session, String playerColor) throws IOException
    {
        connections.add(playerName, session);
        var message = String.format("%s has joined the game as the color %s", playerName, playerColor);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessageContents(message);
        //should make sure that this is actually broadcasting

        //modify the broadcast function to only send the notification to people in that game
        connections.broadcast(playerName, notification);

        //send message back to the root client (does this work?)
        Connection newConnection = new Connection(playerName, session);
        //put the game in the message
        //session.getremote thing from client
        //this is sent to everyoine when updated
        newConnection.send(new Gson().toJson(new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME)));
    }


    //@OnWebSocketClose
   // public void onClose(Session session)
    //{
     //   return;
   // }

    //@OnWebSocketConnect
    //public void onConnect(Session session)
   // {
    //    return;
    //}

    //@OnWebSocketError
    //public void onError(Throwable throwable)
    //{
    //    return;
   // }

    //send message
    //broadcast message
}
