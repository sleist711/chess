package dataAccess;

import clientShared.ResponseException;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import clientShared.RegistrationRequest;
import java.sql.SQLException;

import static dataAccess.DatabaseManager.databaseName;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLUserDAO implements UserDAO{

    public MySQLUserDAO() throws ResponseException {
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


                "USE " + databaseName + ";",
                "CREATE TABLE IF NOT EXISTS userdata (username varChar(255) NOT NULL, password varChar(255) NOT NULL, email varChar(255) NOT NULL, PRIMARY KEY (username));"
        };
    public void clear() throws ResponseException {
        var statement = "TRUNCATE userdata";
        executeUpdate(statement);
    };
    public boolean checkForUser(String username)
    {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * from userdata WHERE username='" + username + "';";
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

    public UserData createUser(RegistrationRequest regRequest) throws ResponseException
    {
        //encrypt pw
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptPW = encoder.encode(regRequest.password);
        // write the hashed password in database along with the user's other information

        var statement = "INSERT into userdata values('" + regRequest.username + "', '" + encryptPW + "', '" + regRequest.email + "');";
        executeUpdate(statement);

        return new UserData(regRequest.username, regRequest.password, regRequest.email);
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
    public String getPassword(String username) throws ResponseException
    {
        String encryptPW = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT password FROM userdata WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        encryptPW = rs.getString("password");
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ResponseException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return encryptPW;
    }
}
