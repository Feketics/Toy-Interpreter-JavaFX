package exceptions;

public class VariableAlreadyDeclaredException extends RuntimeException
{
  public VariableAlreadyDeclaredException(String message)
  {
    super(message);
  }
}
