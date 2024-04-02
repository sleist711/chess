package server.webSocket;

import com.mysql.cj.jdbc.ConnectionGroupManager;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.client.io.ConnectionManager;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class WebSocketHandler {

    //private final ConnectionManager connections = new ConnectionGroupManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        return;
    }

    @OnWebSocketClose
    public void onClose(Session session)
    {
        return;
    }

    @OnWebSocketConnect
    public void onConnect(Session session)
    {
        return;
    }

    @OnWebSocketError
    public void onError(Throwable throwable)
    {
        return;
    }

    //send message
    //broadcast message
}
