package com.company;

import dijkstra.Node;
import dijkstra.StepResults;

import java.util.ArrayList;

public class Statement {
    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Edge> edges = new ArrayList<>();
    public StepResults step;

    Statement(ArrayList<Node> node_list, ArrayList<Edge> edge_list, StepResults step) {
        this.nodes = node_list;
        this.edges = edge_list;
        this.step = step;
    }
}
