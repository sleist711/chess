package dataAccess;

/**
 * Indicates there was a bad request
 */
public class BadRequestException extends Exception{
    public BadRequestException(String message) {
        super(message);
    }
}
