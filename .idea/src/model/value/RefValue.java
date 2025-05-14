package model.value;

import model.type.RefType;
import model.type.Type;

import java.util.Objects;

public class RefValue implements Value
{
    private Integer address;
    private Type locationType;

    public RefValue(Integer address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public Integer getAddress()
    {
        return address;
    }

    public Type getLocationType()
    {
        return locationType;
    }

    @Override
    public Type getType()
    {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy()
    {
        return new RefValue(address, locationType.deepCopy());
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof RefValue obj)
            return Objects.equals(obj.address, address) && obj.locationType.equals(locationType);
        return false;
    }

    @Override
    public String toString()
    {
        return "(" + address.toString() + ", " + locationType.toString() + ")";
    }
}
