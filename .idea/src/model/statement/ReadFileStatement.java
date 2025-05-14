package model.statement;

import exceptions.*;
import model.ProgramState;
import model.expressions.Expression;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.IFileTable;
import utils.ISymTable;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement
{
    private Expression expression;
    private String variableName;

    public ReadFileStatement(Expression expression, String variable)
    {
        this.expression = expression;
        this.variableName = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InvalidTypeException, VariableNotDeclaredException
    {
        ISymTable<String, Value> symTbl = state.getSymtbl();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value v = symTbl.lookUp(variableName);

        if(!(v instanceof IntValue))
            throw new InvalidTypeException("Variable should be an integer");

        Value expValue = this.expression.eval(symTbl, state.getHeap());
        if(!(expValue instanceof StringValue fileName))
            throw new InvalidTypeException("File name should be a string");

        if(!fileTable.isDefined(new StringValue("src/files/" + fileName.getValue())))
            throw new FileNotOpenException("File " + fileName.getValue() + " is not open.");

        BufferedReader br = fileTable.LookUp(new StringValue("src/files/" + fileName.getValue()));

        String line;
        try
        {
            line = br.readLine();
        }
        catch(IOException e)
        {
            throw new OtherFileException("Exception while reading file " + fileName.getValue());
        }

        if(line == null)
            symTbl.update(variableName, new IntValue(0));
        else
        {
            try
            {
                int i = Integer.parseInt(line);
                if(i <= 0)
                    throw new InvalidValueException("Variable should be a positive integer");
                symTbl.update(variableName, new IntValue(i));
            }
            catch(NumberFormatException e)
            {
                throw new InvalidTypeException("Input file may only contain integers.");
            }
        }

        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv) throws InvalidTypeException
    {
        Type varType = typeEnv.lookUp(variableName);
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new StringType()))
        {
            if(varType.equals(new IntType()))
            {
                return typeEnv;
            }
            else
            {
                throw new InvalidTypeException("File read: Trying to read into a variable that is not an int.");
            }
        }
        else
        {
            throw new InvalidTypeException("Read file: filename should be a string");
        }

    }

    @Override
    public Statement deepCopy()
    {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }

    @Override
    public String toString()
    {
        return "Read(" + expression.toString() + ", " + variableName + ")";
    }
}
