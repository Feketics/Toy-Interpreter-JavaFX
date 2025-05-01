package model.expressions;

import exceptions.DivisionByZeroException;
import exceptions.InvalidArithmeticOperandExpression;
import exceptions.InvalidTypeException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

import java.util.Objects;

public class ArithmeticExpression implements Expression
{
    Expression first, second;
    String option;

    public ArithmeticExpression(String option, Expression first, Expression second)
    {
        this.first = first;
        this.second = second;
        this.option = option;
    }

    @Override
    public Value eval(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap) throws InvalidArithmeticOperandExpression, DivisionByZeroException, InvalidTypeException
    {
        Value v1, v2;
        v1 = first.eval(tbl, heap);
        v2 = second.eval(tbl, heap);
        if(!v1.getType().equals(new IntType()))
        {
            throw new InvalidTypeException("First argument is not a number.");
        }
        if(!v2.getType().equals(new IntType()))
        {
            throw new InvalidTypeException("Second argument is not a number.");
        }
        IntValue iv1, iv2;
        iv1 = (IntValue)v1;
        iv2 = (IntValue)v2;

        int n1, n2;
        n1 = iv1.getValue();
        n2 = iv2.getValue();

        if(Objects.equals(option, "+"))
        {
            return new IntValue(n1 + n2);
        }

        if(Objects.equals(option, "-"))
        {
            return new IntValue(n1 - n2);
        }

        if(Objects.equals(option, "*"))
        {
            return new IntValue(n1 * n2);
        }

        if(Objects.equals(option, "/"))
        {
            if(n2 == 0)
            {
                throw new DivisionByZeroException("Division by zero.");
            }
            else
            {
                return new IntValue(n1 / n2);
            }
        }
        throw new InvalidArithmeticOperandExpression("Reached end of arithmetic expression evaluation.");
    }

    @Override
    public Type getType(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap)
    {
        return this.eval(tbl, heap).getType();
    }

    @Override
    public Type typeCheck(ITypeEnv<String, Type> typeEnv) throws InvalidTypeException
    {
        Type t1, t2;
        t1 = this.first.typeCheck(typeEnv);
        t2 = this.second.typeCheck(typeEnv);
        if(t1.equals(new IntType()))
        {
            if(t2.equals(new IntType()))
            {
                return new IntType();
            }
            else
            {
                throw new InvalidTypeException("Second argument in arithmetic expression is not a number.");
            }
        }
        else
        {
            throw new InvalidTypeException("First argument in arithmetic expression is not a number.");
        }
    }

    @Override
    public Expression deepCopy()
    {
        return new ArithmeticExpression(option, first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString()
    {
        return this.first.toString() + " " + this.option + " " + this.second.toString();
    }

}
