package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;
import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class ArrayList<T> implements ListADT<T> {
    protected final int NOT_FOUND = -1;
    private final int DEFAULT_CAPACITY = 100;
    protected int rear;
    protected T[] list;

    public ArrayList() {
        this.rear = 0;
        this.list = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    protected void expandCapacity() {
        T[] largerList = (T[]) (new Object[this.list.length * 2]);

        System.arraycopy(this.list, 0, largerList, 0, this.list.length);

        this.list = largerList;
    }

    protected int find(T target) {
        for (int i = 0; i < this.rear; i++) {
            if (this.list[i].equals(target)) {
                return i;
            }
        }

        return this.NOT_FOUND;
    }

    public T removeFirst() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        T result = this.list[0];
        this.list[0] = null;

        for (int i = 0; i < this.rear; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.rear--;

        return result;
    }

    public T removeLast() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        T result = this.list[this.rear];
        this.list[this.rear] = null;
        this.rear--;

        return result;
    }

    public T remove(T target) {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        int index = find(target);
        if (index == this.NOT_FOUND) throw new ElementNotFoundException("List");

        T result = this.list[index];

        for (int i = index; i < this.rear - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.rear--;
        this.list[this.rear] = null;

        return result;
    }

    public T first() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        return this.list[0];
    }

    public T last() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        return this.list[this.rear - 1];
    }

    public boolean contains(T target) {
        return (this.find(target) != this.NOT_FOUND);
    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return this.rear;
    }

    public Iterator<T> iterator() {
        return new ArrayListIterator<T>(this.list, this.rear);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int scan = 0; scan < this.rear; scan++) {
            result.append(this.list[scan].toString()).append("\n");
        }

        return result.toString();
    }

    private static class ArrayListIterator<T> implements Iterator<T> {
        private final int count;
        private final T[] items;
        private int current;

        public ArrayListIterator(T[] collection, int size) {
            this.current = 0;
            this.items = collection;
            this.count = size;
        }

        public boolean hasNext() {
            return this.current < this.count;
        }

        public T next() {
            if (!hasNext()) throw new ElementNotFoundException("List");

            this.current++;

            return this.items[this.current - 1];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
