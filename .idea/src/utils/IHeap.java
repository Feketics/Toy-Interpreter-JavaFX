package utils;

import java.util.Map;
import java.util.Set;

public interface IHeap<K extends Integer, V>
{
    K put(V value);
    void update(K key, V value);
    void delete(K key);
    V LookUp(K key);
    boolean isDefined(K key);
    Set<K> keys();
    void setContent(Map<K, V> content);
    Map<K, V> getContent();
}
