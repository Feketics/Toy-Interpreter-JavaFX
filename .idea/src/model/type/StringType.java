package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type
{
    @Override
    public boolean equals(Object o)
    {
        return o instanceof StringType;
    }

    @Override
    public String toString()
    {
        return "String";
    }

    @Override
    public Value defaultValue()
    {
        return new StringValue("");
    }

    @Override
    public Type deepCopy()
    {
        return new StringType();
    }
}
