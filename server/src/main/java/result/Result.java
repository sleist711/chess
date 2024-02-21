package result;

import com.google.gson.Gson;
import spark.Response;

public class Result extends Response {
    boolean success;
    String message;
    static Gson serializer = new Gson();

    public static String convertToResult(String res)
    {
        //not sure if this is right
        String json = serializer.toJson(res);
        return json;
    }
}
