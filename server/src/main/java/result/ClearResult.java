package result;

import com.google.gson.JsonObject;
import request.ClearRequest;

public class ClearResult{

    public static String convertToResult(String res)
    {

        String jsonResult = convertToResult(res);
        //ClearResult newRequest = serializer.fromJson(reqString, ClearRequest.class);
        return jsonResult;
    }
}
