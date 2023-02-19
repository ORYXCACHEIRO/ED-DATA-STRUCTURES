package pt.ipp.estg.data.structures.List;

public class OrderedArrayList<T> extends ArrayList<T> implements OrderedListADT<T> {
    public OrderedArrayList() {
        super();
    }

    public void add(T element) {
        if (this.size() == this.list.length) this.expandCapacity();

        Comparable<T> temp = (Comparable<T>) element;

        int i = 0;
        while (i < this.rear && temp.compareTo(this.list[i]) > 0) {
            i++;
        }

        for (int j = this.rear; j > i; j--) {
            this.list[j] = this.list[j - 1];
        }

        this.list[i] = element;
        this.rear++;
    }
}
