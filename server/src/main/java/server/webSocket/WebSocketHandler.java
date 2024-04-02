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

    //private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch(command.getCommandType())
        {
            //come back and fix this once the websocket is working
            case JOIN_PLAYER -> joinPlayer( );

        }
    }

    private void joinPlayer()
    {
        return;
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
