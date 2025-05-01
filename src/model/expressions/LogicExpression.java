package model.expressions;

import exceptions.InvalidLogicOperandException;
import exceptions.InvalidTypeException;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;
import utils.ITypeEnv;
import utils.IHeap;
import utils.ISymTable;

import java.util.Objects;

public class LogicExpression implements Expression
{
    String option;
    Expression first, second;

    public LogicExpression(String option, Expression first, Expression second)
    {
        this.first = first;
        this.second = second;
        this.option = option;
    }

    @Override
    public Value eval(ISymTable<String, Value> tbl, IHeap<Integer, Value> heap) throws InvalidTypeException, InvalidLogicOperandException
    {
        Value firstVal = first.eval(tbl, heap);
        Value secondVal = second.eval(tbl, heap);
        if(!firstVal.getType().equals(new BoolType()))
        {
            throw new InvalidTypeException("First value is not a BoolType");
        }
        if(!secondVal.getType().equals(new BoolType()))
        {
            throw new InvalidTypeException("Second value is not a BoolType");
        }

        BoolValue boolVal1 = (BoolValue) firstVal;
        BoolValue boolVal2 = (BoolValue) secondVal;

        boolean b1 = boolVal1.getValue();
        boolean b2 = boolVal2.getValue();

        if(Objects.equals(option, "and"))
        {
            return new BoolValue(b1 && b2);
        }

        if(Objects.equals(option, "or"))
        {
            return new BoolValue(b1 || b2);
        }

        throw new InvalidLogicOperandException("Reached end of logic expression evaluation");

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

        if(t1.equals(new BoolType()))
        {
            if(t2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else
            {
                throw new InvalidTypeException("First argument in logic expression is not a BoolType");
            }
        }
        else
        {
            throw new InvalidTypeException("Second argument in logic expression is not a BoolType");
        }
    }

    @Override
    public Expression deepCopy()
    {
        return new LogicExpression(option, first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString()
    {
        return this.first.toString() + " " + this.option + " " + this.second.toString();
    }
}
