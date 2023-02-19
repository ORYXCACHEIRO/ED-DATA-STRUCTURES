package pt.ipp.estg.data.structures.Queue;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements QueueADT<T> {
    private int count;
    private LinearNode<T> front, rear;

    public LinkedQueue() {
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    public void enqueue(T element) {
        LinearNode<T> node = new LinearNode<T>(element);

        if (isEmpty()) {
            this.front = node;
        } else {
            this.rear.next = node;
        }

        this.rear = node;
        this.count++;
    }

    public T dequeue() {
        if (this.isEmpty()) throw new EmptyCollectionException("Queue");

        T result = this.front.element;
        this.front = this.front.next;
        this.count--;

        if (this.isEmpty()) {
            this.rear = null;
        }

        return result;
    }

    public T first() {
        if (this.isEmpty()) throw new EmptyCollectionException("Queue");

        return this.front.element;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for(LinearNode<T> current = this.front; current != null; current = current.next) {
            result.append(current.element.toString()).append("\n");
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
}
