package com.company;

import dijkstra.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

class Edge {
    public Line2D picture;
    public Node node1;
    public Node node2;
    public Integer weight;
    public Integer status = 0;

    Edge (Node node1, Node node2, Line2D line, Integer weight) {
        this.picture = line;
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }
}


public class Graph_panel extends JPanel {
    private Node first;
    private Node second;
    //    private ArrayList<Line2D> lines;
    public ArrayList<Edge> edges;
    public ArrayList<Node> vertex;
    private Node current;
    public boolean addVertexListenerIsActive = false;
    public boolean addEdgeListenerIsActive = false;
    public boolean deleteEdgeListenerIsActive = false;
    public boolean deleteVertexListenerIsActive = false;


    public void clear() {
        vertex = new ArrayList<Node>();
        edges = new ArrayList<>();
        current = null;
        first = null;
        second = null;
        repaint();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth() / 2, getParent().getHeight());
    }

    ;

    public Graph_panel() {
        setBackground(Color.blue);
        vertex = new ArrayList<Node>();
//        lines = new ArrayList();
        edges = new ArrayList<>();
        current = null;
        addMouseListener(new MyMouse());
        addMouseMotionListener(new MyMove());
        first = null;
        second = null;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Edge edge : edges) {
            if (edge.status == 0) {
                g2.setColor(Color.BLACK);
            }
            else {
                g2.setColor(Color.GREEN);
            }
            g2.setStroke(new BasicStroke(4));
            g2.fill(edge.picture);
            g2.draw(edge.picture);
            g2.setColor(Color.RED);
            g2.drawString(edge.weight.toString(), (int) (edge.node1.x + edge.node2.x) / 2,
                    (int) (edge.node1.y + edge.node2.y) / 2);
        }

        for (Node node : vertex) {
            if (node.status == 0) {
                g2.setColor(new Color(172, 219, 235));
            }
            if (node.status == 1) {
                g2.setColor(new Color(90, 190, 65));
            }
            if (node.status == 2) {
                g2.setColor(new Color(240, 240, 60));
            }
            if (node.status == 3) {
                g2.setColor(new Color(140, 140, 140));
            }
            g2.fill(node.picture);
            g2.setColor(new Color(1, 1, 1));
            g2.drawString(String.valueOf(node.getName()), Math.round(node.x), Math.round(node.y));
        }
    }

    public void add_node(Point2D p, String name) {

        var node = new Node(name);
        node.x = p.getX() - 10;
        node.y = p.getY() - 10;
        node.picture = new Ellipse2D.Double(p.getX() - 10, p.getY() - 10, 30, 30);
        vertex.add(node);

        repaint();
    }


    public Node find_circle(Point2D p) {
        for (Node node : vertex) {
            if (node.picture.contains(p)) {
                return node;
            }
        }
        return null;
    }

    public void set_nodes(ArrayList<Node> nodes) {
        double theta = 2 * Math.PI / nodes.size();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            node.x = 250 + 150 * Math.cos(theta*i);
            node.y = 250 + 150 * Math.sin(theta*i);
            node.picture = new Ellipse2D.Double(node.x, node.y,30,30);
            this.vertex.add(node);

        }
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            var connections = node.getAdjacentNodes();
            for (Node value : nodes) {
                var weight = connections.get(value);
                if (weight != null) {
                    Line2D my_line = new Line2D.Double(node.x + 15, node.y + 15, value.x + 15, value.y + 15);
                    this.edges.add(new Edge(node, value, my_line, weight));
                }
            }
        }


        System.out.println(this.vertex.toString());
    }

    public Edge find_line(Point2D p) {
        for (Edge edge : edges) {
            Line2D line = edge.picture;
            System.out.println("not found");
            double k = (line.getY2() - line.getY1()) / (line.getX2() - line.getX1());
            double b = line.getY1() - line.getX1() * k;
            double error_amount = 3;
            System.out.println(p.getX());
            System.out.println(p.getY());
            if ((p.getY() - (p.getX() * k + b) < error_amount) && ((p.getX() * k + b - p.getY()) < error_amount)) {
                System.out.println("found");
                return edge;
            }
        }

        return null;
    }

    public String get_new_name() {
        String[] alphabet2 = "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z".split(", ");
        var alphabet = new ArrayList<String>(Arrays.asList(alphabet2).subList(0, 26));
        if (vertex.size() == 0) {
            return "A";
        }
        String last_name = vertex.get(vertex.size() - 1).getName();
        return alphabet.get(alphabet.indexOf(last_name) + 1);
    }


    public void remove_node(Node node) {
        if (node == null) return;
        vertex.remove(node);
        current = null;
        first = null;
        second = null;
        repaint();
    }

    public void remove_line(Edge edge) {
        if (edge == null) return;
        edges.remove(edge);

        current = null;
        first = null;
        second = null;
        repaint();
    }


    private class MyMouse extends MouseAdapter {
        public void stopMouseListener() {
            addVertexListenerIsActive = false;
        }


        public void mousePressed(MouseEvent event) {
            /*
            if(deleteListenerIsActive) {
                var point = event.getPoint();
                current = find(point);
                remove_node(current);
                var line = find_line(point);
                remove_line(line);
            }
            if(vertexListenerIsActive || edgeListenerIsActive) {

                current = find(event.getPoint());
                if (current == null && vertexListenerIsActive) {
                    add_node(event.getPoint(), get_new_name());
                } else {

                    if (first == null) {
                        first = current;
                    } else {
                        second = current;
                    }
                }
                if (first != null && second != null && edgeListenerIsActive) {
                    Line2D my_line = new Line2D.Double(first.x + 15, first.y + 15, second.x + 15, second.y + 15);
//                    lines.add(my_line);
                    Node node1 = vertex.get(vertex.indexOf(first));
                    Node node2 = vertex.get(vertex.indexOf(second));
                    if (node1 != node2) {
                        edges.add(new Edge(node1, node2, my_line, (int) Double.parseDouble(default_weight)));
                        node1.addDestination(node2, (int) Double.parseDouble(default_weight));
                        first = null;
                        second = null;
                        repaint();
                    }
                }
            }
        }

       */
            current = find_circle(event.getPoint());            //for all if

            if (addVertexListenerIsActive) {
                if (current == null) {
                    add_node(event.getPoint(), get_new_name());

                }
            }

            if (addEdgeListenerIsActive) {
                if (first == null) {
                    first = current;
                } else {
                    second = current;
                }
                if (first != null && second != null) {
                    Line2D my_line = new Line2D.Double(first.x + 15, first.y + 15, second.x + 15, second.y + 15);
                    //lines.add(my_line);

                    Node node1 = vertex.get(vertex.indexOf(first));
                    Node node2 = vertex.get(vertex.indexOf(second));
                    if (node1 != node2) {
                        System.out.println("add_node");
                        int weight  = Integer.parseInt(get_weight());
                        edges.add(new Edge(node1, node2, my_line, weight));
                        node1.addDestination(node2, weight);
                        node2.addDestination(node1, weight);
                        first = null;
                        second = null;
                        repaint();
                    }
                }
            }

            if(deleteVertexListenerIsActive){
                System.out.println("tUT");
                DeliteVertex();

            }

            if(deleteEdgeListenerIsActive){
                Edge auxiliary = find_line(event.getPoint());
                remove_line(auxiliary);

            }
        }


        private void DeliteVertex(){
            //if use a usually circle, the elements will be scipped
            int fist_size = edges.size();
            for(int i = 0; i < fist_size; i++) {
                for (Edge edge : edges) {
                    if (edge.node1.getName().equals(current.getName()) || edge.node2.getName().equals(current.getName())) {
                        edges.remove(edge);
                        break;
                    }
                }
            }
            //I dont understand how i can do this normaly
            vertex.remove(current);
            current = null;
            repaint();
        }


        private String get_weight(){
            int weight = 0;
            String answer = "0";
            String message = "Input the weight";
            int optionPane = JOptionPane.QUESTION_MESSAGE;
            for( int isNumber = 0; isNumber < 1;  ) {
                answer = JOptionPane.showInputDialog(
                        null, message,
                        "THe weight of edge",
                        optionPane);

                try {
                    weight = Integer.parseInt(answer);
                    if( weight != 0 ) {
                        isNumber = 1;
                    }else {
                        message = "The weight must be more zero";
                        optionPane = JOptionPane.WARNING_MESSAGE;
                    }
                }
                catch (NumberFormatException err)
                {
                    message = "The weight must be integer";
                    optionPane = JOptionPane.ERROR_MESSAGE;
                }
            }
            return answer;
        }



    }

    public void write_to_the_file(String filename) throws FileNotFoundException {
        if(filename == null) filename = "the_faste_save";
        File file = new File(filename);
        PrintWriter pw = new PrintWriter(file);
        for(Edge edge : edges){
            String str = new String();
            str += edge.node1.getName();
            str += " ";
            str += edge.node2.getName();
            str += " ";
            str += String.valueOf(edge.weight);
            pw.println(str);
        }
        pw.close();
    }

    private class MyMove implements MouseMotionListener {
        @Override
        public void mouseMoved(MouseEvent event) {
            if (find_circle(event.getPoint()) == null || !addEdgeListenerIsActive) {
                setCursor(Cursor.getDefaultCursor());
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        }

        @Override
        public void mouseDragged(MouseEvent event) {

        }
    }


}


