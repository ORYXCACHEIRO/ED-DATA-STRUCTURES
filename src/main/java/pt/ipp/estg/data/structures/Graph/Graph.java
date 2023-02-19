package pt.ipp.estg.data.structures.Graph;

import pt.ipp.estg.data.structures.Queue.LinkedQueue;
import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;
import pt.ipp.estg.data.structures.Queue.QueueADT;
import pt.ipp.estg.data.structures.Stack.LinkedStack;
import pt.ipp.estg.data.structures.Stack.StackADT;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {
    protected final int DEFAULT_CAPACITY = 16;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    protected T[] vertices;

    public Graph() {
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
        this.initAdjMatrix(this.adjMatrix);
    }

    private void initAdjMatrix(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = false;
            }
        }
    }

    private void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[this.vertices.length * 2]);
        boolean[][] largerAdjMatrix = new boolean[this.vertices.length * 2][this.vertices.length * 2];
        this.initAdjMatrix(largerAdjMatrix);

        for (int i = 0; i < this.numVertices; i++) {
            System.arraycopy(this.adjMatrix[i], 0, largerAdjMatrix[i], 0, this.numVertices);
            largerVertices[i] = this.vertices[i];
        }

        this.vertices = largerVertices;
        this.adjMatrix = largerAdjMatrix;
    }

    protected int getIndex(T vertex) {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }

        return -1;
    }

    protected boolean indexInvalid(int index) {
        return index < 0 && index >= this.numVertices;
    }

    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index = startIndex;
        int[] pathLength = new int[this.numVertices];
        int[] predecessor = new int[this.numVertices];

        QueueADT<Integer> traversalQueue = new LinkedQueue<>();
        UnorderedListADT<Integer> resultList = new UnorderedArrayList<>();

        if (this.indexInvalid(startIndex) || this.indexInvalid(targetIndex) || (startIndex == targetIndex))
            return resultList.iterator();

        boolean[] visited = new boolean[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            index = traversalQueue.dequeue();

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) {
                    pathLength[i] = pathLength[index] + 1;
                    predecessor[i] = index;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }

        if (index != targetIndex) return resultList.iterator();

        StackADT<Integer> stack = new LinkedStack<>();
        index = targetIndex;
        stack.push(index);

        do {
            index = predecessor[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop());
        }

        return resultList.iterator();
    }

    public void addVertex(T vertex) {
        if (this.numVertices == this.vertices.length) this.expandCapacity();

        this.vertices[this.numVertices] = vertex;

        for (int i = 0; i < this.numVertices; i++) {
            this.adjMatrix[this.numVertices][i] = false;
            this.adjMatrix[i][this.numVertices] = false;
        }

        this.numVertices++;
    }

    public void removeVertex(T vertex) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph");

        int pos = this.getIndex(vertex);

        if (this.indexInvalid(pos)) throw new IllegalArgumentException();

        for (int i = 0; i < this.numVertices; i++) {
            if (this.adjMatrix[pos][i]) this.adjMatrix[pos][i] = false;
            if (this.adjMatrix[i][pos]) this.adjMatrix[i][pos] = false;
        }

        for (int i = pos; i < this.numVertices - 1; i++) {
            this.vertices[i] = this.vertices[i + 1];
            for (int j = 0; j < this.numVertices; j++) {
                this.adjMatrix[i][j] = this.adjMatrix[i + 1][j];
                this.adjMatrix[j][i] = this.adjMatrix[j][i + 1];
            }
        }

        this.numVertices--;
    }

    public void addEdge(T vertex1, T vertex2) {
        int index1 = this.getIndex(vertex1);
        int index2 = this.getIndex(vertex2);

        if (this.indexInvalid(index1) || this.indexInvalid(index2)) throw new IllegalArgumentException();

        if (!this.adjMatrix[index1][index2] || !this.adjMatrix[index2][index1]) {
            this.adjMatrix[index1][index2] = true;
            this.adjMatrix[index2][index1] = true;
        }
    }

    public void removeEdge(T vertex1, T vertex2) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph");

        int pos1 = this.getIndex(vertex1);
        int pos2 = this.getIndex(vertex2);

        if (this.indexInvalid(pos1) || this.indexInvalid(pos2)) throw new IllegalArgumentException();
        if (!this.adjMatrix[pos1][pos2] || !this.adjMatrix[pos2][pos2]) throw new IllegalArgumentException();

        this.adjMatrix[pos1][pos2] = false;
        this.adjMatrix[pos2][pos1] = false;
    }

    public Iterator<T> iteratorBFS(T startVertex) {
        QueueADT<Integer> traversalQueue = new LinkedQueue<>();
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();
        Integer x;

        int startIndex = this.getIndex(startVertex);
        if (this.indexInvalid(startIndex)) return resultList.iterator();

        boolean[] visited = new boolean[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(this.vertices[x]);

            for (int i = 0; i < this.numVertices; i++) {
                if (this.adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }

        return resultList.iterator();
    }

    public Iterator<T> iteratorDFS(T startVertex) {
        StackADT<Integer> traversalStack = new LinkedStack<>();
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();
        Integer x;
        boolean found;

        int startIndex = this.getIndex(startVertex);
        if (this.indexInvalid(startIndex)) return resultList.iterator();

        boolean[] visited = new boolean[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(this.vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; (i < this.numVertices) && !found; i++) {
                if (this.adjMatrix[x][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(this.vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }

            if (!found && !traversalStack.isEmpty()) traversalStack.pop();
        }

        return resultList.iterator();
    }

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();

        int startIndex = this.getIndex(startVertex);
        int targetIndex = this.getIndex(targetVertex);
        if (this.indexInvalid(startIndex) || this.indexInvalid(targetIndex) || (startIndex == targetIndex))
            return resultList.iterator();

        Iterator<Integer> iterator = iteratorShortestPathIndices(startIndex, targetIndex);

        while (iterator.hasNext()) {
            resultList.addToRear(this.vertices[iterator.next()]);
        }

        return resultList.iterator();
    }

    public int shortestPathLength(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        int result = 0;

        if (this.indexInvalid(startIndex) || this.indexInvalid(targetIndex) || (startIndex == targetIndex))
            return 0;

        Iterator<Integer> iterator = iteratorShortestPathIndices(startIndex, targetIndex);

        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }

        return result;
    }

    public boolean isConnected() {
        if (this.isEmpty()) return false;

        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (this.adjMatrix[i][j] != this.adjMatrix[j][i]) return false;
            }
        }

        return true;
    }

    public boolean isEmpty() {
        return this.numVertices == 0;
    }

    public int size() {
        return this.numVertices;
    }

    public String toString() {
        if (this.numVertices == 0) return "Graph is empty";

        StringBuilder result = new StringBuilder();

        result.append("Adjacency Matrix\n");
        result.append("----------------\n");
        result.append("index\t");

        for (int i = 0; i < this.numVertices; i++) {
            result.append(i);
            if (i < 10)
                result.append(" ");
        }
        result.append("\n\n");

        for (int i = 0; i < this.numVertices; i++) {
            result.append(i).append("\t");

            for (int j = 0; j < this.numVertices; j++) {
                if (this.adjMatrix[i][j])
                    result.append("1 ");
                else
                    result.append("0 ");
            }
            result.append("\n");
        }

        result.append("\n\nVertex Values");
        result.append("\n-------------\n");
        result.append("index\tvalue\n\n");

        for (int i = 0; i < this.numVertices; i++) {
            result.append(i).append("\t");
            result.append(this.vertices[i].toString()).append("\n");
        }
        result.append("\n");

        return result.toString();
    }
}
