package server.webSocket;

import chess.ChessGame;
import chess.ChessPiece;
import chess.InvalidMoveException;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import model.GameData;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.requests.GameRequest;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;
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

        switch(command.getCommandType())
        {
            case JOIN_PLAYER:
                JoinPlayer joinPlayerCommand = new Gson().fromJson(message, JoinPlayer.class);
                joinPlayer(joinPlayerCommand, session);
                break;
            case JOIN_OBSERVER:
                JoinObserver joinObserverCommand = new Gson().fromJson(message, JoinObserver.class);
                joinObserver(joinObserverCommand, session);
                break;
            case MAKE_MOVE:
                MakeMove makeMoveCommand = new Gson().fromJson(message, MakeMove.class);
                makeMove(makeMoveCommand, session);
                break;
            case RESIGN:
                Resign resignCommand = new Gson().fromJson(message, Resign.class);
                resign(resignCommand, session);
                break;
            case LEAVE:
                LeaveGame leaveGameCommand = new Gson().fromJson(message, LeaveGame.class);
                leaveGame(leaveGameCommand, session);

        }
    }

    private void leaveGame(LeaveGame leaveGameCommand, Session session) throws Exception
    {
        String playerName = authAccess.getUser(leaveGameCommand.getAuthString());
        Integer gameID = leaveGameCommand.getGameID();

        GameRequest gamereq = new GameRequest();
        gamereq.authToken = leaveGameCommand.getAuthString();
        gamereq.gameID = gameID;


        //if they're the black player
        if (gameAccess.getBlackPlayer(gameID).equals(playerName))
        {
            gameAccess.setBlackPlayer(gamereq, null);
            //close connection
            connections.remove(playerName);
        }
        //if they're the white player
        else if (gameAccess.getWhitePlayer(gameID).equals(playerName))
        {
            gameAccess.setWhitePlayer(gamereq, null);
            //close connection
            connections.remove(playerName);
        }
        //if it's an observer, just close the websocket connection
        else
        {
            connections.remove(playerName);
        }

        //send notification to everyone in the game
        Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(String.format("%s left the game.", playerName));
        connections.sendAll(notification, leaveGameCommand.getGameID());
    }

    private void resign(Resign resignCommand, Session session) throws Exception
    {
        String playerName = authAccess.getUser(resignCommand.getAuthString());
        Integer gameID = resignCommand.getGameID();

        GameRequest gamereq = new GameRequest();
        gamereq.authToken = resignCommand.getAuthString();
        gamereq.gameID = gameID;

        ChessGame loadedGame = null;
        Collection<GameData> games= gameAccess.listGames(gamereq);

        for (GameData game: games)
        {
            if (game.gameID() == resignCommand.getGameID())
            {
                loadedGame = game.game();
            }
        }

        //if it's an observer that wants to resign
        if(!gameAccess.getBlackPlayer(gameID).equals(playerName) && !gameAccess.getWhitePlayer(gameID).equals(playerName))
        {
            //send error message
            var message = "Error: An observer cannot resign.";
            Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
            messageToSend.setErrorMessage(message);
            session.getRemote().sendString(new Gson().toJson(messageToSend));
            return;
        }

        //if someone has resigned already
        if(loadedGame.getTeamTurn() == null)
        {
            Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR);
            errorMessage.setErrorMessage("Error: This game is already over.");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }

        //set turn to null so no one can make a move
        loadedGame.setTeamTurn(null);

        //updates game in the database
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonGame = gson.toJson(loadedGame);
        gameAccess.updateGame(gamereq, jsonGame);

        //send notification to everyone in the game
        Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(String.format("%s resigned from the game.", playerName));
        //serialize the chess game
        connections.sendAll(notification, resignCommand.getGameID());
    }

    private void joinObserver(JoinObserver command, Session session) throws Exception
    {
        String playerName = authAccess.getUser(command.getAuthString());

        if(!gameAccess.checkForGame(command.getGameID()))
        {
            //send error message
            var message = "Error: That game does not exist.";
            Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
            messageToSend.setErrorMessage(message);

            String json = new Gson().toJson(messageToSend);
            session.getRemote().sendString(json);
        }

        //invalid authtoken
        else if(!authAccess.checkAuthToken(command.getAuthString()))
        {
            //send error message
            var message = "Error: That authtoken is invalid.";
            Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
            messageToSend.setErrorMessage(message);
            session.getRemote().sendString(new Gson().toJson(messageToSend));
        }
        else
        {
            //should only send back if there are no errors
            LoadGame messageToSend = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);

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

            messageToSend.setGame(loadedGame);

            session.getRemote().sendString(new Gson().toJson(messageToSend));

            connections.add(playerName, session, command.getGameID());
            var message = String.format("%s has joined the game as an observer", playerName);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
            notification.setMessage(message);

            connections.broadcast(playerName, notification, command.getGameID());
        }
    }
    private void joinPlayer(JoinPlayer command, Session session) throws Exception {

        String playerName = authAccess.getUser(command.getAuthString());

        //game exists
        if(!gameAccess.checkForGame(command.getGameID()))
        {
            //send error message
            var message = "Error: That game does not exist.";
            Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
            messageToSend.setErrorMessage(message);
            session.getRemote().sendString(new Gson().toJson(messageToSend));
            return;
        }
        //invalid authtoken
        else if(!authAccess.checkAuthToken(command.getAuthString()))
        {
            //send error message
            var message = "Error: That authtoken is invalid.";
            Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
            messageToSend.setErrorMessage(message);
            session.getRemote().sendString(new Gson().toJson(messageToSend));
            return;
        }
        else if(command.getTeamColor().toString().toLowerCase().equals("black"))
        {
            if(gameAccess.existsBlackPlayer(command.getGameID()))
            {
                //if the black player isn't the same as the player trying to join
                if (!gameAccess.getBlackPlayer(command.getGameID()).equals(playerName))
                {
                    //send error message
                    var message = "Error: That spot is already taken.";
                    Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
                    messageToSend.setErrorMessage(message);
                    session.getRemote().sendString(new Gson().toJson(messageToSend));
                    return;
                }
            }
        }
        else if(command.getTeamColor().toString().toLowerCase().equals("white"))
        {
            if(gameAccess.existsWhitePlayer(command.getGameID()))
            {
                //if the white player isn't the same as the player trying to join
                if (!gameAccess.getWhitePlayer(command.getGameID()).equals(playerName))
                {
                    //send error message
                    var message = "Error: That spot is already taken.";
                    Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
                    messageToSend.setErrorMessage(message);
                    session.getRemote().sendString(new Gson().toJson(messageToSend));
                    return;
                }
            }
        }
        //user actually is in the game
        if(command.getTeamColor().toString().toLowerCase().equals("black"))
        {
            if(!gameAccess.existsBlackPlayer(command.getGameID())) {
                //send error message
                var message = "Error: The game is empty";
                Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
                messageToSend.setErrorMessage(message);
                session.getRemote().sendString(new Gson().toJson(messageToSend));
                return;
            }
        }
        if(command.getTeamColor().toString().toLowerCase().equals("white"))
        {
            if(!gameAccess.existsWhitePlayer(command.getGameID())) {
                //send error message
                var message = "Error: The game is empty";
                Error messageToSend = new Error(ServerMessage.ServerMessageType.ERROR);
                messageToSend.setErrorMessage(message);
                session.getRemote().sendString(new Gson().toJson(messageToSend));
                return;
            }
        }

        //should only send back if there are no errors
         LoadGame messageToSend = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);

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

        messageToSend.setGame(loadedGame);
        connections.add(playerName, session, command.getGameID());
        var message = String.format("%s has joined the game as the color %s", playerName, command.getTeamColor().toString());
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);

        //modify the broadcast function to only send the notification to people in that game!!!!
        connections.broadcast(playerName, notification, command.getGameID());

        //serialize the chess game
        session.getRemote().sendString(new Gson().toJson(messageToSend));

    }

    private void makeMove(MakeMove makeMoveCommand, Session session) throws Exception
    {
        String playerName = authAccess.getUser(makeMoveCommand.getAuthString());

        //get the right game
        ChessGame loadedGame = null;

        GameRequest gamereq = new GameRequest();
        gamereq.authToken = makeMoveCommand.getAuthString();
        gamereq.gameID = makeMoveCommand.getGameID();

        Collection<GameData> games= gameAccess.listGames(gamereq);
        for (GameData game: games)
        {
            if (game.gameID() == makeMoveCommand.getGameID())
            {
                loadedGame = game.game();
            }
        }

        //make sure the player isn't an observer
        if(!gameAccess.getWhitePlayer(gamereq.gameID).equals(playerName) && !gameAccess.getBlackPlayer(gamereq.gameID).equals(playerName))
        {
            Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR);
            errorMessage.setErrorMessage("Error: You are just observing this game.");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }

        //if someone has resigned
        if(loadedGame.getTeamTurn() == null)
        {
            Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR);
            errorMessage.setErrorMessage("Error: This game is over.");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }
        //if they're trying to move a piece that isn't theirs
        ChessPiece pieceToMove = loadedGame.myBoard.myChessBoard[makeMoveCommand.getMove().getStartPosition().getColumn()][makeMoveCommand.getMove().getStartPosition().getRow()];
        if(pieceToMove.getTeamColor() == ChessGame.TeamColor.WHITE && !gameAccess.getWhitePlayer(gamereq.gameID).equals(playerName) )
        {
            Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR);
            errorMessage.setErrorMessage("Error: You are just observing this game.");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }
        else if(pieceToMove.getTeamColor() == ChessGame.TeamColor.BLACK && !gameAccess.getBlackPlayer(gamereq.gameID).equals(playerName))
        {
            Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR);
            errorMessage.setErrorMessage("Error: You are just observing this game.");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }

        //makes the move
        assert loadedGame != null;

        try {
            loadedGame.makeMove(makeMoveCommand.getMove());
        }
        catch(InvalidMoveException ex)
        {
            Error errorMessage = new Error(ServerMessage.ServerMessageType.ERROR);
            errorMessage.setErrorMessage("Error: Invalid move");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }

        //updates database
        gameAccess.updateGame(gamereq, new Gson().toJson(loadedGame));

        //send loadGame message to everyone in the game
        LoadGame messageToSend = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);
        messageToSend.setGame(loadedGame);
        //serialize the chess game
        connections.sendAll(messageToSend, makeMoveCommand.getGameID());

        //send notification to everyone but the root client
        connections.add(playerName, session, makeMoveCommand.getGameID());
        var notifMessage = String.format("%s made the move %s to %s", playerName, makeMoveCommand.getMove().getStartPosition().toString(), makeMoveCommand.getMove().getEndPosition().toString());
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(notifMessage);

        //modify the broadcast function to only send the notification to people in that game!!!!
        connections.broadcast(playerName, notification, makeMoveCommand.getGameID());
    }
}




//TO DO
//fix observe option