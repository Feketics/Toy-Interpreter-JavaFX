package utils;

import model.value.Value;

import java.util.Map;
import java.util.Set;

public interface ISymTable<K, V extends Value >
{
    void put(K key, V value);
    void update(K key, V value);
    void delete(K key);
    V lookUp(K key);
    boolean isDefined(K key);
    Set<K> keys();
    Map<K, V> getContent();
    ISymTable<K, V> deepCopy();
}
