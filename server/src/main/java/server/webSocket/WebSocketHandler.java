package server.webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
//import com.mysql.cj.jdbc.ConnectionGroupManager;
//import dataAccess.MySQLGameDAO;
//import dataAccess.ResponseException;
import dataAccess.MySQLAuthDAO;
import model.GameData;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.requests.GameRequest;
import service.GameService;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

//import javax.websocket.Endpoint;
import java.io.IOException;
import java.util.Collection;

import static service.Service.authAccess;
import static service.Service.gameAccess;

@WebSocket
public class WebSocketHandler
{

    private final ConnectionHandler connections = new ConnectionHandler();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        //make a child class for each user game command that extends user game command
        //deserialize to a usergame command
        //based on the command type, then re-deserialize as the right command type

        //same thing for server message, should have base class servermessage with chiuld class
        //all variables should have same name as specs

        //right now every player has null username
        //user authtoken to get username
        //can use services or daos
        switch(command.getCommandType())
        {
            case JOIN_PLAYER:
                JoinPlayer joinPlayerCommand = new Gson().fromJson(message, JoinPlayer.class);
                joinPlayer(joinPlayerCommand, session);
                break;
            //case JOIN_OBSERVER -> joinObserver(command.getUserName(), session);

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
    private void joinPlayer(JoinPlayer command, Session session) throws Exception {

        String playerName = authAccess.getUser(command.getAuthString());

        connections.add(playerName, session);

        var message = String.format("%s has joined the game as the color %s", playerName, command.getTeamColor().toString());
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessageContents(message);
        //should make sure that this is actually broadcasting

        //modify the broadcast function to only send the notification to people in that game
        connections.broadcast(playerName, notification);

        //this is sent to everyone when updated
        ServerMessage message1 = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);

        //need to get the list of games and set the attribute to an actual game, so it can be deserialized
        GameRequest gamereq = new GameRequest();
        gamereq.authToken = command.getAuthString();

        ChessGame loadedGame = null;
        Collection<GameData> games= gameAccess.listGames(gamereq);
        for (GameData game: games)
        {
            if (game.gameID() == command.getGameID())
            {
                loadedGame = game.game();
            }
        }

        message1.setGame(loadedGame);

        //serialize the chess game
        //failing on this line in the actual test. I don't think it's going back to the client
        session.getRemote().sendString(new Gson().toJson(message1));


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
