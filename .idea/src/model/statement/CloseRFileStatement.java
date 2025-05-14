package model.statement;

import exceptions.*;
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
import java.io.IOException;

public class CloseRFileStatement implements Statement
{
    private Expression expression;

    public CloseRFileStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InvalidTypeException, FileNotOpenException
    {
        ISymTable<String, Value> symTbl = state.getSymtbl();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value v = this.expression.eval(symTbl, state.getHeap());
        if(!(v.getType() instanceof StringType))
            throw new InvalidTypeException("The expression given in the CloseRFile statement is not a string.");

        StringValue sv = new StringValue("src/files/" + ((StringValue)v).getValue());
        if(!fileTable.isDefined(sv))
            throw new FileNotOpenException("The file " + sv.getValue() + " is not open.");


        BufferedReader b = fileTable.LookUp(sv);
        try
        {
            b.close();
        }
        catch(IOException e)
        {
            throw new FileNotOpenException("The file " + sv.getValue() + " is not open.");
        }

        fileTable.delete(sv);

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
            throw new InvalidTypeException("The expression given in the CloseRFile statement is not a string.");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new CloseRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "Close(" + expression.toString() + ")";
    }
}
