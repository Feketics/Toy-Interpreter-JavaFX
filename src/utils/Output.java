package utils;

import java.util.ArrayList;
import java.util.List;

public class Output<T> implements IOutput<T>
{
    private List<T> output;

    public Output()
    {
        this.output = new ArrayList<T>();
    }

    @Override
    public void add(T elem)
    {
        this.output.add(elem);
    }

    @Override
    public List<T> getList()
    {
        return output;
    }

    @Override
    public String toString()
    {
        return this.output.toString();
    }
}
