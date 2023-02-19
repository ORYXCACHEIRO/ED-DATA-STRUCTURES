package pt.ipp.estg.data.structures.Tree;

import pt.ipp.estg.data.structures.Queue.LinkedQueue;
import pt.ipp.estg.data.structures.Exceptions.ElementNotFoundException;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.Queue.QueueADT;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {
    protected int count;
    protected BinaryTreeNode<T> root;

    public LinkedBinaryTree() {
        this.count = 0;
        this.root = null;
    }

    public LinkedBinaryTree(T element) {
        this.count = 1;
        this.root = new BinaryTreeNode<>(element);
    }

    private void inOrder(BinaryTreeNode<T> node, UnorderedArrayList<T> tempList) {
        if (node == null) return;

        inOrder(node.left, tempList);
        tempList.addToRear(node.element);
        inOrder(node.right, tempList);
    }

    private void preOrder(BinaryTreeNode<T> node, UnorderedArrayList<T> tempList) {
        if (node == null) return;

        tempList.addToRear(node.element);
        preOrder(node.left, tempList);
        preOrder(node.right, tempList);
    }

    private void postOrder(BinaryTreeNode<T> node, UnorderedArrayList<T> tempList) {
        if (node == null) return;

        postOrder(node.left, tempList);
        postOrder(node.right, tempList);
        tempList.addToRear(node.element);
    }

    private void levelOrder(BinaryTreeNode<T> node, UnorderedArrayList<T> tempList) {
        if (node == null) return;

        QueueADT<BinaryTreeNode<T>> queue = new LinkedQueue<>();
        queue.enqueue(node);

        while (!queue.isEmpty()) {
            BinaryTreeNode<T> tmp = queue.dequeue();
            tempList.addToRear(tmp.element);

            if (tmp.left != null) {
                queue.enqueue(node.left);
            }

            if (tmp.right != null) {
                queue.enqueue(node.right);
            }
        }
    }

    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) return null;

        if (next.element.equals(targetElement)) return next;

        BinaryTreeNode<T> tmp = findAgain(targetElement, next.left);

        if (tmp == null) tmp = findAgain(targetElement, next.right);

        return tmp;
    }

    public T getRoot() {
        return this.root.element;
    }

    public void removeLeftSubtree() {

    }

    public void removeRightSubtree() {

    }

    public void removeAllElements() {

    }

    public boolean contains(T targetElement) {
        BinaryTreeNode<T> found = this.findAgain(targetElement, this.root);

        return found != null;
    }

    public T find(T targetElement) {
        BinaryTreeNode<T> tmp = this.findAgain(targetElement, this.root);

        if (tmp == null) throw new ElementNotFoundException("Tree");

        return tmp.element;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public int size() {
        return this.count;
    }

    public Iterator<T> iteratorInOrder() {
        UnorderedArrayList<T> tempList = new UnorderedArrayList<>();
        inOrder(this.root, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorPreOrder() {
        UnorderedArrayList<T> tempList = new UnorderedArrayList<>();
        preOrder(this.root, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorPostOrder() {
        UnorderedArrayList<T> tempList = new UnorderedArrayList<>();
        postOrder(this.root, tempList);

        return tempList.iterator();
    }

    public Iterator<T> iteratorLevelOrder() {
        UnorderedArrayList<T> tempList = new UnorderedArrayList<>();
        levelOrder(this.root, tempList);

        return tempList.iterator();
    }

    protected static class BinaryTreeNode<T> {

        public T element;
        public BinaryTreeNode<T> left;
        public BinaryTreeNode<T> right;

        public BinaryTreeNode(T element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public BinaryTreeNode<T> getLeft() {
            return left;
        }

        public void setLeft(BinaryTreeNode<T> left) {
            this.left = left;
        }

        public BinaryTreeNode<T> getRight() {
            return right;
        }

        public void setRight(BinaryTreeNode<T> right) {
            this.right = right;
        }
    }
}
