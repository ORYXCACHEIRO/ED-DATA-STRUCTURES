package pt.ipp.estg.data.structures.Tree;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class ArrayHeap<T> extends ArrayBinaryTree<T> implements HeapADT<T> {
    public ArrayHeap() {
        super();
    }

    private void heapifyAdd() {
        int next = super.count - 1;

        while ((next != 0) && (((Comparable) super.tree[next]).compareTo(super.tree[(next - 1) / 2]) < 0)) {
            T temp = super.tree[next];
            super.tree[next] = super.tree[(next - 1) / 2];
            super.tree[(next - 1) / 2] = temp;
            next = (next - 1) / 2;
        }
    }

    private void heapifyRemove() {
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((tree[left] == null) && (tree[right] == null)) {
            next = count;
        } else if (tree[left] == null) {
            next = right;
        } else if (tree[right] == null) {
            next = left;
        } else if (((Comparable) tree[left]).compareTo(tree[right]) < 0) {
            next = left;
        } else {
            next = right;
        }

        while ((next < super.count) && (((Comparable) super.tree[next]).compareTo(super.tree[node]) < 0)) {
            T temp = super.tree[node];
            super.tree[node] = super.tree[next];
            super.tree[next] = temp;
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((super.tree[left] == null) && (super.tree[right] == null)) {
                next = super.count;
            } else if (super.tree[left] == null) {
                next = right;
            } else if (super.tree[right] == null) {
                next = left;
            } else if (((Comparable) super.tree[left]).compareTo(super.tree[right]) < 0) {
                next = left;
            } else {
                next = right;
            }
        }
    }

    public void addElement(T obj) {
        if (super.count == size()) super.expandCapacity();

        super.tree[super.count] = obj;
        super.count++;

        if (super.count > 1) this.heapifyAdd();
    }

    public T removeMin() {
        if (super.isEmpty()) throw new EmptyCollectionException("Heap");

        T minElement = super.tree[0];

        super.tree[0] = super.tree[super.count - 1];
        heapifyRemove();
        super.count--;
        return minElement;
    }

    public T findMin() {
        if (super.isEmpty()) throw new EmptyCollectionException("Heap");

        return tree[0];
    }
}
