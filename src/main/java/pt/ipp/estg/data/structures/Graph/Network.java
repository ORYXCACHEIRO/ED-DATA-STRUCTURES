package pt.ipp.estg.data.structures.Graph;

import pt.ipp.estg.data.structures.Queue.LinkedQueue;
import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;
import pt.ipp.estg.data.structures.Queue.QueueADT;
import pt.ipp.estg.data.structures.Stack.LinkedStack;
import pt.ipp.estg.data.structures.Stack.StackADT;
import pt.ipp.estg.data.structures.Tree.Heap;
import pt.ipp.estg.data.structures.Tree.HeapADT;

import java.util.Iterator;

public class Network<T> extends Graph<T> implements NetworkADT<T> {
    private double[][] adjMatrix;

    public Network() {
        super();
        this.adjMatrix = new double[super.DEFAULT_CAPACITY][super.DEFAULT_CAPACITY];
        this.initAdjMatrix(this.adjMatrix);
    }

    private void initAdjMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    private void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[super.vertices.length * 2]);
        double[][] largerAdjMatrix = new double[super.vertices.length * 2][super.vertices.length * 2];
        initAdjMatrix(largerAdjMatrix);

        for (int i = 0; i < super.numVertices; i++) {
            System.arraycopy(this.adjMatrix[i], 0, largerAdjMatrix[i], 0, super.numVertices);
            largerVertices[i] = super.vertices[i];
        }

        super.vertices = largerVertices;
        this.adjMatrix = largerAdjMatrix;
    }

    protected int getIndexOfAdjVertexWithWeightOf(boolean[] visited, double[] pathWeight, double weight) {
        for (int i = 0; i < numVertices; i++) {
            if ((pathWeight[i] == weight) && !visited[i]) {
                for (int j = 0; j < numVertices; j++) {
                    if ((adjMatrix[i][j] < Double.POSITIVE_INFINITY) && visited[j]) return i;
                }
            }
        }
        return -1;
    }

    public void addVertex(T vertex) {
        if (super.numVertices == super.vertices.length) this.expandCapacity();

        super.vertices[super.numVertices] = vertex;

        for (int i = 0; i < super.numVertices; i++) {
            this.adjMatrix[super.numVertices][i] = Double.POSITIVE_INFINITY;
            this.adjMatrix[i][super.numVertices] = Double.POSITIVE_INFINITY;
        }

        super.numVertices++;
    }

    public void removeVertex(T vertex) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph");

        int pos = getIndex(vertex);

        if (indexInvalid(pos)) throw new IllegalArgumentException();

        for (int i = 0; i < super.numVertices; i++) {
            this.adjMatrix[i][pos] = Double.POSITIVE_INFINITY;
            this.adjMatrix[pos][i] = Double.POSITIVE_INFINITY;
        }

        for (int i = pos; i < super.numVertices - 1; i++) {
            super.vertices[i] = super.vertices[i + 1];
            for (int j = 0; j < super.numVertices; j++) {
                this.adjMatrix[i][j] = this.adjMatrix[i + 1][j];
                this.adjMatrix[j][i] = this.adjMatrix[j][i + 1];
            }
        }

        super.numVertices--;
    }

    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = super.getIndex(vertex1);
        int index2 = super.getIndex(vertex2);

        if (super.indexInvalid(index1) || super.indexInvalid(index2)) throw new IllegalArgumentException();

        if (this.adjMatrix[index1][index2] == Double.POSITIVE_INFINITY || this.adjMatrix[index2][index1] == Double.POSITIVE_INFINITY) {
            this.adjMatrix[index1][index2] = weight;
            this.adjMatrix[index2][index1] = weight;
        }
    }

    public void removeEdge(T vertex1, T vertex2) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph");

        int index1 = super.getIndex(vertex1);
        int index2 = super.getIndex(vertex2);

        if (super.indexInvalid(index1) || super.indexInvalid(index2)) throw new IllegalArgumentException();
        if (this.adjMatrix[index1][index2] == Double.POSITIVE_INFINITY || this.adjMatrix[index2][index1] == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException();

        adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
        adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
    }

    public double getEdgeWeight(T vertex1, T vertex2) {
        int index1 = super.getIndex(vertex1);
        int index2 = super.getIndex(vertex2);

        if (super.indexInvalid(index1) || super.indexInvalid(index2)) throw new IllegalArgumentException();

        return this.adjMatrix[index1][index2];
    }

    public Iterator<T> iteratorBFS(T startVertex) {
        QueueADT<Integer> traversalQueue = new LinkedQueue<>();
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();
        Integer x;

        int startIndex = super.getIndex(startVertex);
        if (super.indexInvalid(startIndex)) return resultList.iterator();

        boolean[] visited = new boolean[super.numVertices];
        for (int i = 0; i < super.numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(super.vertices[x]);

            for (int i = 0; i < super.numVertices; i++) {
                if ((this.adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
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

        int startIndex = super.getIndex(startVertex);
        if (super.indexInvalid(startIndex)) return resultList.iterator();

        boolean[] visited = new boolean[super.numVertices];
        for (int i = 0; i < super.numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(super.vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; (i < super.numVertices) && !found; i++) {
                if ((this.adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(super.vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }

            if (!found && !traversalStack.isEmpty()) traversalStack.pop();
        }

        return resultList.iterator();
    }

    public Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index;
        double weight;
        int[] predecessor = new int[numVertices];
        HeapADT<Double> traversalMinHeap = new Heap<>();
        UnorderedListADT<Integer> resultList = new UnorderedArrayList<>();
        StackADT<Integer> stack = new LinkedStack<>();

        int[] pathIndex = new int[numVertices];

        if (this.indexInvalid(startIndex) || this.indexInvalid(targetIndex) || (startIndex == targetIndex))
            return resultList.iterator();

        double[] pathWeight = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            pathWeight[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }

        pathWeight[startIndex] = 0;
        predecessor[startIndex] = -1;
        visited[startIndex] = true;
        weight = 0;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                pathWeight[i] = pathWeight[startIndex] + adjMatrix[startIndex][i];
                predecessor[i] = startIndex;
                traversalMinHeap.addElement(pathWeight[i]);
            }
        }

        do {
            weight = traversalMinHeap.removeMin();
            traversalMinHeap.removeAllElements();

            if (weight == Double.POSITIVE_INFINITY)
                return resultList.iterator();
            else {
                index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight, weight);
                visited[index] = true;
            }

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY) && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                        pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                        predecessor[i] = index;
                    }
                    traversalMinHeap.addElement(pathWeight[i]);
                }
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]);

        index = targetIndex;
        stack.push(index);
        do {
            index = predecessor[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty())
            resultList.addToRear((stack.pop()));

        return resultList.iterator();
    }

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();

        int startIndex = this.getIndex(startVertex);
        int targetIndex = this.getIndex(targetVertex);
        if (this.indexInvalid(startIndex) || this.indexInvalid(targetIndex) || (startIndex == targetIndex))
            return resultList.iterator();

        Iterator<Integer> iterator = this.iteratorShortestPathIndices(startIndex, targetIndex);

        while (iterator.hasNext()) {
            resultList.addToRear(super.vertices[iterator.next()]);
        }

        return resultList.iterator();
    }

    public double shortestPathWeight(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        double result = 0;

        if (this.indexInvalid(startIndex) || this.indexInvalid(targetIndex) || (startIndex == targetIndex))
            return Double.POSITIVE_INFINITY;

        int index1, index2;
        Iterator<Integer> iterator = this.iteratorShortestPathIndices(startIndex, targetIndex);

        if (iterator.hasNext()) {
            index1 = iterator.next();
        } else {
            return Double.POSITIVE_INFINITY;
        }

        while (iterator.hasNext()) {
            index2 = iterator.next();
            result += this.adjMatrix[index1][index2];
            index1 = index2;
        }

        return result;
    }

    public String toString() {
        if (super.numVertices == 0) return "Graph is empty";

        StringBuilder result = new StringBuilder();

        result.append("Adjacency Matrix\n");
        result.append("----------------\n");
        result.append("index\t");

        for (int i = 0; i < super.numVertices; i++) {
            result.append(i);
            if (i < 10) result.append(" ");
        }
        result.append("\n\n");

        for (int i = 0; i < super.numVertices; i++) {
            result.append(i).append("\t");

            for (int j = 0; j < super.numVertices; j++) {
                if (this.adjMatrix[i][j] < Double.POSITIVE_INFINITY) {
                    result.append("1 ");
                } else {
                    result.append("0 ");
                }
            }
            result.append("\n");
        }

        result.append("\n\nVertex Values");
        result.append("\n-------------\n");
        result.append("index\tvalue\n\n");

        for (int i = 0; i < super.numVertices; i++) {
            result.append(i).append("\t");
            result.append(super.vertices[i].toString()).append("\n");
        }

        result.append("\n\nWeights of Edges");
        result.append("\n----------------\n");
        result.append("index\tweight\n\n");

        for (int i = 0; i < super.numVertices; i++) {
            for (int j = super.numVertices - 1; j > i; j--) {
                if (this.adjMatrix[i][j] < Double.POSITIVE_INFINITY) {
                    result.append(i).append(" to ").append(j).append("\t");
                    result.append(this.adjMatrix[i][j]).append("\n");
                }
            }
        }

        result.append("\n");

        return result.toString();
    }
}
