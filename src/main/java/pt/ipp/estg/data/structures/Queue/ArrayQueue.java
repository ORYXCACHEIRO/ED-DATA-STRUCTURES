package pt.ipp.estg.data.structures.Queue;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class ArrayQueue<T> implements QueueADT<T> {
    private final int DEFAULT_CAPACITY = 100;
    private int rear;
    private T[] queue;

    public ArrayQueue() {
        this.rear = 0;
        this.queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    private void expandCapacity() {
        T[] largerQueue = (T[]) new Object[this.queue.length * 2];

        System.arraycopy(this.queue, 0, largerQueue, 0, this.queue.length);

        this.queue = largerQueue;
    }

    public void enqueue(T element) {
        if (this.size() == this.queue.length) this.expandCapacity();

        this.queue[this.rear] = element;
        this.rear++;
    }

    public T dequeue() {
        if (this.isEmpty()) throw new EmptyCollectionException("queue");

        T result = this.queue[0];
        this.rear--;

        for (int i = 0; i < this.rear; i++) {
            this.queue[i] = this.queue[i + 1];
        }

        this.queue[this.rear] = null;

        return result;
    }

    public T first() {
        if (this.isEmpty()) throw new EmptyCollectionException("queue");

        return this.queue[0];
    }

    public boolean isEmpty() {
        return this.rear == 0;
    }

    public int size() {
        return this.rear;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int scan = 0; scan < this.rear; scan++) {
            result.append(this.queue[scan].toString()).append("\n");
        }

        return result.toString();
    }
}
