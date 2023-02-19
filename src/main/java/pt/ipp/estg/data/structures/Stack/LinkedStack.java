package pt.ipp.estg.data.structures.Stack;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class LinkedStack<T> implements StackADT<T> {
    private int count;
    private LinearNode<T> top;

    public LinkedStack() {
        this.count = 0;
        this.top = null;
    }

    public void push(T element) {
        LinearNode<T> node = new LinearNode<>(element);

        if (!this.isEmpty()) {
            node.next = this.top;
        }

        this.top = node;
        this.count++;
    }

    public T pop() {
        if (this.isEmpty()) throw new EmptyCollectionException("Stack");

        T result = this.top.element;
        this.top = this.top.next;
        this.count--;

        return result;
    }

    public T peek() {
        if (this.isEmpty()) throw new EmptyCollectionException("Stack");

        return this.top.element;
    }

    public boolean isEmpty() {
        return this.top == null;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        LinearNode<T> current = this.top;

        while (current != null) {
            result.append((current.element).toString()).append("\n");
            current = current.next;
        }

        return result.toString();
    }

    private static class LinearNode<T> {
        private T element;
        private LinearNode<T> next;

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
    }
}
