package com.mbg.module.common.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicMultiValueMap<K, V> implements MultiValueMap<K, V> {
    protected Map<K, List<V>> mSource;

    public BasicMultiValueMap(Map<K, List<V>> source) {
        mSource = source;
    }

    @Override
    public void add(K key, V value) {
        if (!containsKey(key))
            mSource.put(key, new ArrayList<V>(1));
        getValues(key).add(value);
    }

    @Override
    public void add(K key, List<V> values) {
        if (!containsKey(key))
            mSource.put(key, values);
        else
            mSource.get(key).addAll(values);
    }

    @Override
    public void set(K key, V value) {
        remove(key);
        add(key, value);
    }

    @Override
    public void set(K key, List<V> values) {
        mSource.put(key, values);
    }

    @Override
    public List<V> remove(K key) {
        return mSource.remove(key);
    }

    @Override
    public void clear() {
        mSource.clear();
    }

    @Override
    public Set<K> keySet() {
        return mSource.keySet();
    }

    @Override
    public List<V> values() {
        List<V> allValues = new ArrayList<>();
        Set<K> keySet = keySet();
        for (K key : keySet) {
            allValues.addAll(getValues(key));
        }
        return allValues;
    }

    @Override
    public List<V> getValues(K key) {
        return mSource.get(key);
    }

    @Override
    public V getFirstValue(K key) {
        return getValue(key, 0);
    }

    @Override
    public Set<Map.Entry<K, List<V>>> entrySet() {
        return mSource.entrySet();
    }

    @Override
    public V getValue(K key, int index) {
        List<V> values = getValues(key);
        if (values != null && values.size() > index)
            return values.get(index);
        return null;
    }

    @Override
    public int size() {
        return mSource.size();
    }

    @Override
    public boolean isEmpty() {
        return mSource.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return mSource.containsKey(key);
    }
}
