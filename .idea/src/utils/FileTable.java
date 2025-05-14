package utils;

import exceptions.VariableNotDeclaredException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTable<K, V> implements IFileTable<K, V>
{
    private Map<K, V> map;

    public FileTable()
    {
        this.map = new HashMap<K, V>();
    }

    @Override
    public void put(K key, V value)
    {
        this.map.put(key, value);
    }

    @Override
    public void update(K key, V value)
    {
        this.put(key, value);
    }

    @Override
    public void delete(K key)
    {
        this.map.remove(key);
    }

    @Override
    public V LookUp(K key) throws VariableNotDeclaredException
    {
        if(!this.map.containsKey(key))
            throw new VariableNotDeclaredException("Key " + key + " not found");
        return this.map.get(key);
    }

    @Override
    public boolean isDefined(K key)
    {
        return this.map.get(key) != null;
    }

    @Override
    public Set<K> keys()
    {
        return this.map.keySet();
    }

    @Override
    public Map<K, V> getContent()
    {
        return this.map;
    }

    @Override
    public String toString()
    {
        return this.map.toString();
    }
}
