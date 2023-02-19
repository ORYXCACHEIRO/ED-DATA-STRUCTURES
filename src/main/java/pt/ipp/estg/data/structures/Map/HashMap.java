package pt.ipp.estg.data.structures.Map;

import pt.ipp.estg.data.structures.List.UnorderedLinkedList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;

public class HashMap<K, V> implements MapADT<K, V> {
    private final int DEFAULT_CAPACITY = 16;
    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry<K, V>[] buckets;
    private int size;

    public HashMap() {
        this.buckets = new Entry[DEFAULT_CAPACITY];
        this.size = 0;
        initEntry(buckets);
    }

    private void initEntry(Entry<K, V>[] entry) {
        for (int i = 0; i < entry.length; i++) {
            entry[i] = null;
        }
    }

    private void expandCapacity() {
        Entry<K, V>[] oldBuckets = this.buckets;
        this.buckets = new Entry[this.buckets.length * 2];
        this.size = 0;

        this.initEntry(buckets);
        this.putAll(oldBuckets);
    }

    public V get(K key) {
        Entry<K, V> entry = this.buckets[Math.abs(key.hashCode() % this.buckets.length)];

        if (entry == null) return null;

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }

        return null;
    }

    public void put(K key, V value) {
        if ((1.0 * this.size) / this.buckets.length >= this.DEFAULT_LOAD_FACTOR) this.expandCapacity();

        Entry<K, V> newEntry = new Entry<>(key, value);

        if (!this.containsKey(key)) {
            this.buckets[Math.abs(key.hashCode() % this.buckets.length)] = newEntry;
        } else {
            Entry<K, V> entry = this.buckets[Math.abs(key.hashCode() % this.buckets.length)];

            if (entry == null) {
                this.buckets[Math.abs(key.hashCode() % this.buckets.length)] = newEntry;
                return;
            }

            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }

            while (entry.getNext() != null) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
                entry = entry.getNext();
            }
            entry.setNext(newEntry);
        }

        this.size++;
    }

    public boolean putAll(MapADT.Entry<K, V>[] entries) {
        for (MapADT.Entry<K, V> entry : entries) {
            while (entry != null) {
                this.put(entry.getKey(), entry.getValue());
                entry = entry.getNext();
            }
        }

        return true;
    }

    public void remove(K key) {
        Entry<K, V> entry = this.buckets[Math.abs(key.hashCode() % this.buckets.length)];

        if (entry == null) return;

        if (entry.getKey().equals(key)) {
            this.buckets[Math.abs(key.hashCode() % this.buckets.length)] = entry.getNext();
            this.size--;
            return;
        }

        while (entry.getNext() != null) {
            if (entry.getNext().getKey().equals(key)) {
                entry.setNext(entry.getNext().getNext());
                this.size--;
                return;
            }
            entry = entry.getNext();
        }
    }

    public boolean containsKey(K key) {
        Entry<K, V> entry = this.buckets[Math.abs(key.hashCode() % this.buckets.length)];

        if (entry == null) return false;

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return true;
            }
            entry = entry.getNext();
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (Entry<K, V> entry : this.buckets) {
            if (entry != null) {
                while (entry != null) {
                    if (entry.getValue().equals(value)) {
                        return true;
                    }
                    entry = entry.getNext();
                }
            }
        }

        return false;
    }

    public UnorderedListADT<K> getKeys() {
        UnorderedListADT<K> list = new UnorderedLinkedList<>();

        for (Entry<K, V> entry : this.buckets) {
            if (entry != null) {
                while (entry != null) {
                    list.addToRear(entry.getKey());
                    entry = entry.getNext();
                }
            }
        }

        return list;
    }

    public UnorderedListADT<V> getValues() {
        UnorderedListADT<V> list = new UnorderedLinkedList<>();

        for (Entry<K, V> entry : this.buckets) {
            if (entry != null) {
                while (entry != null) {
                    list.addToRear(entry.getValue());
                    entry = entry.getNext();
                }
            }
        }

        return list;
    }

    public UnorderedListADT<MapADT.Entry<K, V>> getEntries() {
        UnorderedListADT<MapADT.Entry<K, V>> list = new UnorderedLinkedList<>();

        for (Entry<K, V> entry : this.buckets) {
            if (entry != null) {
                while (entry != null) {
                    list.addToRear(entry);
                    entry = entry.getNext();
                }
            }
        }

        return list;
    }

    public void clear() {
        this.buckets = new Entry[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry<K, V> kvEntry : this.buckets) {
            if (kvEntry != null) {
                Entry<K, V> e = kvEntry;
                while (e != null) {
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(e.getValue());
                    sb.append(", ");
                    e = e.next;
                }
            }
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return sb.toString();
    }

    private static class Entry<K, V> implements MapADT.Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(MapADT.Entry<K, V> next) {
            this.next = (Entry<K, V>) next;
        }
    }
}
