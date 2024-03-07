package dataAccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLAuthDAO implements AuthDAO{

    //initializing the table
    public MySQLAuthDAO() throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex)
        {
            throw new ResponseException("Unable to configure table");
        }
        catch (DataAccessException ex)
        {
            throw new ResponseException("Could not access database");
        }
    }
    private final String[] createStatements =
    {
        "USE chess;",
        "CREATE TABLE IF NOT EXISTS authdata (authToken varChar(255) NOT NULL, username text NOT NULL, PRIMARY KEY (authToken));"
        };

    public void clear() throws ResponseException {
        var statement = "TRUNCATE authdata";
        executeUpdate(statement);
    };
    public AuthData createAuth(String username) throws ResponseException {
        String authToken = UUID.randomUUID().toString();
        var statement = "INSERT into authdata values('" + authToken + "', '" + username + "');";
        executeUpdate(statement);

        return new AuthData(authToken, username);
    };

    public String getUser(String authToken) throws ResponseException
    {
        String username = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username FROM authdata WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString("username");
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ResponseException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return username;
    };

    public boolean checkAuthToken(String authToken) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * from authdata WHERE authToken='" + authToken + "';";
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

    public String getAuth(String username) throws ResponseException
    {
        String authToken = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken FROM authdata WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        authToken = rs.getString("authToken");
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ResponseException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return authToken;
    };

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
        }
        catch (SQLException | DataAccessException e) {
            throw new ResponseException("Unable to update database");
        }
    }

    public void remove(AuthData userToAccess, String username) throws ResponseException {
        //ok so I think it's removing all authtokens with that username, but it should just be doing it to one authtoken - the one that currently is logged in
        var statement = "DELETE FROM authdata WHERE authToken ='"+userToAccess.authToken()+"';";

        executeUpdate(statement);
    }
}
