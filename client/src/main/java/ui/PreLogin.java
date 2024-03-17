package ui;

import dataAccess.ResponseException;
import server.requests.RegistrationRequest;

import java.util.Arrays;


public class PreLogin extends ChessClient{

    public PreLogin(String serverUrl)
    {
        super(serverUrl);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws ResponseException
    {
        if(params.length == 2) {
            RegistrationRequest newRequest = new RegistrationRequest();
            newRequest.username = params[0];
            newRequest.password = params[1];

            String authToken = server.login(newRequest);
            state = State.SIGNEDIN;
            return String.format("Your authToken is %s", authToken);
        }
        throw new ResponseException("Expected more login information");
    }

    public String register(String ... params) throws ResponseException{
        if(params.length == 3)
        {
            RegistrationRequest newRequest = new RegistrationRequest();
            newRequest.username = params[0];
            newRequest.password = params[1];
            newRequest.email = params[2];

            String authToken = server.register(newRequest);
            return String.format("Your authToken is %s", authToken);
        }
        throw new ResponseException("Expected more registration information.");
    }

    public String help()
    {
        return """
                register <USERNAME><PASSWORD><EMAIL> - to create an account
                login <USERNAME><PASSWORD> - to play chess
                quit - playing chess
                help - with possible commands
                """;
    }

}
