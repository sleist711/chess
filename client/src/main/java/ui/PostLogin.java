package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.ResponseException;
import server.requests.GameRequest;
import server.requests.RegistrationRequest;

import java.util.Arrays;

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
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
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

    public String joinGame(String ... params) throws ResponseException{
        if(params.length >= 1)
        {
            GameRequest newRequest = new GameRequest();
            newRequest.gameID = Integer.parseInt(params[0]);

            if(params.length == 2)
            {
                newRequest.playerColor = params[1];
            }

            server.joinGame(newRequest);
            return String.format("You joined the game as %s.", newRequest.playerColor);
        }
        throw new ResponseException("Expected more registration information.");
    }

    public String observeGame(String... params) throws ResponseException{
        if(params.length == 1)
        {
            joinGame(params);
            return ("You joined the game as an observer");
        }
        throw new ResponseException("You cannot include a color as an observer.");
    }

    public String logout(String ... params) throws ResponseException{
        if(params.length == 3)
        {

        }
        throw new ResponseException("Expected more registration information.");
    }

    public String help()
    {
        return """
                create <NAME> <AUTHTOKEN> - a game
                list <AUTHTOKEN> - games
                join <ID> [BLACK | WHITE | <empty>] - a game
                observe <ID> - a game
                quit - playing chess
                help - with possible commands
                """;
    }
}
