package dijkstra;

import netscape.javascript.JSObject;

import java.util.*;
import java.util.Map.Entry;

public class Dijkstra {

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        ArrayList<StepResults> step_res = new ArrayList<>();
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);
        int counter = 0;
        ArrayList<Hashtable<String, String>> json = new ArrayList<>();
        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            for (Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
//                json.add(step_res.put("current node", ))
                step_res.add(new StepResults());
                step_res.get(counter).currentNode = currentNode;
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeigh = adjacencyPair.getValue();
                step_res.get(counter).lookingNode = adjacentNode;
                step_res.get(counter).shortest_path = edgeWeigh;
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                    step_res.get(counter).lookingNode = adjacentNode;
                    step_res.get(counter).shortest_path = edgeWeigh;
                }
                counter++;
            }
            settledNodes.add(currentNode);
        }
        for (StepResults i : step_res){
//            System.out.println("current node : " + i.currentNode.getName() + " looking node: " + i.lookingNode.getName() + " shortest path: " + i.shortest_path);
            i.result = "Текущая вершина : " + i.currentNode.getName() + "\nСледующая вершина: " + i.lookingNode.getName() + "\nСтоимость пути: " + i.shortest_path;
        }
        graph.step_res = step_res;
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}