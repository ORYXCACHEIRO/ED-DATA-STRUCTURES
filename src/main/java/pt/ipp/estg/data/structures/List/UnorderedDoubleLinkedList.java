package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.NonComparableElementException;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {
    public UnorderedDoubleLinkedList() {
        super();
    }

    public void addToFront(T element) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if (super.isEmpty()) {
            super.front = newNode;
            super.rear = newNode;
            super.count++;
            return;
        }

        super.front.setPrevious(newNode);
        newNode.setNext(super.front);
        super.front = newNode;
        super.count++;
    }

    public void addToRear(T element) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if (super.isEmpty()) {
            super.front = newNode;
            super.rear = newNode;
            count++;
            return;
        }

        newNode.setPrevious(super.rear);
        super.rear.setNext(newNode);
        super.rear = newNode;
        super.count++;
    }

    public void addAfter(T element, T target) {
        if (!(element instanceof Comparable)) throw new NonComparableElementException("DoubleLinkedUnorderedList");

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);
        DoubleLinearNode<T> current = super.find(target);

        if (current == null) throw new NonComparableElementException("DoubleLinkedUnorderedList");

        if (current == super.rear) {
            this.addToRear(element);
            return;
        }

        newNode.setNext(current.getNext());
        newNode.setPrevious(current);
        current.getNext().setPrevious(newNode);
        current.setNext(newNode);
        super.count++;
    }
}
