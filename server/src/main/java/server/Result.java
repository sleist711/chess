package server;

import spark.Response;

public class Result extends Response {

    String message;
    public Result(String message)
    {
        this.message = message;
    }

    public static Object convertToResult(String message)
    {
        Result returnResult = new Result(message);
        return returnResult;
    }
}

