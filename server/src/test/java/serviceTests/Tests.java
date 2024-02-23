package serviceTests;

import dataAccess.DataAccessException;
import request.ClearRequest;
import request.RegistrationRequest;
import service.*;
import org.junit.jupiter.api.Test;

public class Tests {


    @Test
    public void loginUserDoesntExist() throws Exception {

        ClearRequest clearRequest = new ClearRequest();
        ClearService.clear(clearRequest);

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        String result = RegistrationService.login(newRequest);

        if (!result.equals("{ message: Error: unauthorized }"))
        {
            throw(new Exception());
        }

    }

    @Test
    public void loginSuccess() throws Exception {
        ClearRequest clearRequest = new ClearRequest();
        ClearService.clear(clearRequest);

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";

        RegistrationService.register(newRequest);
        String result = RegistrationService.login(secondRequest);

        if (result.equals("{ message: Error: unauthorized }") || result.equals("{ message : Error: Something happened. Try again }"))
        {
            throw(new Exception());
        }

    }

    @Test
    public void wrongPassword() throws Exception
    {
        ClearRequest clearRequest = new ClearRequest();
        ClearService.clear(clearRequest);

        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "wrongpassword";

        RegistrationService.register(newRequest);
        String result = RegistrationService.login(secondRequest);

        if (!result.equals("{ message: Error: unauthorized }"))
        {
            throw(new Exception());
        }

    }
    @Test
    public void registerSuccess() throws Exception
    {
        //make a reg request
        RegistrationRequest newRequest = new RegistrationRequest();
        newRequest.username = "Sydney";
        newRequest.password = "password";
        newRequest.email = "sydney@gmail.com";

        RegistrationService.register(newRequest);

        //now check and make sure that the database actually contains that user
        if(!RegistrationService.userAccess.users.containsKey("Sydney"))
        {
            throw(new Exception());
        }
        //then you can call the service
    }
    @Test
    public void noDoubleUsers() throws Exception
    {
        //check that it won't register the same user twice
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "Sydney";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        RegistrationService.register(firstRequest);

        //try to add the same user again
        RegistrationRequest secondRequest = new RegistrationRequest();
        secondRequest.username = "Sydney";
        secondRequest.password = "password";
        secondRequest.email = "sydney@gmail.com";

        String result = RegistrationService.register(secondRequest);
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

        String result = RegistrationService.register(firstRequest);

        System.out.println(result);
        if(!result.equals("{ message: Error: Bad Request}"))
        {
            throw(new Exception());
        }
    }

    @Test
    public void clearAll() throws Exception
    {
        //put something in the database
        RegistrationRequest firstRequest = new RegistrationRequest();
        firstRequest.username = "Sydney";
        firstRequest.password = "password";
        firstRequest.email = "sydney@gmail.com";

        RegistrationService.register(firstRequest);

        ClearRequest clearRequest = new ClearRequest();
        ClearService clearService = new ClearService();
        clearService.clear(clearRequest);

        if(!ClearService.userAccess.users.isEmpty())
        {
            throw(new Exception());
        }

    }

}
