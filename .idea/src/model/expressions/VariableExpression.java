package model.expressions;

import exceptions.VariableNotDeclaredException;
import model.type.Type;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

public class VariableExpression implements Expression
{
    private String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public Value eval(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap) throws VariableNotDeclaredException
    {
        if(tbl.isDefined(this.id))
            return tbl.lookUp(this.id);
        else throw new VariableNotDeclaredException("Variable '" + this.id + "' not defined");
    }

    @Override
    public Type getType(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap) throws VariableNotDeclaredException
    {
        return this.eval(tbl, heap).getType();
    }

    @Override
    public Type typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        return typeEnv.lookUp(this.id);
    }

    @Override
    public Expression deepCopy()
    {
        return new VariableExpression(id);
    }

    @Override
    public String toString()
    {
        return this.id;
    }
}
