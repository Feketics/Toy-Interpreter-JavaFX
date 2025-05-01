package model.statement;

import model.ProgramState;
import model.type.Type;
import utils.ExecutionStack;
import utils.ITypeEnv;

public class ForkStatement implements Statement
{
    private Statement statement;

    public ForkStatement(Statement statement)
    {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        return new ProgramState(new ExecutionStack<>(), state.getSymtbl().deepCopy(), state.getOutput(), state.getFileTable(), state.getHeap(), this.statement);
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        this.statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString()
    {
        return "Fork(" + statement.toString() + ")";
    }
}
