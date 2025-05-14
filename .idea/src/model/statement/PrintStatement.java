package model.statement;

import exceptions.*;
import model.ProgramState;
import model.expressions.Expression;
import model.type.Type;
import model.value.Value;
import utils.ITypeEnv;

public class PrintStatement implements Statement
{
    private Expression expression;

    public PrintStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws VariableNotDeclaredException, DivisionByZeroException, InvalidArithmeticOperandExpression, InvalidLogicOperandException
    {
        Value v = expression.eval(state.getSymtbl(), state.getHeap());
        state.getOutput().add(v);
        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "print(" + expression.toString() + ")";
    }
}
