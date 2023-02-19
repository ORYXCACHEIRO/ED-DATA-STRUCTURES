package pt.ipp.estg.data.structures.Tree;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;

public class Heap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {
    public HeapNode<T> lastNode;

    public Heap() {
        super();
    }

    public Heap(T element) {
        super(element);
    }

    private HeapNode<T> getNextParentAdd() {
        HeapNode<T> result = this.lastNode;

        while ((result != root) && (result.parent.left != result)) {
            result = result.parent;
        }

        if (result != root) {
            if (result.parent.right == null) {
                result = result.parent;
            } else {
                result = (HeapNode<T>) result.parent.right;
                while (result.left != null) {
                    result = (HeapNode<T>) result.left;
                }
            }
        } else {
            while (result.left != null) {
                result = (HeapNode<T>) result.left;
            }
        }

        return result;
    }

    private void heapifyAdd() {
        T temp;
        HeapNode<T> next = this.lastNode;
        temp = next.element;

        while ((next != root) && (((Comparable) temp).compareTo(next.parent.element) < 0)) {
            next.element = next.parent.element;
            next = next.parent;
        }

        next.element = temp;
    }

    private void heapifyRemove() {
        T temp;
        HeapNode<T> node = (HeapNode<T>) root;
        HeapNode<T> left = (HeapNode<T>) node.left;
        HeapNode<T> right = (HeapNode<T>) node.right;
        HeapNode<T> next;

        if ((left == null) && (right == null)) {
            next = null;
        } else if (left == null) {
            next = right;
        } else if (right == null) {
            next = left;
        } else if (((Comparable) left.element).compareTo(right.element) < 0) {
            next = left;
        } else {
            next = right;
        }

        temp = node.element;
        while ((next != null) && (((Comparable) next.element).compareTo(node.element) < 0)) {
            node.element = next.element;
            node = next;
            left = (HeapNode<T>) node.left;
            right = (HeapNode<T>) node.right;
            if ((left == null) && (right == null)) {
                next = null;
            } else if (left == null) {
                next = right;
            } else if (right == null) {
                next = left;
            } else if (((Comparable) left.element).compareTo(right.element) < 0) {
                next = left;
            } else {
                next = right;
            }
        }

        node.element = temp;
    }

    private HeapNode<T> getNewLastNode() {
        HeapNode<T> result = this.lastNode;

        while ((result != root) && (result.parent.left == result)) {
            result = result.parent;
        }
        if (result != root) {
            result = (HeapNode<T>) result.parent.left;
        }

        while (result.right != null) {
            result = (HeapNode<T>) result.right;
        }

        return result;
    }

    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<>(obj);

        if (super.root == null) {
            super.root = node;
        } else {
            HeapNode<T> nextParent = this.getNextParentAdd();

            if (nextParent.left == null) {
                nextParent.left = node;
            } else if (nextParent.right == null) {
                nextParent.right = node;
            }

            node.parent = nextParent;
        }

        this.lastNode = node;
        super.count++;

        if (count > 1) this.heapifyAdd();
    }

    public T removeMin() {
        if (super.isEmpty()) throw new EmptyCollectionException("Heap");

        T minElement = super.root.element;

        if (super.count == 1) {
            super.root = null;
            this.lastNode = null;
        } else {
            HeapNode<T> nextLast = this.getNewLastNode();

            if (this.lastNode.parent.left == this.lastNode) {
                this.lastNode.parent.left = null;
            } else {
                this.lastNode.parent.right = null;
            }

            super.root.element = this.lastNode.element;
            this.lastNode = nextLast;
            this.heapifyRemove();
        }

        super.count--;

        return minElement;
    }

    public T findMin() throws EmptyCollectionException {
        if (super.isEmpty()) throw new EmptyCollectionException("Heap");

        return root.element;
    }

    private static class HeapNode<T> extends BinaryTreeNode<T> {
        protected HeapNode<T> parent;

        HeapNode(T obj) {
            super(obj);
            parent = null;
        }
    }
}
