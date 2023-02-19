package pt.ipp.estg.data.structures.Queue;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class CircularArrayQueue<T> implements QueueADT<T> {
    private final int DEFAULT_CAPACITY = 100;
    private int front, rear, count;
    private T[] queue;

    public CircularArrayQueue() {
        this.front = 0;
        this.rear = 0;
        this.count = 0;
        this.queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    private void expandCapacity() {
        T[] largerQueue = (T[]) new Object[this.queue.length * 2];

        for (int i = 0; i < this.count; i++) {
            largerQueue[i] = this.queue[this.front];
            this.front = (this.front + 1) % this.queue.length;
        }

        this.front = 0;
        this.rear = this.count;
        this.queue = largerQueue;
    }

    public void enqueue(T element) {
        if (this.size() == this.queue.length) this.expandCapacity();

        this.queue[this.rear] = element;
        this.rear = (this.rear + 1) % this.queue.length;
        this.count++;
    }

    public T dequeue() {
        if (this.isEmpty()) throw new EmptyCollectionException("Queue");

        T result = this.queue[this.front];
        this.queue[this.front] = null;
        this.front = (this.front + 1) % this.queue.length;
        this.count--;

        return result;
    }

    public T first() {
        if (this.isEmpty()) throw new EmptyCollectionException("Queue");

        return this.queue[this.front];
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        int scan = 0;

        while (scan < this.count) {
            if (this.queue[scan] != null) {
                result.append(this.queue[scan].toString()).append("\n");
            }
            scan++;
        }

        return result.toString();
    }
}
