package pt.ipp.estg.data.structures.List;

import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;

public class UnorderedArrayList<T> extends ArrayList<T> implements UnorderedListADT<T> {
    public UnorderedArrayList() {
        super();
    }

    public void addToFront(T element) {
        if (super.size() == super.list.length) super.expandCapacity();

        if (super.isEmpty()) {
            super.list[super.rear] = element;
            super.rear++;
            return;
        }

        for (int i = super.rear; i > 0; i--) {
            super.list[i] = super.list[i - 1];
        }

        super.list[0] = element;
        super.rear++;
    }

    public void addToRear(T element) {
        if (super.size() == super.list.length) super.expandCapacity();

        super.list[super.rear] = element;
        super.rear++;
    }

    public void addAfter(T element, T target) {
        if (super.size() == super.list.length) super.expandCapacity();

        int index = super.find(target);
        if (index == super.NOT_FOUND) throw new ElementNotFoundException("List");

        for (int j = super.rear; j > index; j--) {
            super.list[j] = super.list[j - 1];
        }

        super.list[index + 1] = element;
        super.rear++;
    }
}
