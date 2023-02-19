package pt.ipp.estg.data.structures.Set;

import java.util.Iterator;

public interface SetADT<T> {
    void add(T element);

    T removeRandom();

    T remove(T target);

    SetADT<T> union(SetADT<T> set);

    boolean contains(T target);

    boolean equals(SetADT<T> set);

    boolean isEmpty();

    int size();

    Iterator<T> iterator();

    String toString();
}
