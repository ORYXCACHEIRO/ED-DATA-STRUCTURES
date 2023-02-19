package pt.ipp.estg.data.structures.Map;

import pt.ipp.estg.data.structures.List.UnorderedListADT;

public interface MapADT<K, V> {
    V get(K key);

    void put(K key, V value);

    boolean putAll(Entry<K, V>[] entries);

    void remove(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);

    UnorderedListADT<K> getKeys();

    UnorderedListADT<V> getValues();

    UnorderedListADT<Entry<K, V>> getEntries();

    void clear();

    boolean isEmpty();

    int size();

    String toString();

    interface Entry<K, V> {
        K getKey();

        void setKey(K key);

        V getValue();

        void setValue(V value);

        Entry<K, V> getNext();

        void setNext(Entry<K, V> next);
    }
}
