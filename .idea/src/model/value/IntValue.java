package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value
{
    private int value;

    public IntValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public Type getType()
    {
        return new IntType();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof IntValue i)
        {
            return i.value == this.value;
        }
        return false;
    }

    @Override
    public Value deepCopy()
    {
        return new IntValue(value);
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
}
