package webSocketMessages.serverMessages;

public class Error extends ServerMessage{

    String errorMessage;

    public Error(ServerMessageType type) {
        super(type);
    }

    public void setErrorMessage (String message)
    {
        errorMessage = message;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }
}
