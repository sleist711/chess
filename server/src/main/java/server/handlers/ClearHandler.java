package server.handlers;
import com.google.gson.Gson;
import clientShared.ResponseException;
import server.Result;
import service.*;
import spark.Request;
import spark.Response;

public class ClearHandler {

    public ClearHandler() {}

    public static Object handle(Request request, Response response){

        Object message;
        try
        {
            ClearService.clear();
            response.status(200);
            message = "";
            return message;
        }
        catch(ResponseException ex)
        {
            response.status(500);
            message = Result.convertToResult(ex.getMessage());

        }
        return new Gson().toJson(message);



    }
}

