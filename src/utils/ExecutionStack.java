package utils;

import exceptions.ExecStackEmptyException;

import java.util.*;

public class ExecutionStack<T> implements IExecutionStack<T>
{
    private Stack<T> stack;

    public ExecutionStack()
    {
        stack = new Stack<T>();
    }

    @Override
    public T pop() throws ExecStackEmptyException
    {
        if(stack.isEmpty())
            throw new ExecStackEmptyException("Execution stack is empty");
        return this.stack.pop();
    }

    @Override
    public void push(T v)
    {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty()
    {
        return this.stack.isEmpty();
    }

    @Override
    public List<T> getReversed()
    {
        return this.stack.reversed();
    }

    @Override
    public String toString()
    {
        return this.stack.toString();
    }
}
