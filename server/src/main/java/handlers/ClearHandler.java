package handlers;
import chess.ChessBoard;
import chess.ChessPosition;
import request.*;
import result.ClearResult;
import result.Result;
import service.*;
import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.Gson;

public class ClearHandler {

    public ClearHandler() {}

    public static Object handle(Request request, Response response) throws Exception {


        //call the right service
        ClearService clearService = new ClearService();
        clearService.clear();
        response.status(200);
        return "";

    }
}

