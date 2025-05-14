package utils;

import java.util.Map;
import java.util.Set;

public interface IFileTable<K, V>
{
    void put(K key, V value);
    void update(K key, V value);
    void delete(K key);
    V LookUp(K key);
    boolean isDefined(K key);
    Set<K> keys();
    Map<K, V> getContent();
}
