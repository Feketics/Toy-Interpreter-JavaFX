package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type
{
    @Override
    public boolean equals(Object o)
    {
        return o instanceof BoolType;
    }

    @Override
    public String toString()
    {
        return "Bool";
    }

    @Override
    public Value defaultValue()
    {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy()
    {
        return new BoolType();
    }
}
