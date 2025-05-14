package exceptions;

public class InvalidArithmeticOperandExpression extends RuntimeException
{
    public InvalidArithmeticOperandExpression(String message)
    {
        super(message);
    }
}
