package request;

import com.google.gson.Gson;


public class Request {

    static Gson serializer = new Gson();

    public static String convertToString(spark.Request req)
    {
        String reqString = req.body();
        return reqString;
    }

}
