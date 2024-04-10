package WebSocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import dataAccess.ResponseException;

import javax.websocket.Endpoint;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Authentication;
import ui.ChessBoard;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.MakeMove;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class WebSocketFacade extends Endpoint {

    Session session;
    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try{
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {

                    ServerMessage receivedmessage = new Gson().fromJson(message, ServerMessage.class);

                    switch(receivedmessage.getServerMessageType())
                    {
                        case NOTIFICATION:
                        {
                            Notification outputMessage = new Gson().fromJson(message, Notification.class);
                            System.out.println(outputMessage.getMessage());
                            break;
                        }

                        case ERROR:
                        {
                            Error outputMessage = new Gson().fromJson(message, Error.class);
                            System.out.println(outputMessage.getErrorMessage());
                            break;
                        }
                        case LOAD_GAME:
                        {
                            //LoadGame outputMessage = new Gson().fromJson(message, LoadGame.class);
                            ChessGame loadedGame = new Gson().fromJson(message, ChessGame.class);
                            var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
                            ChessBoard.drawSquares(out, loadedGame.getBoard());
                            break;
                        }
                    }


                }
            });

        }
        catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    public void joinObserver(String auth) throws ResponseException
    {
        try {
            var command = new UserGameCommand(auth);
            command.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        }
        catch(IOException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(String authToken, String color, Integer currentGame) throws ResponseException {

        try {
            //create command message
            JoinPlayer newCommand = new JoinPlayer(authToken, currentGame, ChessGame.TeamColor.valueOf(color.toUpperCase()));
            newCommand.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);

            //send message to server
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch (IOException ex)
        {
            throw new ResponseException(ex.getMessage());
        }
    }

    public void leaveGame(String visitorName) throws ResponseException {
        return;
    }


    public void onError()
    {}

    public void movePiece(String authToken, Integer gameID, ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionType) throws ResponseException {
        try
        {
            ChessMove newMove = new ChessMove(startPosition, endPosition, promotionType);

            //create command
            MakeMove newCommand = new MakeMove(authToken, gameID, newMove);
            newCommand.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);

            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));

        }
        catch (IOException ex)
        {
            throw new ResponseException(ex.getMessage());
        }
    }
}
