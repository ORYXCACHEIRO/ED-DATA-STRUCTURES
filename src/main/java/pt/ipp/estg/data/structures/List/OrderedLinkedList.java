package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.NonComparableElementException;

public class OrderedLinkedList<T> extends LinkedList<T> implements OrderedListADT<T> {
    public OrderedLinkedList() {
        super();
    }

    public void add(T element) {
        if (!(element instanceof Comparable)) throw new NonComparableElementException("List");

        Comparable<T> temp = (Comparable<T>) element;

        LinearNode<T> newNode = new LinearNode<>(element);
        LinearNode<T> current = super.head;
        LinearNode<T> previous = null;

        while (current != null && temp.compareTo(current.getElement()) > 0) {
            previous = current;
            current = current.getNext();
        }

        if (previous == null) {
            newNode.setNext(super.head);
            this.head = newNode;
        } else {
            newNode.setNext(current);
            previous.setNext(newNode);
        }

        if (current == null) {
            super.tail = newNode;
        }

        super.count++;
    }
}
