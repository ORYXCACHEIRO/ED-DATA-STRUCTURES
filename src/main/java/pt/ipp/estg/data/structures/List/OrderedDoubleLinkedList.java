package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.NonComparableElementException;

public class OrderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {
    public OrderedDoubleLinkedList() {
        super();
    }

    public void add(T element) {
        if (!(element instanceof Comparable)) throw new NonComparableElementException("List");

        Comparable<T> temp = (Comparable<T>) element;

        DoubleLinearNode<T> newNode = new DoubleLinearNode<T>(element);
        DoubleLinearNode<T> traverse = front;
        boolean found = false;

        if (super.isEmpty()) {
            super.front = newNode;
            super.rear = newNode;
        } else if (temp.compareTo(super.rear.getElement()) >= 0) {
            super.rear.setNext(newNode);
            newNode.setPrevious(super.rear);
            newNode.setNext(null);
            super.rear = newNode;
        } else if (temp.compareTo(super.front.getElement()) <= 0) {
            super.front.setPrevious(newNode);
            newNode.setNext(super.front);
            newNode.setPrevious(null);
            super.front = newNode;
        } else {
            while ((temp.compareTo(traverse.getElement()) > 0)) {
                traverse = traverse.getNext();
            }

            newNode.setNext(traverse);
            newNode.setPrevious(traverse.getPrevious());
            traverse.getPrevious().setNext(newNode);
            traverse.setPrevious(newNode);
        }

        super.count++;
    }
}
