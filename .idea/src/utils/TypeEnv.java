package utils;

import exceptions.VariableNotDeclaredException;
import model.type.Type;

import java.util.HashMap;
import java.util.Map;

public class TypeEnv<K, V extends Type> implements ITypeEnv<K, V>
{
    private Map<K, V> map;

    public TypeEnv()
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
    public ITypeEnv<K, V> deepCopy()
    {
        ITypeEnv<K, V> newTable = new TypeEnv<>();

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
