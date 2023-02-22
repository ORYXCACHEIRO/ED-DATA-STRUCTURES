package pt.ipp.estg.data.structures.Graph;

import pt.ipp.estg.data.structures.Exceptions.EmptyCollectionException;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;
import pt.ipp.estg.data.structures.Queue.LinkedQueue;
import pt.ipp.estg.data.structures.Queue.QueueADT;
import pt.ipp.estg.data.structures.Stack.LinkedStack;
import pt.ipp.estg.data.structures.Stack.StackADT;

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
        this.initAdjMatrix(largerAdjMatrix);

        for (int i = 0; i < super.numVertices; i++) {
            System.arraycopy(this.adjMatrix[i], 0, largerAdjMatrix[i], 0, super.numVertices);
            largerVertices[i] = super.vertices[i];
        }

        super.vertices = largerVertices;
        this.adjMatrix = largerAdjMatrix;
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
        if (super.isEmpty()) throw new EmptyCollectionException("Graph");

        int pos = super.getIndex(vertex);
        if (super.indexInvalid(pos)) throw new IllegalArgumentException();

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
        if (super.isEmpty()) throw new EmptyCollectionException("Graph");

        int index1 = super.getIndex(vertex1);
        int index2 = super.getIndex(vertex2);
        if (super.indexInvalid(index1) || super.indexInvalid(index2)) throw new IllegalArgumentException();
        if (this.adjMatrix[index1][index2] == Double.POSITIVE_INFINITY || this.adjMatrix[index2][index1] == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException();

        this.adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
        this.adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
    }

    public Iterator<T> iteratorBFS(T startVertex) {
        QueueADT<Integer> queue = new LinkedQueue<>();
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();
        boolean[] visited = new boolean[super.numVertices];

        int startIndex = startVertex == null ? 0 : super.getIndex(startVertex);
        if (super.indexInvalid(startIndex)) return resultList.iterator();

        queue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.dequeue();
            resultList.addToRear(super.vertices[currentVertex]);

            for (int i = 0; i < super.numVertices; i++) {
                if ((this.adjMatrix[currentVertex][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    visited[i] = true;
                    queue.enqueue(i);
                }
            }
        }

        return resultList.iterator();
    }

    public Iterator<T> iteratorDFS(T startVertex) {
        StackADT<Integer> stack = new LinkedStack<>();
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();
        boolean[] visited = new boolean[super.numVertices];

        int startIndex = startVertex == null ? 0 : super.getIndex(startVertex);
        if (super.indexInvalid(startIndex)) return resultList.iterator();

        stack.push(startIndex);

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();

            if (!visited[currentVertex]) {
                visited[currentVertex] = true;
                resultList.addToRear(super.vertices[currentVertex]);
            }

            for (int i = super.numVertices - 1; i >= 0; i--) {
                if ((this.adjMatrix[currentVertex][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    stack.push(i);
                }
            }
        }

        return resultList.iterator();
    }

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        UnorderedListADT<T> resultList = new UnorderedArrayList<>();

        int startIndex = startVertex == null ? 0 : this.getIndex(startVertex);
        int targetIndex = this.getIndex(targetVertex);
        if (super.indexInvalid(startIndex) || super.indexInvalid(targetIndex) || (startIndex == targetIndex))
            return resultList.iterator();

        double[] distances = new double[super.numVertices];
        int[] previous = new int[super.numVertices];
        boolean[] visited = new boolean[super.numVertices];

        for (int i = 0; i < super.numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            previous[i] = -1;
        }

        distances[startIndex] = 0;

        while (!visited[targetIndex]) {
            int current = getClosestUnvisited(distances, visited);
            visited[current] = true;

            for (int i = 0; i < super.numVertices; i++) {
                if (this.adjMatrix[current][i] < Double.POSITIVE_INFINITY && !visited[i]) {
                    double distance = distances[current] + this.adjMatrix[current][i];
                    if (distance < distances[i]) {
                        distances[i] = distance;
                        previous[i] = current;
                    }
                }
            }
        }

        int vertex = targetIndex;
        while (previous[vertex] != -1) {
            resultList.addToFront(super.vertices[vertex]);
            vertex = previous[vertex];
        }

        if (resultList.isEmpty()) return resultList.iterator();

        resultList.addToFront(startVertex);

        return resultList.iterator();
    }

    public double shortestPathWeight(T startVertex, T targetVertex) {
        int startIndex = startVertex == null ? 0 : this.getIndex(startVertex);
        int targetIndex = this.getIndex(targetVertex);
        if (super.indexInvalid(startIndex) || super.indexInvalid(targetIndex) || (startIndex == targetIndex)) return -1;

        double[] distances = new double[super.numVertices];
        boolean[] visited = new boolean[super.numVertices];

        for (int i = 0; i < super.numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }

        distances[startIndex] = 0;

        while (!visited[targetIndex]) {
            int current = getClosestUnvisited(distances, visited);
            visited[current] = true;

            for (int i = 0; i < super.numVertices; i++) {
                if (this.adjMatrix[current][i] < Double.POSITIVE_INFINITY && !visited[i]) {
                    double distance = distances[current] + this.adjMatrix[current][i];
                    if (distance < distances[i]) {
                        distances[i] = distance;
                    }
                }
            }
        }

        return distances[targetIndex];
    }

    private int getClosestUnvisited(double[] distances, boolean[] visited) {
        double minDistance = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int i = 0; i < super.numVertices; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minIndex = i;
            }
        }
        return minIndex;
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
