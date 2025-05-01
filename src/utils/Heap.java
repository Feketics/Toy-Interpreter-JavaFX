package utils;

import exceptions.InvalidAddressException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Heap<K extends Integer, V> implements IHeap<K, V>
{
    private Map<K, V> map;
    private K nextFreeAddress;

    public Heap()
    {
        map = new ConcurrentHashMap<K, V>();
        nextFreeAddress = (K) (Integer) 1;
    }

    @Override
    public K put(V value)
    {
        this.map.put(nextFreeAddress, value);

        K retVal = nextFreeAddress;

        Integer index = (Integer) nextFreeAddress;
        while(this.map.containsKey((K) index))
            index++;
        nextFreeAddress = (K) index;

        return retVal;
    }

    @Override
    public void update(K key, V value)
    {
        this.map.put(key, value);
    }

    @Override
    public void delete(K key)
    {
        if(this.map.containsKey(key))
        {
            Integer index = (Integer) nextFreeAddress;
            Integer intKey = (Integer) key;

            if(intKey < index)
                nextFreeAddress = key;

            this.map.remove(key);
        }
    }

    @Override
    public V LookUp(K key) throws InvalidAddressException
    {
        if(!this.map.containsKey(key))
            throw new InvalidAddressException("Address " + ((Integer)key).toString() + " not found");
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
    public void setContent(Map<K, V> content)
    {
        this.map = content;
        int i = 1;
        while(this.map.containsKey((K) (Integer) i))
        {
            i++;
        }
        nextFreeAddress = (K) (Integer) i;
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
