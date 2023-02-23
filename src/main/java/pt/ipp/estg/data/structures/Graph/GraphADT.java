package pt.ipp.estg.data.structures.Graph;

import java.util.Iterator;

public interface GraphADT<T> {
    void addVertex(T vertex);

    void removeVertex(T vertex);

    void addEdge(T vertex1, T vertex2);

    void removeEdge(T vertex1, T vertex2);

    Iterator<T> iteratorBFS(T startVertex);

    Iterator<T> iteratorDFS(T startVertex);

    Iterator<T> iteratorShortestPath(T startVertex, T targetVertex);

    int shortestPathLength(T startVertex, T targetVertex);

    Iterator<T> getAdjacentVertices(T vertex);

    Iterator<T> getVertices();

    boolean isConnected();

    boolean isEmpty();

    int size();
}
