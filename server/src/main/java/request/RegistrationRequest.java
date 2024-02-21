package request;

public class RegistrationRequest extends Request{
    public String username;
    public String password;
    public String email;
    public static RegistrationRequest convertToRequest(spark.Request req)
    {
        //converts it to a json string
        String reqString = convertToString(req);

        //makes a request object from the json string
        RegistrationRequest newRequest = serializer.fromJson(reqString, RegistrationRequest.class);

        //does this fill in the variables in this class? how do i get the actual username and pw?

        return newRequest;
    }
}
