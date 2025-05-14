package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value
{
    private String value;

    public StringValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public Type getType()
    {
        return new StringType();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof StringValue s)
        {
            return this.value.equals(s.value);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }

    @Override
    public Value deepCopy()
    {
        return new StringValue(value);
    }

    @Override
    public String toString()
    {
        return value;
    }
}
