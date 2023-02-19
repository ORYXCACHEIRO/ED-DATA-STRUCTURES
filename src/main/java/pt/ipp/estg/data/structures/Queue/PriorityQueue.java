package pt.ipp.estg.data.structures.Queue;

import pt.ipp.estg.data.structures.Tree.Heap;

public class PriorityQueue<T> extends Heap<PriorityQueue.PriorityQueueNode<T>> {
    public PriorityQueue() {
        super();
    }

    public void addElement(T object, int priority) {
        PriorityQueueNode<T> node = new PriorityQueueNode<>(object, priority);
        super.addElement(node);
    }

    public T removeNext() {
        PriorityQueueNode<T> temp = super.removeMin();
        return temp.getElement();
    }

    public static class PriorityQueueNode<T> implements Comparable<PriorityQueueNode> {
        private static int nextorder = 0;
        private final int priority;
        private final int order;
        private final T element;

        public PriorityQueueNode(T obj, int prio) {
            element = obj;
            priority = prio;
            order = nextorder;
            nextorder++;
        }

        public T getElement() {
            return element;
        }

        public int getPriority() {
            return priority;
        }

        public int getOrder() {
            return order;
        }

        public int compareTo(PriorityQueueNode obj) {
            if (priority > ((PriorityQueueNode<T>) obj).getPriority()) {
                return 1;
            } else if (priority < ((PriorityQueueNode<T>) obj).getPriority()) {
                return -1;
            } else if (order > ((PriorityQueueNode<T>) obj).getOrder()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
