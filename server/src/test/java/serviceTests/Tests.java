package serviceTests;

import dataAccess.DataAccessException;
import request.ClearRequest;
import request.RegistrationRequest;
import service.*;
import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    public void test_clear() throws Exception
    {

        ClearRequest newRequest = new ClearRequest();
        ClearService newService = new ClearService();
        newService.clear(newRequest);
        if(!newService.userAccess.users.isEmpty())
        {
            throw(new Exception());
        }
    }

    @Test
    public void add_user() throws Exception
    {
        //make a spark request with the required info

        //make a reg request
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationService newService = new RegistrationService();
        newService.register(newRequest);

        //now check and make sure that the database actually contains that user
        if(!newService.userAccess.users.containsKey("Sydney"))
        {
            throw(new Exception());
        }
        //then you can call the service
    }
    @Test
    public void no_double_users() throws Exception
    {
        //check that it won't register the same user twice
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "Sydney";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        RegistrationService newService = new RegistrationService();
        newService.register(firstRequest);

        //try to add the same user again
        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";
        secondRequest.email = "sydney@gmail.com";

        String result = newService.register(secondRequest);
        System.out.println(result);
        if(!result.equals("{ message: Error: Already taken}"))
        {
            throw(new Exception());
        }

    }

    @Test
    public void noUsername() throws Exception
    {
        //check that it won't register someone without a username
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        RegistrationService newService = new RegistrationService();
        String result = newService.register(firstRequest);

        System.out.println(result);
        if(!result.equals("{ message: Error: Bad Request}"))
        {
            throw(new Exception());
        }
    }

}
