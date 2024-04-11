package clientShared;

import server.requests.Request;

public class GameRequest extends Request {

    public Integer gameID;
    public String blackUsername;
    public String whiteUsername;
    public String authToken;
    public String gameName;
    public String playerColor;

}
