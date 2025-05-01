package model.statement;

import model.ProgramState;
import model.type.Type;
import utils.ITypeEnv;

public class NopStatement implements Statement
{

    public NopStatement(){}

    @Override
    public ProgramState execute(ProgramState state)
    {
        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new NopStatement();
    }

    @Override
    public String toString()
    {
        return "NopStatement";
    }
}
