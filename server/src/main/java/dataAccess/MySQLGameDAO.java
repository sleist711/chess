package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import server.requests.GameRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException("Unable to configure table");
        } catch (DataAccessException ex) {
            throw new ResponseException("Could not access database");
        }
    }

    private final String[] createStatements =
            {
                    "USE chess;",
                    "CREATE TABLE IF NOT EXISTS gamedata(gameID int NOT NULL AUTO_INCREMENT, whiteUsername varChar(255), blackUsername varChar(255), gameName varChar(255) NOT NULL, json text NOT NULL, PRIMARY KEY(gameID));"
            };

    public void clear() throws ResponseException {
        var statement = "TRUNCATE gameData";
        executeUpdate(statement);
    }

    ;


    public GameData createGame(GameRequest req) throws ResponseException {
        ChessGame newGame = new ChessGame();

        var statement = "INSERT INTO gamedata (whiteUsername, blackUsername, gameName, json) VALUES (?, ?, ?, ?)";
        var json = new Gson().toJson(newGame);


        //not sure if this will be returning the right id
        var id = executeUpdate(statement, req.whiteUsername, req.blackUsername, req.gameName, json);

        return new GameData(id, req.whiteUsername, req.blackUsername, req.gameName, newGame);
    }

    public Collection<GameData> listGames(GameRequest req) throws ResponseException {
        //working here
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {

            //depending on what listGames needs to return, this may be wrong
            var statement = "SELECT * FROM gamedata";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException("Unable to read data");
        }
        return result;
    }

    ;

    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var json = rs.getString("json");
        var chessGame = new Gson().fromJson(json, ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
    }

    public boolean checkForGame(Integer gameID) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * from gamedata WHERE gameID='" + gameID + "';";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var returnStatement = preparedStatement.executeQuery()) {
                    if (returnStatement.next()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                throw new ResponseException("Unable to read data");
            }
        } catch (DataAccessException | SQLException | ResponseException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void joinGame(GameRequest req, String userColor) throws ResponseException, DataAccessException {
        String currentWhiteUsername = null;
        String currentGameName = null;
        String currentChessGame = null;
        String currentBlackUsername=null;

        if (userColor.equals("BLACK")) {
            String newBlackUser = null;

            try (var conn = DatabaseManager.getConnection()) {
                var statement = "SELECT * FROM gameData WHERE gameID=?";
                try (var ps = conn.prepareStatement(statement)) {
                    ps.setInt(1, req.gameID);
                    try (var rs = ps.executeQuery()) {
                        if (rs.next()) {
                            currentWhiteUsername = rs.getString("whiteUsername");
                            currentGameName = rs.getString("gameName");
                            currentChessGame = rs.getString("json");
                        }
                    }
                }
                var statement2 = "SELECT username from authdata where authToken=?";
                try (var ps2 = conn.prepareStatement(statement2)) {
                    ps2.setString(1, req.authToken);
                    try (var rs2 = ps2.executeQuery()) {
                        if (rs2.next()) {
                            newBlackUser = rs2.getString("username");
                        }
                    }
                }
            } catch (SQLException ex) {
                throw new ResponseException("Unable to read data");
            }

            var statement3 = "UPDATE gameData SET blackUsername='" + newBlackUser + "' WHERE gameID='" + req.gameID + "';";
            executeUpdate(statement3);
        }
        else if (userColor.equals("WHITE"))
        {

            String newWhiteUser = null;

            try (var conn = DatabaseManager.getConnection()) {
                var statement = "SELECT * FROM gameData WHERE gameID=?";
                try (var ps = conn.prepareStatement(statement)) {
                    ps.setInt(1, req.gameID);
                    try (var rs = ps.executeQuery()) {
                        if (rs.next()) {
                            currentBlackUsername = rs.getString("blackUsername");
                            currentGameName = rs.getString("gameName");
                            currentChessGame = rs.getString("json");
                        }
                    }
                }
                var statement2 = "SELECT username from authdata where authToken=?";
                try (var ps2 = conn.prepareStatement(statement2)) {
                    ps2.setString(1, req.authToken);
                    try (var rs2 = ps2.executeQuery()) {
                        if (rs2.next()) {
                            newWhiteUser = rs2.getString("username");
                        }
                    }
                }
            } catch (SQLException ex) {
                throw new ResponseException("Unable to read data");
            }

            //GameData newGame = new GameData(req.gameID, currentWhiteUsername, newBlackUser, currentGameName, currentChessGame);
            var statement3 = "UPDATE gameData SET whiteUsername='" + newWhiteUser + "' WHERE gameID='" + req.gameID + "';";
            //need to figure out how to insert data at a specific index
            executeUpdate(statement3);
        }
    }

    public boolean existsBlackPlayer(int gameID)
    {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT blackUsername from gamedata WHERE gameID='" + gameID + "';";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var returnStatement = preparedStatement.executeQuery()) {
                    if (returnStatement.next()) {
                        if(returnStatement.getString("blackUsername") != null)
                        {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                throw new ResponseException("Unable to read data");
            }
        } catch (DataAccessException | SQLException | ResponseException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean existsWhitePlayer(int gameID)
    {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT whiteUsername from gamedata WHERE gameID='" + gameID + "';";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var returnStatement = preparedStatement.executeQuery()) {
                    if (returnStatement.next()) {
                        if(returnStatement.getString("whiteUsername") != null)
                        {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                throw new ResponseException("Unable to read data");
            }
        } catch (DataAccessException | SQLException | ResponseException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    private int executeUpdate(String statement, Object... params) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException("Unable to update database");
        }
    }
}



