package exceptions;

public class RepositoryEmptyException extends RuntimeException
{
    public RepositoryEmptyException(String message)
    {
        super(message);
    }
}
