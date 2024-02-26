package server;
import service.*;
import spark.Request;
import spark.Response;

public class ClearHandler {

    public ClearHandler() {}

    public static Object handle(Request request, Response response){

        ClearService.clear();
        response.status(200);
        return "";
    }
}

