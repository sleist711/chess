import chess.*;
import dataAccess.MySQLAuthDAO;
import dataAccess.MySQLGameDAO;
import dataAccess.MySQLUserDAO;
import server.Server;
import ui.Repl;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        var serverUrl = "http://localhost:8080";
        if (args.length == 1)
        {
            serverUrl = args[0];
        }

        Server chessServer = new Server();
        chessServer.run(8080);
        /*
        try{
            MySQLAuthDAO authDAO = new MySQLAuthDAO();
            authDAO.clear();
            MySQLGameDAO gameDAO = new MySQLGameDAO();
            gameDAO.clear();
            MySQLUserDAO userDAO = new MySQLUserDAO();
            userDAO.clear();
        }
        catch(Exception ex)
        {
            System.exit(0);
        }

         */


        new Repl(serverUrl).run();



    }
}