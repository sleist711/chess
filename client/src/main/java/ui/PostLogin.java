package ui;

import WebSocket.WebSocketFacade;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.ResponseException;
import model.GameData;
import server.requests.GameRequest;
import server.requests.RegistrationRequest;
import service.GameService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class PostLogin extends ChessClient{

    public PostLogin(String serverUrl)
    {
        super(serverUrl);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> createGame(params);
                case "list" -> listGames(params);
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logout(params);
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createGame(String... params) throws ResponseException
    {
        if(params.length == 1)
        {
            GameRequest newRequest = new GameRequest();
            newRequest.gameName = params[0];

            String gameID = server.createGame(newRequest);
            return String.format("The gameID of the game %s is %s", newRequest.gameName, gameID);
        }
        throw new ResponseException("Expected more game information");
    }

    public String listGames(String ... params) throws ResponseException{
        GameRequest newRequest = new GameRequest();

        var games = server.listGames(newRequest);
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game : games) {
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();

    }

    public String joinGame(String ... params) throws Exception {
        if(params.length >= 1)
        {
            GameRequest newRequest = new GameRequest();
            String playerName = params[0];
            String authToken = params[1];
            newRequest.gameID = Integer.parseInt(params[2]);

            if(params.length == 4)
            {
                newRequest.playerColor = params[3];
            }

            //calls the server facade to join them to the game or verify it exists
            server.joinGame(newRequest);

            try {
                //get the list of current games
                Collection<GameData> currentGameList = GameService.listGames(newRequest, authToken);

                //if the game id matches the one they're joining
                for (GameData game : currentGameList) {
                    if (game.gameID() == newRequest.gameID) {
                        //set that one to the current game
                        currentGame = game.game();
                    }
                }
            }
            catch(Exception ex)
            {
                throw ex;
            }

            //set tehe player name
            playerName = newRequest.blackUsername;
            if(playerName == null)
            {
                playerName = newRequest.whiteUsername;
            }

            //Open a WebSocket connecion with the server (using the /connect endpoint) so it can send and receive gameplay messages.
            ws = new WebSocketFacade(server.serverUrl, notificationHandler);

            //Send either a JOIN_PLAYER or JOIN_OBSERVER WebSocket message to the server.
            if (newRequest.playerColor == null)
            {
                ws.joinObserver(authToken, playerName);
            }
            else
            {
                //pass in the chess game
               // ws.joinPlayer(authToken, playerName, newRequest.playerColor, currentGame);
                ws.joinPlayer(authToken, playerName, newRequest.playerColor, newRequest.gameID);
            }

            //transition to gameplay UI
            Repl.state = State.INPLAY;

            String[] startPosition = new String[]{"0"};

            //make a new board for the game

            //modify this to take in a chess board
            ChessBoard.main(startPosition);

            //update the current chess game
            //currentGame =

            return String.format("\nYou joined the game as %s.", newRequest.playerColor);
        }
        throw new ResponseException("Expected more registration information.");
    }

    public String observeGame(String... params) throws Exception {
        if(params.length == 1)
        {
            joinGame(params);

            Repl.state = State.INPLAY;
            String[] startPosition = new String[]{"0"};
            ChessBoard.main(startPosition);

            return ("You joined the game as an observer");
        }
        throw new ResponseException("You cannot include a color as an observer.");
    }

    public String logout(String ... params) throws ResponseException{
        RegistrationRequest newRequest = new RegistrationRequest();
        server.logout(newRequest);

        Repl.state = State.SIGNEDOUT;
        return ("You are logged out.");
    }

    public String help()
    {
        return """
                create <NAME> <AUTHTOKEN> - a game
                list <AUTHTOKEN> - games
                join <NAME> <AUTHTOKEN> <ID> [BLACK | WHITE | <empty>] - a game
                observe <ID> - a game
                quit - playing chess
                help - with possible commands
                """;
    }

}
