package ui;

import dataAccess.ResponseException;
import server.requests.RegistrationRequest;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Gameplay extends ChessClient{

    public Gameplay(String serverUrl)
    {
        super(serverUrl);
    }

    public String eval(String input) {
        //try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                //case "redraw" -> redrawBoard(params);

                default -> help();
            };
       // }
        //catch (ResponseException ex) {
           // return ex.getMessage();
        //}
    }

    public String help()
    {
        return """
                redraw < BLACK | WHITE | OBSERVER > - the chess board
                leave - your current game
                move - one of your chess pieces
                resign - your current game
                highlight - possible moves for a piece
                help - with possible commands
                """;
    }

    /*public String redrawBoard(String ... params) throws ResponseException
    {
        if(params.length == 1)
        {
            if (params.equalsIgnoreCase("black"))
            {
                var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

                //draw it flipped with the board
                ChessBoard.drawSquaresFlipped(out, );
            }
            else if(params.equalsIgnoreCase("white") || params.equalsIgnoreCase("observer"))
            {
                //draw the board with white on the bottom
            }
        }
        throw new ResponseException("Expected different input");
    }
    */



}
