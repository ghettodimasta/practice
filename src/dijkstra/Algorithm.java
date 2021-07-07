package dijkstra;

import java.io.*;
import java.util.ArrayList;


public class Algorithm {

    private Graph graph = new Graph();
    private ArrayList<Node> nodes = new ArrayList<Node>();

    Algorithm() throws Exception {
        this.run_alg();
    }

    public void run_alg() throws Exception {
        read_graph_from_file();

        graph = Dijkstra.calculateShortestPathFromSource(this.graph, nodes.get(0));
        for (Node node : graph.getNodes()) {
            System.out.print(node.getName() + ' ');
            System.out.println(node.getDistance());
        }

    }

    private void read_graph_from_file() throws Exception {
        String data = Read("./src/graph.txt").strip();
        ArrayList<Character> letters = new ArrayList<Character>();
        int counter = 0;
        while (counter < data.length()) {
            char letter = data.charAt(counter);
            if (Character.isAlphabetic(letter) && !letters.contains(letter)) {
                letters.add(data.charAt(counter));
            }
            counter += 1;
        }

        for (Character letter : letters) {
            nodes.add(new Node(letter.toString()));
        }

        for (String line : data.split("\n")) {
            String[] edge = line.split(" ");
            String name1 = edge[0];
            Node node1 = null;
            for (Node el : nodes) {
                if (el.getName().equals(name1)) {
                    node1 = el;
                }
            }
            if (node1 == null) {
                throw new Exception("[ERROR] Can't read file. Check rules u v weight");
            }
            String name2 = edge[1];
            Node node2 = null;

            for (Node el : nodes) {
                if (el.getName().equals(name2)) {
                    node2 = el;
                }
            }

            if (node2 == null) {
                throw new Exception("[ERROR] Can't read file. Check rules u v weight");
            }

            node1.addDestination(node2, Integer.parseInt(edge[2]));

        }

        for (Node node : nodes) {
            this.graph.addNode(node);
        }

    }


    public String Read(String filename){
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(filename)){
            int i;
            while ((i=reader.read()) != -1)
                content.append((char) i);
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}