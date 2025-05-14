package model.statement;

import exceptions.FileAlreadyOpenException;
import exceptions.FileNotFoundException;
import exceptions.InvalidTypeException;
import model.ProgramState;
import model.expressions.Expression;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.IFileTable;
import utils.ISymTable;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFileStatement implements Statement
{
    private Expression expression;

    public OpenRFileStatement(Expression exp)
    {
        this.expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws FileNotFoundException, InvalidTypeException, FileAlreadyOpenException
    {
        ISymTable<String, Value> symTbl = state.getSymtbl();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value v = this.expression.eval(symTbl, state.getHeap());
        if(!(v.getType() instanceof StringType))
            throw new InvalidTypeException("The expression given in the OpenRFile statement is not a string.");

        StringValue sv = new StringValue("src/files/" + ((StringValue)v).getValue());
        if(fileTable.isDefined(sv))
            throw new FileAlreadyOpenException("The file " + sv.getValue() + " is already open.");

        FileReader fr;
        try
        {
            fr = new FileReader(sv.getValue());
        }
        catch (java.io.FileNotFoundException e)
        {
            throw new FileNotFoundException("The file " + sv.getValue() + " was not found.");
        }
        BufferedReader b = new BufferedReader(fr);
        fileTable.put(sv, b);

        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv) throws InvalidTypeException
    {
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new StringType()))
        {
            return typeEnv;
        }
        else
        {
            throw new InvalidTypeException("The expression given in the OpenRFile statement is not a string.");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new OpenRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "Open(" + expression.toString() + ")";
    }
}
