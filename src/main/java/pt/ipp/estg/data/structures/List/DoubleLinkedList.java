package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;
import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class DoubleLinkedList<T> implements ListADT<T> {
    protected int count;
    protected DoubleLinearNode<T> front;
    protected DoubleLinearNode<T> rear;

    public DoubleLinkedList() {
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    protected DoubleLinearNode<T> find(T target) {
        boolean found = false;
        DoubleLinearNode<T> traverse = this.front;
        DoubleLinearNode<T> result = null;

        if (!this.isEmpty()) {
            while (!found && traverse != null) {
                if (target.equals(traverse.element)) {
                    found = true;
                    result = traverse;
                } else {
                    traverse = traverse.next;
                }
            }
        }

        return result;
    }

    public T removeFirst() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        T result = this.front.element;
        this.front = this.front.next;

        if (this.front == null) {
            this.rear = null;
        } else {
            this.front.previous = null;
        }

        this.count--;

        return result;
    }

    public T removeLast() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        T result = this.rear.element;
        this.rear = this.rear.previous;

        if (this.rear == null) {
            this.front = null;
        } else {
            this.rear.next = null;
        }

        this.count--;

        return result;
    }

    public T remove(T element) {
        DoubleLinearNode<T> nodePtr = this.find(element);

        if (nodePtr == null) throw new ElementNotFoundException("List");

        T result = nodePtr.element;

        if (nodePtr == this.front) {
            result = this.removeFirst();
        } else if (nodePtr == this.rear) {
            result = this.removeLast();
        } else {
            nodePtr.next.setPrevious(nodePtr.previous);
            nodePtr.previous.setNext(nodePtr.next);
            this.count--;
        }

        return result;
    }

    public T first() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        return this.front.element;
    }

    public T last() {
        if (this.isEmpty()) throw new EmptyCollectionException("List");

        return this.rear.element;
    }

    public boolean contains(T target) {
        return this.find(target) != null;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public Iterator<T> iterator() {
        return new ListIterator<T>(this.front, this.count);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        DoubleLinearNode<T> traverse = this.front;

        while (traverse != null) {
            result.append((traverse.element).toString()).append("\n");
            traverse = traverse.next;
        }

        return result.toString();
    }

    protected static class DoubleLinearNode<T> {
        private T element;
        private DoubleLinearNode<T> previous;
        private DoubleLinearNode<T> next;

        public DoubleLinearNode() {
            this.element = null;
            this.previous = null;
            this.next = null;
        }

        public DoubleLinearNode(T element) {
            this.element = element;
            this.previous = null;
            this.next = null;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public DoubleLinearNode<T> getPrevious() {
            return previous;
        }

        public void setPrevious(DoubleLinearNode<T> previous) {
            this.previous = previous;
        }

        public DoubleLinearNode<T> getNext() {
            return next;
        }

        public void setNext(DoubleLinearNode<T> next) {
            this.next = next;
        }
    }

    private static class ListIterator<T> implements Iterator<T> {
        private final int count;
        private DoubleLinearNode<T> current;

        public ListIterator(DoubleLinearNode<T> collection, int size) {
            this.current = collection;
            this.count = size;
        }

        public boolean hasNext() {
            return this.current != null;
        }

        public T next() {
            if (!this.hasNext()) throw new ElementNotFoundException("List");

            T result = this.current.element;
            this.current = this.current.next;

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
