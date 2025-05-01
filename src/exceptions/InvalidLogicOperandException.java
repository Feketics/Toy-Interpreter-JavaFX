package exceptions;

public class InvalidLogicOperandException extends RuntimeException
{
    public InvalidLogicOperandException(String message)
    {
        super(message);
    }
}
