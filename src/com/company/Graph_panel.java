package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.KeyListener;


public class Graph_panel extends JPanel {
    private Ellipse2D first;
    private Ellipse2D second;
    private ArrayList circle;
    private ArrayList line;
    private Ellipse2D current;
    public boolean mouseListenerIsActive = false;



    @Override
    public Dimension getPreferredSize() {
        return new Dimension( getParent().getWidth()/2, getParent().getHeight());
    };
    public Graph_panel(){
        setBackground(Color.blue);
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
        for( int i = 0; i < circle.size();i++) {
            g2.setColor(new Color((100 + 20) % 255, (100 + 20) % 255, (100 + 20) % 255));
            g2.fill((Ellipse2D) circle.get(i));
            g2.drawString(String.valueOf(i), (int) ((Ellipse2D) circle.get(i)).getX(), (int) ((Ellipse2D) circle.get(i)).getY());
        }
        for(int i = 0; i<line.size() ;i++){
            g2.fill((Line2D)line.get(i));
            g2.draw((Line2D)line.get(i));
        }
    }

    public void add(Point2D p){
        current = new Ellipse2D.Double(p.getX() -10 , p.getY() - 10,30,30);

        circle.add(current);

        repaint();
    }


    public Ellipse2D find(Point2D p)
    {
        for(int i = 0; i < circle.size(); i++)
        {
            Ellipse2D e = (Ellipse2D) circle.get(i);
            if(e.contains(p)) return e;
        }
        return null;
    }


    public void remove(Ellipse2D e )
    {
        if(e == null) return;
        if(e == current) current = null;
        circle.remove(e);
        repaint();
    }


    private class MyMouse extends MouseAdapter{
        public void stopMouseListener(){
            mouseListenerIsActive = false;
        }
        public void mousePressed(MouseEvent event )
        {
//            mouseListenerIsActive = true;
            if(mouseListenerIsActive) {

                current = find(event.getPoint());
                if (current == null) {
                    add(event.getPoint());
                } else {

                    if (first == null) {
                        first = current;
                    } else {
                        second = current;
                    }
                }
                if (first != null && second != null) {
                    System.out.println("fdfdfdf");
                    Line2D my_line = new Line2D.Double(first.getX() + 10, first.getY() + 10, second.getX() + 10, second.getY() + 10);
                    line.add(my_line);
                    first = null;
                    second = null;
                    repaint();
                }
            }

        }

        public void mouseClicked(MouseEvent event)
        {
            if(event.getClickCount() >= 2)
            {
                current = find(event.getPoint());
                if (current != null)
                {
                    remove(current);
                }
            }
        }
    }



    private class MyMove implements MouseMotionListener
    {
        @Override
        public void mouseMoved(MouseEvent event) {
            if(find(event.getPoint()) == null){
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
