package pt.ipp.estg.data.structures.Tree;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {
    public LinkedBinarySearchTree() {
        super();
    }

    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    public void addElement(T element) {

    }

    public T removeElement(T targetElement) {
        return null;
    }

    public void removeAllOccurrences(T targetElement) {

    }

    public T removeMin() {
        return null;
    }

    public T removeMax() {
        return null;
    }

    public T findMin() {
        BinaryTreeNode<T> current = super.root;

        while (current.left != null) {
            current = current.left;
        }

        return current.element;

    }

    public T findMax() {
        BinaryTreeNode<T> current = super.root;

        while (current.right != null) {
            current = current.right;
        }

        return current.element;

    }
}
