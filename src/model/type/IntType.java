package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type
{
    @Override
    public boolean equals(Object o)
    {
        return o instanceof IntType;
    }

    @Override
    public String toString()
    {
        return "Int";
    }

    @Override
    public Value defaultValue()
    {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy()
    {
        return new IntType();
    }
}
