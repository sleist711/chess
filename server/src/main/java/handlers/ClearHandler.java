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
        //turn the request into json string
        //make it a java object

        //ClearRequest clearRequest = ClearRequest.convertToRequest(request);
        //var clearRequest = new Gson().fromJson(request.body(), ClearRequest.class);

        //call the right service
        ClearService clearService = new ClearService();
        //clearService.clear(clearRequest);

        clearService.clear();
        response.status(200);
        return "";

        /*
        var pet = new Gson().fromJson(req.body(), Pet.class);
        pet = service.addPet(pet);
        webSocketHandler.makeNoise(pet.name(), pet.sound());
        return new Gson().toJson(pet);
         */

        //convert response to json

        //String clearResult = Result.convertToResult("{}");
        //send http response
        //response.status(200);
        //return "";
        //return new Gson().toJson('{}');
        //return clearResult;
        //response.body(clearResult);
        //return clearResult;
    }


    //creates a clearRequest from the json
    //public Request createRequest(Request req)
    //{
      //  String jsonString = toJSONString(req);
        //ClearRequest request = serializer.fromJson(jsonString, ClearRequest.class);

        //return request;
    //}

    //also needs to create a response
}

