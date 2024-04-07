import chess.*;
import dataAccess.MySQLAuthDAO;
import dataAccess.MySQLGameDAO;
import dataAccess.MySQLUserDAO;
import server.Server;
import server.handlers.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

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

        Server testServer = new Server();
        testServer.run(8080);

    }
}