package model.statement;

import model.ProgramState;
import model.type.Type;
import utils.ITypeEnv;
import utils.IExecutionStack;

public class CompStatement implements Statement
{
    private Statement first, second;

    public CompStatement(Statement first, Statement second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        IExecutionStack<Statement> stack = state.getExecstack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        return this.second.typeCheck(this.first.typeCheck(typeEnv));
    }

    @Override
    public Statement deepCopy()
    {
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString()
    {
        return "COMP{" + first.toString() + "; " + second.toString() + "}";
    }

    public Statement getFirst()
    {
        return first;
    }

    public Statement getSecond()
    {
        return second;
    }
}
