package pt.ipp.estg.data.structures.Tree;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;

import java.util.Iterator;

public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {
    protected int height;
    protected int maxIndex;

    public ArrayBinarySearchTree() {
        super();
        height = 0;
        maxIndex = -1;
    }

    public ArrayBinarySearchTree(T element) {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    protected void replace(int targetIndex) {
        int currentIndex, parentIndex, temp, oldIndex, newIndex;
        UnorderedListADT<Integer> oldList = new UnorderedArrayList<>();
        UnorderedListADT<Integer> newList = new UnorderedArrayList<Integer>();
        UnorderedListADT<Integer> tempList = new UnorderedArrayList<Integer>();
        Iterator<Integer> oldIt, newIt;

        if ((targetIndex * 2 + 1 >= tree.length) || (targetIndex * 2 + 2 >= tree.length)) {
            tree[targetIndex] = null;
        } else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] == null)) {
            tree[targetIndex] = null;
        } else if ((tree[targetIndex * 2 + 1] != null) && (tree[targetIndex * 2 + 2] == null)) {
            currentIndex = targetIndex * 2 + 1;
            tempList.addToRear(currentIndex);

            while (!tempList.isEmpty()) {
                currentIndex = tempList.removeFirst();
                newList.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    tempList.addToRear(currentIndex * 2 + 1);
                    tempList.addToRear(currentIndex * 2 + 2);
                }
            }

            currentIndex = targetIndex;
            tempList.addToRear(currentIndex);

            while (!tempList.isEmpty()) {
                currentIndex = tempList.removeFirst().intValue();
                oldList.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    tempList.addToRear(currentIndex * 2 + 1);
                    tempList.addToRear(currentIndex * 2 + 2);
                }
            }

            oldIt = oldList.iterator();
            newIt = newList.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] != null)) {
            currentIndex = targetIndex * 2 + 2;
            tempList.addToRear(currentIndex);
            while (!tempList.isEmpty()) {
                currentIndex = tempList.removeFirst();
                newList.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    tempList.addToRear(currentIndex * 2 + 1);
                    tempList.addToRear(currentIndex * 2 + 2);
                }
            }

            currentIndex = targetIndex;
            tempList.addToRear(currentIndex);
            while (!tempList.isEmpty()) {
                currentIndex = tempList.removeFirst();
                oldList.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    tempList.addToRear(currentIndex * 2 + 1);
                    tempList.addToRear(currentIndex * 2 + 2);
                }
            }

            oldIt = oldList.iterator();
            newIt = newList.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } else {
            currentIndex = targetIndex * 2 + 2;

            while (tree[currentIndex * 2 + 1] != null) {
                currentIndex = currentIndex * 2 + 1;
            }

            tree[targetIndex] = tree[currentIndex];

            int currentRoot = currentIndex;

            if (tree[currentRoot * 2 + 2] != null) {
                currentIndex = currentRoot * 2 + 2;
                tempList.addToRear(currentIndex);
                while (!tempList.isEmpty()) {
                    currentIndex = tempList.removeFirst();
                    newList.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        tempList.addToRear(currentIndex * 2 + 1);
                        tempList.addToRear(currentIndex * 2 + 2);
                    }
                }

                currentIndex = currentRoot;
                tempList.addToRear(currentIndex);
                while (!tempList.isEmpty()) {
                    currentIndex = tempList.removeFirst();
                    oldList.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        tempList.addToRear(currentIndex * 2 + 1);
                        tempList.addToRear(currentIndex * 2 + 2);
                    }
                }

                oldIt = oldList.iterator();
                newIt = newList.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();
                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            } else
                tree[currentRoot] = null;
        }

    }

    public void addElement(T element) {
        if (tree.length < maxIndex * 2 + 3)
            expandCapacity();

        Comparable<T> tempElement = (Comparable<T>) element;

        if (isEmpty()) {
            super.tree[0] = element;
            this.maxIndex = 0;
        } else {
            boolean added = false;
            int currentIndex = 0;

            while (!added) {
                if (tempElement.compareTo((super.tree[currentIndex])) < 0) {
                    if (super.tree[currentIndex * 2 + 1] == null) {
                        super.tree[currentIndex * 2 + 1] = element;
                        added = true;
                        if (currentIndex * 2 + 1 > this.maxIndex)
                            this.maxIndex = currentIndex * 2 + 1;
                    } else
                        currentIndex = currentIndex * 2 + 1;
                } else {
                    if (super.tree[currentIndex * 2 + 2] == null) {
                        super.tree[currentIndex * 2 + 2] = element;
                        added = true;
                        if (currentIndex * 2 + 2 > this.maxIndex)
                            this.maxIndex = currentIndex * 2 + 2;
                    } else
                        currentIndex = currentIndex * 2 + 2;
                }

            }
        }

        this.height = (int) (Math.log(this.maxIndex + 1) / Math.log(2)) + 1;
        super.count++;
    }

    public T removeElement(T targetElement) {
        T result = null;
        boolean found = false;

        if (super.isEmpty()) return null;

        for (int i = 0; (i <= this.maxIndex) && !found; i++) {
            if ((super.tree[i] != null) && targetElement.equals(super.tree[i])) {
                found = true;
                result = super.tree[i];
                replace(i);
                super.count--;
            }
        }

        if (!found) throw new EmptyCollectionException("Tree");

        int temp = maxIndex;
        maxIndex = -1;
        for (int i = 0; i <= temp; i++)
            if (tree[i] != null)
                maxIndex = i;

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;
    }

    public T[] getArray() {
        T[] temp;
        if (size() == 0) {
            temp = (T[]) new Object[0];
            return temp;
        }

        temp = (T[]) new Object[super.tree.length];
        for (int i = 0; i < super.tree.length; i++) {
            if (super.tree[i] != null)
                temp[i] = super.tree[i];
            else
                temp[i] = null;
        }
        return temp;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public void removeAllElements() {
        super.removeAllElements();
        height = 0;
        maxIndex = -1;
    }

    public void removeAllOccurrences(T targetElement) {
        removeElement(targetElement);

        while (contains(targetElement)) {
            removeElement(targetElement);
        }
    }

    public T removeMin() {
        T result;

        if (super.isEmpty()) throw new EmptyCollectionException("Tree");

        int currentIndex = 1;
        int previousIndex = 0;
        while (super.tree[currentIndex] != null && currentIndex <= super.tree.length) {
            previousIndex = currentIndex;
            currentIndex = currentIndex * 2 + 1;
        }

        result = super.tree[previousIndex];
        replace(previousIndex);

        super.count--;

        return result;
    }

    public T removeMax() {
        T result;

        if (super.isEmpty()) throw new EmptyCollectionException("Tree");

        int currentIndex = 2;
        int previousIndex = 0;
        while (super.tree[currentIndex] != null && currentIndex <= this.maxIndex) {
            previousIndex = currentIndex;
            currentIndex = currentIndex * 2 + 2;
        }
        result = super.tree[previousIndex];
        replace(previousIndex);

        super.count--;

        return result;
    }

    public T findMin() {
        T result;

        if (super.isEmpty()) throw new EmptyCollectionException("Tree");

        int currentIndex = 0;
        while ((currentIndex * 2 + 1 <= this.maxIndex) && (super.tree[currentIndex * 2 + 1] != null)) {
            currentIndex = currentIndex * 2 + 1;
        }

        result = super.tree[currentIndex];

        return result;
    }

    public T findMax() {
        T result;

        if (super.isEmpty()) throw new EmptyCollectionException("Tree");

        int currentIndex = 0;
        while ((currentIndex * 2 + 2 <= this.maxIndex) && (super.tree[currentIndex * 2 + 2] != null)) {
            currentIndex = currentIndex * 2 + 2;
        }

        result = super.tree[currentIndex];

        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i <= maxIndex; i++) {
            if (tree[i] != null) {
                result.append(tree[i].toString()).append("\n");
            }
        }

        return result.toString();
    }
}
