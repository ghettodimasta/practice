package com.company;

import dijkstra.Node;

import java.util.ArrayList;

public class Statement {
    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Edge> edges = new ArrayList<>();

    Statement(ArrayList<Node> node_list, ArrayList<Edge> edge_list) {
        this.nodes = node_list;
        this.edges = edge_list;
    }
}
