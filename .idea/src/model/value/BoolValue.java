package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value
{
    private boolean value;

    public BoolValue(boolean value)
    {
        this.value = value;
    }

    public boolean getValue()
    {
        return value;
    }

    @Override
    public Type getType()
    {
        return new BoolType();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof BoolValue b)
        {
            return b.value == this.value;
        }
        return false;
    }

    @Override
    public Value deepCopy()
    {
        return new BoolValue(value);
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
}
