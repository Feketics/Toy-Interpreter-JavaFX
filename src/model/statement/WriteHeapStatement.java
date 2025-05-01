package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableNotDeclaredException;
import model.ProgramState;
import model.expressions.Expression;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

public class WriteHeapStatement implements Statement
{
    private String variable;
    private Expression expression;

    public WriteHeapStatement(String variable, Expression expression)
    {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws VariableNotDeclaredException, InvalidTypeException
    {
        ISymTable<String, Value> tbl = state.getSymtbl();
        IHeap<Integer, Value> heap = state.getHeap();

        if(!tbl.isDefined(variable))
            throw new VariableNotDeclaredException("Variable " + variable + " is not defined.");

        Value v = tbl.lookUp(variable);
        if(!(v instanceof RefValue rv))
            throw new InvalidTypeException("Variable " + variable + " is not a reference.");

        if(!heap.isDefined(rv.getAddress()))
            throw new VariableNotDeclaredException("Address " + rv.getAddress() + " is not defined.");

        Value ev = expression.eval(tbl, heap);
        if(!ev.getType().equals(rv.getLocationType()))
            throw new InvalidTypeException("Type of expression " + expression.toString() + " does not match type of reference.");

        heap.update(rv.getAddress(), ev);

        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv) throws InvalidTypeException
    {
        Type varType = typeEnv.lookUp(variable);
        Type expType = expression.typeCheck(typeEnv);
        if(varType instanceof RefType rt)
        {
            if(expType.equals(rt.getInnerType()))
            {
                return typeEnv;
            }
            else
            {
                throw new InvalidTypeException("Write heap: Variable and Expression type do not match.");
            }
        }
        else
        {
            throw new InvalidTypeException("Variable " + variable + " is not a reference.");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new WriteHeapStatement(variable, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "wH(" + variable + ", " + expression.toString() + ")";
    }
}
