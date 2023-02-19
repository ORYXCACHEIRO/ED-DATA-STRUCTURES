package pt.ipp.estg.data.structures.Set;

import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;
import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;

public class ArraySet<T> implements SetADT<T> {
    private static final Random rand = new Random();
    private final int DEFAULT_CAPACITY = 100;
    private final int NOT_FOUND = -1;
    private int count;
    private transient T[] contents;

    public ArraySet() {
        this.count = 0;
        this.contents = (T[]) (new Object[this.DEFAULT_CAPACITY]);
    }

    private void expandCapacity() {
        T[] largerSet = (T[]) (new Object[this.contents.length * 2]);

        System.arraycopy(this.contents, 0, largerSet, 0, this.contents.length);

        this.contents = largerSet;
    }

    public void add(T element) {
        if (this.size() == this.contents.length) expandCapacity();

        if (!(contains(element))) {
            this.contents[this.count] = element;
            this.count++;
        }
    }

    public void addAll(SetADT<T> set) {
        Iterator<T> scan = set.iterator();

        while (scan.hasNext()) {
            add(scan.next());
        }
    }

    public T removeRandom() {
        if (this.isEmpty()) throw new EmptyCollectionException("Set");

        int choice = rand.nextInt(this.count);

        T result = this.contents[choice];

        this.contents[choice] = this.contents[this.count - 1];
        this.contents[this.count - 1] = null;
        this.count--;

        return result;
    }

    public T remove(T target) {
        int search = NOT_FOUND;

        if (this.isEmpty()) throw new EmptyCollectionException("Set");

        for (int index = 0; index < this.count && search == this.NOT_FOUND; index++) {
            if (this.contents[index].equals(target)) {
                search = index;
            }
        }
        if (search == NOT_FOUND) throw new ElementNotFoundException("Set");

        T result = this.contents[search];

        this.contents[search] = this.contents[this.count - 1];
        this.contents[this.count - 1] = null;
        this.count--;

        return result;
    }

    public SetADT<T> union(SetADT<T> set) {
        ArraySet<T> both = new ArraySet<>();

        for (int index = 0; index < this.count; index++) {
            both.add(this.contents[index]);
        }

        Iterator<T> scan = set.iterator();
        while (scan.hasNext()) {
            both.add(scan.next());
        }

        return both;
    }

    public boolean contains(T target) {
        int search = this.NOT_FOUND;

        for (int index = 0; index < this.count && search == this.NOT_FOUND; index++) {
            if (this.contents[index].equals(target)) {
                search = index;
            }
        }

        return (search != this.NOT_FOUND);
    }

    public boolean equals(SetADT<T> set) {
        boolean result = false;
        ArraySet<T> temp1 = new ArraySet<>();
        ArraySet<T> temp2 = new ArraySet<>();

        if (size() == set.size()) {
            temp1.addAll(this);
            temp2.addAll(set);

            Iterator<T> scan = set.iterator();

            while (scan.hasNext()) {
                T obj = scan.next();
                if (temp1.contains(obj)) {
                    temp1.remove(obj);
                    temp2.remove(obj);
                }

            }
            result = (temp1.isEmpty() && temp2.isEmpty());
        }

        return result;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public Iterator<T> iterator() {
        return new ArraySetIterator<T>(contents, count);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.count; i++)
            result.append(this.contents[i].toString()).append("\n");

        return result.toString();
    }

    private static class ArraySetIterator<T> implements Iterator<T> {
        private final int count;
        private final T[] items;
        private int current;

        public ArraySetIterator(T[] collection, int size) {
            this.current = 0;
            this.items = collection;
            this.count = size;
        }

        public boolean hasNext() {
            return this.current < this.count;
        }

        public T next() {
            if (!hasNext()) throw new ElementNotFoundException("Set");

            this.current++;

            return this.items[this.current - 1];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
