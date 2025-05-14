package model.expressions;

import model.type.Type;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

public class ValueExpression implements Expression
{
    private Value e;

    public ValueExpression(Value e)
    {
        this.e = e;
    }

    @Override
    public Value eval(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap)
    {
        return e;
    }

    @Override
    public Type getType(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap)
    {
        return this.eval(tbl, heap).getType();
    }

    @Override
    public Type typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        return e.getType();
    }

    @Override
    public Expression deepCopy()
    {
        return new ValueExpression(this.e);
    }

    @Override
    public String toString()
    {
        return e.toString();
    }
}
