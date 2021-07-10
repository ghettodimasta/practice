package com.company;

import dijkstra.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.*;


public class Graph_panel extends JPanel {
    private Node first;
    private Node second;
    private ArrayList circle;
    private ArrayList line;
    private ArrayList<Node> vertex;
    private Node current;
    public boolean vertexListenerIsActive = false;
    public boolean edgeListenerIsActive = false;
    public boolean deleteListenerIsActive = false;



    @Override
    public Dimension getPreferredSize() {
        return new Dimension( getParent().getWidth()/2, getParent().getHeight());
    };
    public Graph_panel(){
        setBackground(Color.blue);
        vertex = new ArrayList<Node>();
        circle = new ArrayList();
        line = new ArrayList();
        current = null;
        addMouseListener(new MyMouse());
        addMouseMotionListener(new MyMove());
        first = null;
        second = null;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for( int i = 0; i < vertex.size();i++) {
            Node node = vertex.get(i);
            g2.setColor(new Color(172, 219, 235));
            g2.fill(node.picture);
            g2.drawString(String.valueOf(node.getName()), Math.round(node.x), Math.round(node.y));
        }
        for(int i = 0; i < line.size() ;i++){
            g2.fill((Line2D)line.get(i));
            g2.draw((Line2D)line.get(i));
        }
    }

    public void add_node(Point2D p, String name){

        var node = new Node(name);
        node.x = p.getX() - 10;
        node.y = p.getY() - 10;
        node.picture = new Ellipse2D.Double(p.getX() -10 , p.getY() - 10,30,30);
        vertex.add(node);

        repaint();
    }


    public Node find(Point2D p) {
        for(int i = 0; i < vertex.size(); i++) {
            Node node = vertex.get(i);
            if(node.picture.contains(p)) {
                return node;
            }
        }
        return null;
    }

    public String get_new_name() {
        var alphabet = new ArrayList<String>();
        String[] alphabet2 = "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z".split(", ");
        for (int i = 0; i < 26; i++) {
            alphabet.add(alphabet2[i]);
        }
        if (vertex.size() == 0) {
            return "A";
        }
        String last_name = vertex.get(vertex.size() - 1).getName();
        return alphabet.get(alphabet.indexOf(last_name) + 1);
    }


    public void remove_node(Node node) {
        if(node == null) return;
        vertex.remove(node);
        current = null;
        first = null;
        second = null;
        repaint();
    }


    private class MyMouse extends MouseAdapter{
        public void stopMouseListener(){
            vertexListenerIsActive = false;
        }
        public void mousePressed(MouseEvent event )
        {
            if(deleteListenerIsActive) {
                var point = event.getPoint();
                current = find(point);
                remove_node(current);
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
                    line.add(my_line);
                    first = null;
                    second = null;
                    repaint();
                }
            }

        }

    }

    private class MyMove implements MouseMotionListener {
        @Override
        public void mouseMoved(MouseEvent event) {
            if(find(event.getPoint()) == null || !edgeListenerIsActive){
                setCursor(Cursor.getDefaultCursor());
            }
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        }

        @Override
        public void mouseDragged(MouseEvent event) {

        }
    }

    public class MyKey extends KeyAdapter {

    }



}
