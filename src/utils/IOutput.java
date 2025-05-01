package utils;

import java.util.List;

public interface IOutput<T>
{
    void add(T elem);
    List<T> getList();
}
