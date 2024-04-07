package server.webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionGroupManager;
import dataAccess.MySQLGameDAO;
import dataAccess.ResponseException;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.io.ConnectionManager;
import org.eclipse.jetty.websocket.api.Session;
import server.requests.GameRequest;
import service.GameService;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.Endpoint;
import java.io.IOException;
import java.util.Collection;

@WebSocket
public class WebSocketHandler
{

    private final ConnectionHandler connections = new ConnectionHandler();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch(command.getCommandType())
        {
            case JOIN_PLAYER -> joinPlayer(command.getUserName(), session, command.getPlayerColor(), command.getGameID(), command.getAuthString());
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
    private void joinPlayer(String playerName, Session session, String playerColor, Integer currentGame, String authString) throws Exception {
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
        //need to get the list of games and set the attribute to an actual game so it can be deserialized
        GameRequest gamereq = new GameRequest();
        //gamereq.gameID = currentGame;
        ChessGame loadedGame = null;
        Collection<GameData> games= GameService.listGames(gamereq, authString);
        for (GameData game: games)
        {
            if (game.gameID() == currentGame)
            {
                loadedGame = game.game();
            }
        }

        message1.setGame(loadedGame);


        //serialize the chess game

        //failing on this line. I don't think it's going back to the client
        //session.getRemote().sendString("Sending a message");
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
