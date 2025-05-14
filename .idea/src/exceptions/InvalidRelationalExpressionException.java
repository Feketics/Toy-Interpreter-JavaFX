package exceptions;

public class InvalidRelationalExpressionException extends RuntimeException
{
    public InvalidRelationalExpressionException(String message)
    {
        super(message);
    }
}
