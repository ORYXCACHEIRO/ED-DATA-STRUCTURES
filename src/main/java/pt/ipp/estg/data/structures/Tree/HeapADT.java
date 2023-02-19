package pt.ipp.estg.data.structures.Tree;

public interface HeapADT<T> extends BinaryTreeADT<T> {
    void addElement(T obj);

    T removeMin();

    T findMin();
}
