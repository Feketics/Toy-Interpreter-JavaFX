package model.expressions;

import exceptions.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

import java.util.Objects;

public class RelationalExpression implements Expression
{
    private Expression first, second;
    private String option;

    public RelationalExpression(String option, Expression first, Expression second)
    {
        this.first = first;
        this.second = second;
        this.option = option;
    }

    @Override
    public Value eval(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap) throws InvalidTypeException
    {
        Value v1, v2;
        v1 = first.eval(tbl, heap);
        v2 = second.eval(tbl, heap);
        if(!v1.getType().equals(new IntType()))
        {
            throw new InvalidTypeException("First argument is not an integer.");
        }
        if(!v2.getType().equals(new IntType()))
        {
            throw new InvalidTypeException("Second argument is not an integer.");
        }
        IntValue iv1, iv2;
        iv1 = (IntValue)v1;
        iv2 = (IntValue)v2;

        int n1, n2;
        n1 = iv1.getValue();
        n2 = iv2.getValue();

        if(Objects.equals(option, "<"))
        {
            if(n1 < n2)
                return new BoolValue(true);
            return new BoolValue(false);
        }

        if(Objects.equals(option, "<="))
        {
            if(n1 <= n2)
                return new BoolValue(true);
            return new BoolValue(false);
        }

        if(Objects.equals(option, "=="))
        {
            if(n1 == n2)
                return new BoolValue(true);
            return new BoolValue(false);
        }

        if(Objects.equals(option, "!="))
        {
            if(n1 != n2)
                return new BoolValue(true);
            return new BoolValue(false);
        }

        if(Objects.equals(option, ">"))
        {
            if(n1 > n2)
                return new BoolValue(true);
            return new BoolValue(false);
        }

        if(Objects.equals(option, ">="))
        {
            if(n1 >= n2)
                return new BoolValue(true);
            return new BoolValue(false);
        }

        throw new InvalidRelationalExpressionException("Invalid relational expression.");
    }

    @Override
    public Type getType(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap)
    {
        return this.eval(tbl, heap).getType();
    }

    @Override
    public Type typeCheck(ITypeEnv<String, Type> typeEnv)
    {
        Type t1, t2;
        t1 = this.first.typeCheck(typeEnv);
        t2 = this.second.typeCheck(typeEnv);
        if(t1.equals(new IntType()))
        {
            if(t2.equals(new IntType()))
            {
                return new BoolType();
            }
            else
            {
                throw new InvalidTypeException("Second argument in relational expression is not a number.");
            }
        }
        else
        {
            throw new InvalidTypeException("First argument in relational expression is not a number.");
        }
    }

    @Override
    public Expression deepCopy()
    {
        return new RelationalExpression(option, first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString()
    {
        return this.first.toString() + " " + this.option + " " + this.second.toString();
    }
}
