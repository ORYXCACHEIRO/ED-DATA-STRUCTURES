package pt.ipp.estg.data.structures.Tree;

public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {
    void addElement(T element);

    T removeElement(T targetElement);

    void removeAllOccurrences(T targetElement);

    T removeMin();

    T removeMax();

    T findMin();

    T findMax();
}
