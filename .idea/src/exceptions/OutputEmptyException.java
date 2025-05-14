package exceptions;

public class OutputEmptyException extends RuntimeException
{
    public OutputEmptyException(String message)
    {
        super(message);
    }
}
