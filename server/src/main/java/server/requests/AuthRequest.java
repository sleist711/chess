package server.requests;

public class AuthRequest extends Request {
    public String authToken;

    public static AuthRequest convertToRequest(spark.Request req)
    {
        //converts it to a json string
        String reqString = convertToString(req);

        //makes a request object from the json string
        AuthRequest newRequest = serializer.fromJson(reqString, AuthRequest.class);

        if(newRequest == null)
        {
            newRequest = new AuthRequest();
        }

        newRequest.authToken = req.headers("Authorization");
        return newRequest;

    }
}
