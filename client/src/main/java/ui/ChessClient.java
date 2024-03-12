package ui;

import dataAccess.ResponseException;

import java.util.Arrays;

public class ChessClient {

    public class ChessClient {
        private String visitorName = null;
        private final ServerFacade server;
        private final String serverUrl;
        private State state = State.SIGNEDOUT;

        public ChessClient(String serverUrl)
        {
            server = new ServerFacade(serverUrl);
        }

        public String eval(String input)
        {
            try() {
                var tokens = input.toLowerCase().split(" ");
                var cmd = (tokens.length > 0) ? tokens[0] : "help";
                var params = Arrays.copyOfRange(tokens, 1, tokens.length);
                return switch (cmd) {
                    case "clear" -> clear();
                    default -> help();
                };
            }
            catch(ResponseException ex)
            {
                return ex.getMessage();
            }

        }

        public String clear() throws ResponseException {
            String buffer = "All games, pieces, and boards cleared.";
            server.clear();
            return buffer;
        }

        public String help()
        {
            if (state == State.SIGNEDOUT) {
                return """
                    - signIn <yourname>
                    - quit
                    """;
            }
            return """
                - list
                - adopt <pet id>
                - rescue <name> <CAT|DOG|FROG|FISH>
                - adoptAll
                - signOut
                - quit
                """;
        }
    }
}
