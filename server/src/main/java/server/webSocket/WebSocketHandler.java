package server.webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionGroupManager;
import dataAccess.ResponseException;
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
            case JOIN_PLAYER -> joinPlayer(command.getUserName(), session, command.getPlayerColor(), command.getGame());
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
    //private void joinPlayer(String playerName, Session session, String playerColor, ChessGame currentGame) throws IOException
    private void joinPlayer(String playerName, Session session, String playerColor, Integer currentGame) throws IOException
    {
        //if there's no player name???
        if(playerName == null)
        {
            playerName = "unknown";
        }
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
        ServerMessage message1 = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        message1.setGame(currentGame);
        //try sending the messgae
        newConnection.send(new Gson().toJson(currentGame));
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
      //  throw new ResponseException(throwable.getMessage());
    //}

    //send message
    //broadcast message
}
