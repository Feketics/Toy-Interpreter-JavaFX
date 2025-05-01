package model.statement;

import model.ProgramState;
import model.type.Type;
import utils.ITypeEnv;

public interface Statement
{
    ProgramState execute(ProgramState state);

    ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv);

    Statement deepCopy();
}
