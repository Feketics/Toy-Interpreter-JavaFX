package exceptions;

public class ExecStackEmptyException extends RuntimeException
{
    public ExecStackEmptyException(String message)
    {
        super(message);
    }
}
