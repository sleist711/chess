package ui;


import com.google.gson.Gson;

import clientShared.ResponseException;
import clientShared.GameRequest;
import server.requests.GameResult;
import clientShared.RegistrationRequest;
import server.requests.RegistrationResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class ServerFacade {

    final String serverUrl;
    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void clear() throws ResponseException
    {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    public String login(RegistrationRequest loginRequest) throws ResponseException{

        var path = "/session";
        var response = this.makeRequest("POST", path, loginRequest, RegistrationResult.class, null);
        Repl.setAuth(response.authToken);
        return response.authToken;
    }

    public void logout(RegistrationRequest logoutRequest) throws ResponseException{

        var path = "/session";
        this.makeRequest("DELETE", path, logoutRequest, RegistrationResult.class, Repl.getAuth());
    }

    public String register(RegistrationRequest registerRequest) throws ResponseException{
        var path = "/user";
        var response = this.makeRequest("POST", path, registerRequest, RegistrationResult.class, null);
        Repl.setAuth(response.authToken);

        return response.authToken;
    }

    public String createGame(GameRequest gameRequest) throws ResponseException{
        var path = "/game";
        var response = this.makeRequest("POST", path, gameRequest, GameResult.class, Repl.getAuth());
        return response.gameID.toString();
    }

    public GameResult[] listGames(GameRequest gameRequest) throws ResponseException
    {
        var path = "/game";
        record listGameResponse(GameResult[] games) {
        }
        var response = this.makeRequest("GET", path, gameRequest, listGameResponse.class, Repl.getAuth());
        return response.games();
    }

    public void joinGame(GameRequest gameRequest) throws ResponseException
    {
        var path = "/game";
        this.makeRequest("PUT", path, gameRequest, GameResult.class, Repl.getAuth());
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String auth) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if(auth != null)
            {
                http.addRequestProperty("authorization", auth);
            }

            if (!method.equals("GET"))
            {
                writeBody(request, http);
            }

            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }


    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException("failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
