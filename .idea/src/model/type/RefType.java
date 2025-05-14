package model.type;

import model.value.RefValue;
import model.value.Value;

public class RefType implements Type
{
    private Type innerType;

    public RefType(Type innerType)
    {
        this.innerType = innerType;
    }

    public Type getInnerType()
    {
        return innerType;
    }

    @Override
    public Value defaultValue()
    {
        return new RefValue(0, innerType.deepCopy());
    }

    @Override
    public Type deepCopy()
    {
        return new RefType(innerType.deepCopy());
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof RefType)
            return innerType.equals(((RefType)o).innerType);
        return false;
    }

    @Override
    public String toString()
    {
        return "Ref " + innerType.toString();
    }
}
