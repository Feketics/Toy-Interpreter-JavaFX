package exceptions;

public class SymTableEmptyException extends RuntimeException
{
    public SymTableEmptyException(String message)
    {
        super(message);
    }
}
