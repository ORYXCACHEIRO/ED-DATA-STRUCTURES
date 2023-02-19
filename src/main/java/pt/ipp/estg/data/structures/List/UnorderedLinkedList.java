package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class UnorderedLinkedList<T> extends LinkedList<T> implements UnorderedListADT<T> {
    public UnorderedLinkedList() {
        super();
    }

    public void addToFront(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if (super.isEmpty()) {
            super.head = newNode;
            super.count++;
            return;
        }

        newNode.setNext(super.head);
        super.head = newNode;
        super.count++;
    }

    public void addToRear(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if (super.isEmpty()) {
            super.head = newNode;
            count++;
            return;
        }

        LinearNode<T> current = super.head;

        while (current.getNext() != null) {
            current = current.getNext();
        }

        current.setNext(newNode);

        super.count++;
    }

    public void addAfter(T element, T target) {
        LinearNode<T> newNode = new LinearNode<>(element);
        LinearNode<T> current = super.head.getNext();

        Comparable<T> comp = (Comparable<T>) target;

        while (current.getNext() != null && !comp.equals(current.getElement())) {
            current = current.getNext();
        }

        if (!target.equals(current.getElement())) throw new EmptyCollectionException("List");

        newNode.setNext(current.getNext());
        current.setNext(newNode);

        super.count++;
    }
}
