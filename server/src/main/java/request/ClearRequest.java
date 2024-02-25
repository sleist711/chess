package request;

public class ClearRequest extends Request {

    public static ClearRequest convertToRequest(spark.Request req)
    {
        String reqString = convertToString(req);
        ClearRequest newRequest = serializer.fromJson(reqString, ClearRequest.class);
        //System.out.println(reqString);
        return newRequest;
    }



}
