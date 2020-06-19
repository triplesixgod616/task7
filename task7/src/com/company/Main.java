package com.company;

public class Main {

    public static void main(String[] args) {
        AdjMatrixGraph graph = new AdjMatrixGraph(6);
        graph.addAdge(0, 2);
        graph.addAdge(1, 3);
        graph.addAdge(2, 3);
        graph.addAdge(2, 4);
        graph.addAdge(3, 4);
        graph.addAdge(3, 5);
        graph.findCommonVertex(0, 5);
        AdjMatrixGraph graph1 = new AdjMatrixGraph(5);
        graph1.addAdge(0, 1);
        graph1.addAdge(0, 2);
        graph1.addAdge(1, 2);
        graph1.addAdge(1, 3);
        graph1.addAdge(2, 4);
        graph1.addAdge(2, 3);
        graph1.addAdge(3, 4);
        graph1.findCommonVertex(0, 4);
    }
}
