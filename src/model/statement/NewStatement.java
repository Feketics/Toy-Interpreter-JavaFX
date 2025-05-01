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

public class NewStatement implements Statement
{
    private String variable;
    private Expression expression;

    public NewStatement(String variable, Expression expression)
    {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws VariableNotDeclaredException, InvalidTypeException
    {
        ISymTable<String, Value> symTbl = state.getSymtbl();
        IHeap<Integer, Value> heap = state.getHeap();

        if(!symTbl.isDefined(variable))
            throw new VariableNotDeclaredException("Variable " + variable + " is not defined");

        Value v = symTbl.lookUp(variable);
        if(!(v.getType() instanceof RefType))
            throw new InvalidTypeException("Variable " + variable + " is not a ref type");
        RefValue rv = (RefValue)v;

        Value ev = expression.eval(symTbl, heap);

        if(!ev.getType().equals(rv.getLocationType()))
            throw new InvalidTypeException("Type of expression " + expression.toString() + " and allocated space at " + variable + " does not match");

        Integer key = heap.put(ev);

        symTbl.update(variable, new RefValue(key, rv.getLocationType()));

        return null;
    }

    @Override
    public ITypeEnv<String, Type> typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        Type varType, expType;
        varType = typeEnv.lookUp(variable);
        expType = expression.typeCheck(typeEnv);
        if(varType.equals(new RefType(expType)))
        {
            return typeEnv;
        }
        else
        {
            throw new InvalidTypeException("New Statement: left hand side and right hand side have different types");
        }
    }

    @Override
    public Statement deepCopy()
    {
        return new NewStatement(variable, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "new(" + variable + ", " + expression.toString() + ")";
    }
}
