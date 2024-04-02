package WebSocket;

import server.Server;
import webSocketMessages.serverMessages.ServerMessage;

public interface NotificationHandler {
    void notify(ServerMessage message);
}
