package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Реализация графа на основе матрицы смежности
 */
public class AdjMatrixGraph implements Graph {

    private boolean[][] adjMatrix = null;
    private int vCount = 0;
    private int eCount = 0;

    /**
     * Конструктор
     * @param vertexCount Кол-во вершин графа (может увеличиваться при добавлении ребер)
     */
    public AdjMatrixGraph(int vertexCount) {
        adjMatrix = new boolean[vertexCount][vertexCount];
        vCount = vertexCount;
    }

    /**
     * Конструктор без парметров
     * (лучше не использовать, т.к. при добавлении вершин каждый раз пересоздается матрица)
     */
    public AdjMatrixGraph() {
        this(0);
    }

    @Override
    public int vertexCount() {
        return vCount;
    }

    @Override
    public int edgeCount() {
        return eCount;
    }

    @Override
    public void addAdge(int v1, int v2) {
        int maxV = Math.max(v1, v2);
        if (maxV >= vertexCount()) {
            adjMatrix = Arrays.copyOf(adjMatrix, maxV + 1);
            for (int i = 0; i <= maxV; i++) {
                adjMatrix[i] = i < vCount ? Arrays.copyOf(adjMatrix[i], maxV + 1) : new boolean[maxV + 1];
            }
            vCount = maxV + 1;
        }
        if (!adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = true;
            adjMatrix[v2][v1] = true;
            eCount++;
        }
    }

    @Override
    public void removeAdge(int v1, int v2) {
        if (adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = false;
            adjMatrix[v2][v1] = false;
            eCount--;
        }
    }

    @Override
    public Iterable<Integer> adjacencies(int v) {
        return new Iterable<Integer>() {
            Integer nextAdj = null;

            @Override
            public Iterator<Integer> iterator() {
                for (int i = 0; i < vCount; i++) {
                    if (adjMatrix[v][i]) {
                        nextAdj = i;
                        break;
                    }
                }

                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public Integer next() {
                        Integer result = nextAdj;
                        nextAdj = null;
                        for (int i = result + 1; i < vCount; i++) {
                            if (adjMatrix[v][i]) {
                                nextAdj = i;
                                break;
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    private void findAllWays(int initVertex, int finalVertex, List<Integer> way, List<List<Integer>> ways) {
        List<Integer> newWay = new ArrayList<>(way);
        newWay.add(initVertex);
        if(initVertex == finalVertex) {
            ways.add(newWay);
            return;
        }

        for(Integer v : adjacencies(initVertex)) {
            if(newWay.size() < 2 || newWay.indexOf(v) == -1) {
                findAllWays(v, finalVertex, newWay, ways);
            }
        }
    }

    public void findCommonVertex(int initVertex, int finalVertex) {
        List<List<Integer>> ways = new ArrayList<>();
        findAllWays(initVertex, finalVertex, new ArrayList<>(), ways);
        if(ways.size() == 0) {
            System.out.printf("Добраться из %d в %d невозможно\n", initVertex, finalVertex);
            return;
        }

        List<Integer> ans = ways.get(0);
        for(int i = 1; i < ways.size(); i++) {
            for(int j = 0; j < ans.size(); j++) {
                if(ways.get(i).indexOf(ans.get(j)) == -1) ans.remove(j);
            }
        }

        for(int i = 0; i < ans.size(); i++) {
            System.out.printf("%d", ans.get(i));
            if(i != ans.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
