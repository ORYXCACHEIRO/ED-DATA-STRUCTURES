package pt.ipp.estg.data.structures.Tree;

import java.util.Iterator;

public interface BinaryTreeADT<T> {
    T getRoot();

    void removeLeftSubtree();

    void removeRightSubtree();

    void removeAllElements();

    boolean contains(T targetElement);

    T find(T targetElement);

    boolean isEmpty();

    int size();

    Iterator<T> iteratorInOrder();

    Iterator<T> iteratorPreOrder();

    Iterator<T> iteratorPostOrder();

    Iterator<T> iteratorLevelOrder();

    String toString();
}
