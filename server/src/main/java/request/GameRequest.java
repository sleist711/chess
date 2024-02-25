package request;

public class GameRequest extends Request{

    public Integer gameID;
    public String blackUsername;
    public String whiteUsername;
    public String authToken;
    public String gameName;
    public String playerColor;

    public static GameRequest convertToRequest(spark.Request req)
    {
        //converts it to a json string
        String reqString = convertToString(req);

        //makes a request object from the json string
        GameRequest newRequest = serializer.fromJson(reqString, GameRequest.class);

        if(newRequest == null)
        {
            newRequest = new GameRequest();
        }

        newRequest.authToken = req.headers("Authorization");
        //System.out.println(req.headers("Authorization"));
        return newRequest;
    }

}
