package ui;

import com.google.gson.Gson;
import dataAccess.ResponseException;
import model.AuthData;
import server.requests.GameRequest;
import server.requests.RegistrationRequest;
import server.requests.RegistrationResult;
import spark.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void clear() throws ResponseException
    {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null);
    }

    public String login(RegistrationRequest loginRequest) throws ResponseException{

        var path = "/session";
        //not sure this response class will work
        var response = this.makeRequest("POST", path, loginRequest, RegistrationResult.class);

        //not sure if this will return it the way that I want it to
        return response.authToken;
    }

    public String register(RegistrationRequest registerRequest) throws ResponseException{
        var path = "/user";
        var response = this.makeRequest("POST", path, registerRequest, RegistrationResult.class);
        return response.authToken;
    }

    public String createGame(GameRequest gameRequest) throws ResponseException{
        var path = "/game";
        var response = this.makeRequest("POST", path, gameRequest, Response.class);
        return response.toString();
    }

    public String listGames(GameRequest gameRequest, String authToken) throws ResponseException
    {
        var path = "/game";
        gameRequest.authToken = authToken;
        var response = this.makeRequest("GET", path, gameRequest, Response.class);
        return response.toString();
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
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
