package handlers;

import com.google.gson.Gson;
import request.Request;
import result.Result;
import spark.Response;
import spark.Route;

public class Handler implements Route {

    public Handler ()
    {
    }

    @Override
    public Object handle(spark.Request request, Response response) throws Exception {
        return null;
    }

    /*
    The server handler classes serve as a translator between HTTP and Java.
    Your handlers will convert an HTTP request into Java usable objects & data.
    The handler then calls the appropriate service. When the service responds
    it converts the response object back to JSON and sends the HTTP response.
     */

    /* this class is meant to be inherited from. Create a method that converts into the
    right request. Create a method for serializing
    and making the response object JSON and sending the response
     */

    //Class classType;

   // Gson serializer = new Gson();

    //takes a result object, turns it into JSON
    //public String toJSON(Result myResult)
    //{
      //  var json = serializer.toJson(myResult);
        //return json;
    //}

    //deserialize - takes a request object from spark and returns the json string
    //public String toJSONString(Request req)
   // {
        //not sure this toString will work but we'll see
      //  String jsonString = req.toString();
        //return jsonString;
    //}


}
