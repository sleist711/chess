package webSocketMessages.serverMessages;

public class Notification extends ServerMessage{

    String message;

    public Notification(ServerMessageType type) {
        super(type);
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }
}
