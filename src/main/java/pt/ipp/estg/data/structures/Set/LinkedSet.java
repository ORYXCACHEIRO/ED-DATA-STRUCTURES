package pt.ipp.estg.data.structures.Set;

import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;
import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;

public class LinkedSet<T> implements SetADT<T> {
    private static final Random rand = new Random();
    private int count;
    private LinearNode<T> contents;

    public LinkedSet() {
        this.count = 0;
        this.contents = null;
    }

    public void add(T element) {
        if (!(contains(element))) {
            LinearNode<T> node = new LinearNode<T>(element);
            node.setNext(this.contents);
            this.contents = node;
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
        LinearNode<T> previous, current;
        T result;

        if (this.isEmpty()) throw new EmptyCollectionException("Set");

        int choice = rand.nextInt(count) + 1;

        if (choice == 1) {
            result = this.contents.element;
            this.contents = this.contents.next;
        } else {
            previous = contents;

            for (int skip = 2; skip < choice; skip++) {
                previous = previous.next;
            }

            current = previous.next;
            result = current.element;
            previous.next = current.next;
        }

        this.count--;

        return result;
    }

    public T remove(T target) {
        boolean found = false;
        LinearNode<T> previous, current;
        T result = null;

        if (this.isEmpty()) throw new EmptyCollectionException("Set");

        if (this.contents.getElement().equals(target)) {
            result = this.contents.element;
            this.contents = this.contents.next;
        } else {
            previous = this.contents;
            current = this.contents.next;

            for (int look = 0; look < this.count && !found; look++) {
                if (current.element.equals(target))
                    found = true;
                else {
                    previous = current;
                    current = current.next;
                }
            }

            if (!found) throw new ElementNotFoundException("Set");

            result = current.element;
            previous.next = current.next;
        }

        this.count--;

        return result;
    }

    public SetADT<T> union(SetADT<T> set) {
        LinkedSet<T> both = new LinkedSet<>();

        LinearNode<T> current = this.contents;

        while (current != null) {
            both.add(current.element);
            current = current.next;
        }

        Iterator<T> scan = set.iterator();
        while (scan.hasNext()) {
            both.add(scan.next());
        }

        return both;
    }

    public boolean contains(T target) {
        boolean found = false;

        LinearNode<T> current = this.contents;

        for (int look = 0; look < this.count && !found; look++) {
            if (current.element.equals(target)) {
                found = true;
            } else {
                current = current.next;
            }
        }

        return found;
    }

    public boolean equals(SetADT<T> set) {
        boolean result = false;
        LinkedSet<T> temp1 = new LinkedSet<>();
        LinkedSet<T> temp2 = new LinkedSet<>();
        T obj;

        if (this.size() == set.size()) {
            temp1.addAll(this);
            temp2.addAll(set);

            Iterator<T> scan = set.iterator();

            while (scan.hasNext()) {
                obj = scan.next();
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
        return new LinkedSetIterator<>(contents, count);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        LinearNode<T> current = this.contents;

        while (current != null) {
            result.append(current.element.toString()).append("\n");
            current = current.next;
        }

        return result.toString();
    }

    private static class LinearNode<T> {
        private T element;
        private LinearNode<T> next;

        public LinearNode() {
            this.element = null;
            this.next = null;
        }

        public LinearNode(T element) {
            this.element = element;
            this.next = null;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public LinearNode<T> getNext() {
            return next;
        }

        public void setNext(LinearNode<T> next) {
            this.next = next;
        }
    }

    private static class LinkedSetIterator<T> implements Iterator<T> {
        private final int count;
        private LinearNode<T> current;

        public LinkedSetIterator(LinearNode<T> collection, int size) {
            this.current = collection;
            this.count = size;
        }

        public boolean hasNext() {
            return this.current != null;
        }

        public T next() {
            if (!this.hasNext()) throw new ElementNotFoundException("Set");

            T result = current.element;
            current = current.next;

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
