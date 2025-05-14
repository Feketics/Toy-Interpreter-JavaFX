package model.expressions;

import exceptions.InvalidAddressException;
import exceptions.InvalidTypeException;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

public class ReadHeapExpression implements Expression
{
    private Expression expression;

    public ReadHeapExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public Value eval(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap) throws InvalidTypeException, InvalidAddressException
    {
        Value v = expression.eval(tbl, heap);
        if(!(v instanceof RefValue rv))
            throw new InvalidTypeException("Expression " + expression.toString() + " cannot be evaluated to a RefValue");

        Integer address = rv.getAddress();

        if(!heap.isDefined(address))
            throw new InvalidAddressException("Address " + address + " is not defined");

        return heap.LookUp(address);
    }

    @Override
    public Type getType(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap)
    {
        return this.eval(tbl, heap).getType();
    }

    @Override
    public Type typeCheck(ITypeEnv<String, Type> typeEnv) throws InvalidTypeException
    {
        Type t = expression.typeCheck(typeEnv);
        if(t instanceof RefType rt)
        {
            return rt.getInnerType();
        }
        else
        {
            throw new InvalidTypeException("The readHeap argument is not a RefType");
        }
    }

    @Override
    public Expression deepCopy()
    {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "rH(" + expression.toString() + ")";
    }
}
