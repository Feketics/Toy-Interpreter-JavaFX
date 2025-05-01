package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableAlreadyDeclaredException;
import model.ProgramState;
import model.type.Type;
import model.value.Value;
import utils.ITypeEnv;
import utils.ISymTable;

public class VarDeclStatement implements Statement
{
    private String id;
    private Type type;

    public VarDeclStatement(String id, Type type)
    {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws VariableAlreadyDeclaredException, InvalidTypeException
    {
        ISymTable<String, Value> symTbl = state.getSymtbl();

        if(symTbl.isDefined(id))
        {
            throw new VariableAlreadyDeclaredException("Variable " + id + " already exists");
        }

        symTbl.put(id, this.type.defaultValue());

        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        typeEnv.put(id, this.type);
        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new VarDeclStatement(this.id, this.type.deepCopy());
    }

    @Override
    public String toString()
    {
        return type.toString() + " " + id;
    }
}
