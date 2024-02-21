package request;

import com.google.gson.Gson;

//mport spark.Request;
public class Request {

    static Gson serializer = new Gson();

    public static String convertToString(spark.Request req)
    {
        //not sure if this is right
        String reqString = req.body();
        return reqString;
    }

}
