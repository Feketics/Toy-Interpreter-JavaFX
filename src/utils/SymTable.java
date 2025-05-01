package utils;

import exceptions.VariableNotDeclaredException;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SymTable<K, V extends Value> implements ISymTable<K, V>
{
    private Map<K, V> map;

    public SymTable()
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
    public V lookUp(K key) throws VariableNotDeclaredException
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
    public ISymTable<K, V> deepCopy()
    {
        ISymTable<K, V> newTable = new SymTable<>();

        for(K key : this.map.keySet())
            newTable.put(key, (V)(this.lookUp(key).deepCopy()));

        return newTable;
    }

    @Override
    public String toString()
    {
        return this.map.toString();
    }
}
