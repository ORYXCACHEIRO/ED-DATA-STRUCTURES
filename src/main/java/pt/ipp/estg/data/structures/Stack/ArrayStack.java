package pt.ipp.estg.data.structures.Stack;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class ArrayStack<T> implements StackADT<T> {
    private final int DEFAULT_CAPACITY = 100;
    private int top;
    private T[] stack;

    public ArrayStack() {
        this.top = 0;
        this.stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    private void expandCapacity() {
        T[] largerStack = (T[]) new Object[this.stack.length * 2];

        System.arraycopy(this.stack, 0, largerStack, 0, this.stack.length);

        this.stack = largerStack;
    }

    public void push(T element) {
        if (this.size() == this.stack.length) this.expandCapacity();

        this.stack[this.top] = element;
        this.top++;
    }

    public T pop() {
        if (this.isEmpty()) throw new EmptyCollectionException("Stack");

        this.top--;
        T result = this.stack[this.top];
        this.stack[this.top] = null;

        return result;
    }

    public T peek() {
        if (this.isEmpty()) throw new EmptyCollectionException("Stack");

        return this.stack[this.top - 1];
    }

    public boolean isEmpty() {
        return this.top == 0;
    }

    public int size() {
        return this.top;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int scan = 0; scan < this.top; scan++)
            result.append(this.stack[scan].toString()).append("\n");

        return result.toString();
    }
}
