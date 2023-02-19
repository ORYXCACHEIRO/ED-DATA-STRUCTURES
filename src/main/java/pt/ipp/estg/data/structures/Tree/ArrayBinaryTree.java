package pt.ipp.estg.data.structures.Tree;

import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {
    private final int capacity = 50;
    protected int count;
    protected T[] tree;

    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) new Object[this.capacity];
    }

    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[this.capacity];
        tree[0] = element;
    }

    protected void expandCapacity() {
        T[] largerTree = (T[]) new Object[this.tree.length * 2];

        System.arraycopy(this.tree, 0, largerTree, 0, this.tree.length);

        this.tree = largerTree;
    }

    private void inOrder(int node, UnorderedListADT<T> tempList) {
        if (node < this.tree.length)
            if (this.tree[node] != null) {
                inOrder((node + 1) * 2 - 1, tempList);
                tempList.addToRear(this.tree[node]);
                inOrder((node + 1) * (2 + 1) - 1, tempList);
            }
    }

    private void preOrder(int node, UnorderedListADT<T> tempList) {
        if (node < this.tree.length) {
            if (this.tree[node] != null) {
                tempList.addToRear(this.tree[node]);
                inOrder((node + 1) * 2 - 1, tempList);
                inOrder((node + 1) * (2 + 1) - 1, tempList);
            }
        }
    }

    private void postOrder(int node, UnorderedListADT<T> tempList) {
        if (node < this.tree.length) {
            if (this.tree[node] != null) {
                inOrder((node + 1) * 2 - 1, tempList);
                inOrder((node + 1) * (2 + 1) - 1, tempList);
                tempList.addToRear(this.tree[node]);
            }
        }
    }

    public T getRoot() {
        return this.tree[0];
    }

    public void removeLeftSubtree() {

    }

    public void removeRightSubtree() {

    }

    public void removeAllElements() {
        this.count = 0;

        for (int i = 0; i < this.tree.length; i++) {
            this.tree[i] = null;
        }
    }

    public boolean contains(T targetElement) {
        for (int i = 0; i < this.count; i++) {
            if (targetElement.equals(this.tree[i])) {
                return true;
            }
        }

        return false;
    }

    public T find(T targetElement) {
        T temp = null;
        boolean found = false;

        for (int i = 0; i < this.count && !found; i++) {
            if (targetElement.equals(this.tree[i])) {
                found = true;
                temp = this.tree[i];
            }
        }

        if (!found) throw new ElementNotFoundException("Tree");

        return temp;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> tempList = new UnorderedArrayList<>();
        this.inOrder(0, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> tempList = new UnorderedArrayList<T>();
        this.preOrder(0, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> tempList = new UnorderedArrayList<T>();
        this.postOrder(0, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T> tempList = new UnorderedArrayList<T>();

        for (int ct = 0; ct < this.count; ct++) {
            tempList.addToRear(this.tree[ct]);
        }

        return tempList.iterator();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.count; i++) {
            result.append(this.tree[i]).append(" ");
        }

        return result.toString();
    }
}
