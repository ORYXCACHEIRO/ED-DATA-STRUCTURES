package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;
import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class LinkedList<T> implements ListADT<T> {
    protected int count;
    protected LinearNode<T> head;
    protected LinearNode<T> tail;

    public LinkedList() {
        this.count = 0;
        this.head = null;
        this.tail = null;
    }

    public T removeFirst() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        LinearNode<T> result = this.head;
        this.head = this.head.next;

        if (this.head == null) {
            this.tail = null;
        }

        this.count--;

        return result.element;
    }

    public T removeLast() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        LinearNode<T> previous = null;
        LinearNode<T> current = this.head;

        while (current.next != null) {
            previous = current;
            current = current.next;
        }

        LinearNode<T> result = this.tail;
        this.tail = previous;

        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.next = null;
        }

        this.count--;

        return (T) result;
    }

    public T remove(T element) {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        boolean found = false;

        LinearNode<T> previous = null;
        LinearNode<T> current = this.head;

        while (current != null && !found) {
            if (element.equals(current.element)) {
                found = true;
            } else {
                previous = current;
                current = current.next;
            }
        }

        if (!found) throw new EmptyCollectionException("List");

        if (this.size() == 1) {
            this.head = null;
            this.tail = null;
        } else if (current.equals(head)) {
            this.head = current.next;
        } else if (current.equals(tail)) {
            this.tail = previous;
            this.tail.next = null;
        } else {
            previous.next = current.next;
        }

        this.count--;

        return current.element;
    }

    public T first() {
        return head.element;
    }

    public T last() {
        return tail.element;
    }

    public boolean contains(T target) {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        boolean found = false;

        LinearNode<T> current = this.head;

        while (current != null && !found) {
            if (target.equals(current.element)) {
                found = true;
            } else {
                current = current.next;
            }
        }

        return found;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator<T>(head, count);
    }

    public String toString() {
        LinearNode<T> current = this.head;
        StringBuilder result = new StringBuilder();

        while (current != null) {
            result.append((current.element).toString()).append("\n");
            current = current.next;
        }

        return result.toString();

    }

    protected static class LinearNode<T> {
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

    private static class LinkedListIterator<T> implements Iterator<T> {
        private final int count;
        private LinearNode<T> current;

        public LinkedListIterator(LinearNode<T> collection, int size) {
            this.current = collection;
            this.count = size;
        }

        public boolean hasNext() {
            return this.current != null;
        }

        public T next() {
            if (!this.hasNext()) throw new ElementNotFoundException("List");

            T result = current.element;
            current = current.next;

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
