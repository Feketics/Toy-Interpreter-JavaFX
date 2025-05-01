package utils;

import model.type.Type;

public interface ITypeEnv<K, V extends Type>
{
    void put(K key, V value);
    void update(K key, V value);
    V lookUp(K key);
    boolean isDefined(K key);
    ITypeEnv<K, V> deepCopy();
}
