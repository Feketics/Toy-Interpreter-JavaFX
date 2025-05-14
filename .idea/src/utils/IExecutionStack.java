package utils;

import java.util.List;

public interface IExecutionStack<T>
{
    T pop();
    void push(T v);
    boolean isEmpty();
    List<T> getReversed();
}
