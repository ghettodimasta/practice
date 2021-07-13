package com.company;

import dijkstra.Algorithm;
import dijkstra.Node;
import dijkstra.StepResults;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class GUI extends JFrame {
    private JPanel main_panel;
    private Graph_panel graph_panel;
    private JTextArea text_area;
    private int window_width;
    private int window_height;
    private String filename = null;
    private String result;
    private Algorithm alg = new Algorithm();
    private ArrayList<String> step_res;

    public GUI() {
        super("Алгоритм Дейкстры");
        set_ui();
        this.pack();
        this.setVisible(true);
    }

    private void set_ui() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.window_width = (int)screenSize.getWidth();
        this.window_height = (int)screenSize.getHeight();

        setMinimumSize(new Dimension(this.window_width/2+500, this.window_height/2+250));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init_main_panel();
    }

    private void init_main_panel() {
        main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.X_AXIS));
        main_panel.add(init_graph_panel());
        main_panel.add(init_text_area());
        add(main_panel);
        add(init_tool_bar(), BorderLayout.WEST);
        setJMenuBar(init_menu());
    }


    private JPanel init_graph_panel() {
        graph_panel = new Graph_panel();
        graph_panel.setBackground(Color.WHITE);
        graph_panel.setBorder(new LineBorder(new Color(230, 230, 230)));
        return graph_panel;
    }


    private JTextArea init_text_area(){
        text_area = new JTextArea("Пояснение к работе Алгоритма");
        text_area.setPreferredSize(new Dimension(50,50));
        text_area.setEditable(false);
        return text_area;
    }

    private JMenu init_file_menu() {
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem read = new JMenuItem("Открыть файл");
        JMenuItem save = new JMenuItem("Сохранить");
        JMenuItem save_as = new JMenuItem("Сохранить как");
        JMenuItem exit = new JMenuItem("Выход");

        exit.addActionListener( actionEvent -> {
            System.exit(0);
        });

        read.addActionListener( actionEvent -> {
            JFileChooser j = new JFileChooser();
            j.showOpenDialog(null);
            filename = j.getSelectedFile().toString();
            System.out.println(filename);
            graph_panel.clear();
            try {
                alg.read_graph_from_file(filename);
                graph_panel.set_nodes(alg.nodes);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        fileMenu.add(read);
        fileMenu.add(save);
        fileMenu.add(save_as);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenuBar init_menu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(init_file_menu());
        menuBar.add(init_info_menu());
        return menuBar;
    }

    private JMenu init_info_menu() {
        JMenu info = new JMenu("Информация");
        JMenuItem how_to_use = new JMenuItem("Руководство пользователя");
        info.add(how_to_use);

        return info;
    }
    private JToolBar init_tool_bar() {
        JToolBar tool_bar = new JToolBar(1);
        JButton create_graph = new JButton("Создать граф");
        create_graph.setFocusPainted(false);
        JButton add_edge = new JButton("Добавить ребро");
//        JTextArea weight = new JTextArea("Введите вес");
        JButton add_vertex = new JButton("Добавить вершину");
        JButton del_edge = new JButton("Удалить ребро");
        JButton del_vertex = new JButton("Удалить вершину");
        JButton stop_add = new JButton("Завершить создание");
        JButton save_graph = new JButton("Сохранить граф");
        JButton run_algorithm = new JButton("Запуск алгоритма");
        JButton next_step = new JButton("Следующий шаг");
        JButton prev_step = new JButton("Предыдущий шаг");
        JButton stop_alg = new JButton("Остановить");
//        weight.setVisible(false);

        //---------------------------------------------------------

        tool_bar.setFloatable(false);
        tool_bar.add(create_graph);
        tool_bar.add(save_graph);
        tool_bar.add(run_algorithm);
        tool_bar.addSeparator(new Dimension(50, 50));
        tool_bar.add(next_step);
        tool_bar.add(prev_step);
        tool_bar.add(stop_alg);
        tool_bar.addSeparator(new Dimension(50, 50));
        tool_bar.add(add_edge);
        tool_bar.add(add_vertex);
        tool_bar.add(del_edge);
        tool_bar.add(del_vertex);
        tool_bar.add(stop_add);
        tool_bar.addSeparator(new Dimension(50, 50));
//        tool_bar.add(weight);

        next_step.setVisible(false);
        prev_step.setVisible(false);
        stop_alg.setVisible(false);
        del_edge.setVisible(false);
        del_vertex.setVisible(false);
        add_edge.setVisible(false);
        stop_add.setVisible(false);
        add_vertex.setVisible(false);

        run_algorithm.addActionListener(e -> {
            next_step.setVisible(true);
            prev_step.setVisible(true);
            stop_alg.setVisible(true);


            ArrayList<Node> save_nodes = new ArrayList<>();

            for (Node alg_node : alg.nodes) {
                save_nodes.add(alg_node);
            }

            alg.clear();

            for (Node save_node : save_nodes) {
                save_node.clear();
                alg.graph.addNode(save_node);
            }

            alg.read_graph_from_nodes(graph_panel.vertex);
            Node start = get_starte();
            String text = alg.run_alg(start);
            step_res = new ArrayList<>();
            for (StepResults i : alg.getResult()){
                step_res.add(i.getResult());
            }
            AtomicInteger i = new AtomicInteger();
            text_area.setText(this.step_res.get(i.get()));
            next_step.addActionListener(e1 -> {
                if (i.intValue() != this.step_res.size()-1) {
                    System.out.println("I+: " + i.toString());
                    i.incrementAndGet();
                    text_area.setText(this.step_res.get(i.get()));
                }
                else {
                    text_area.setText(this.step_res.get(this.step_res.size()-1) +"\n" + text);
                }
            });
            prev_step.addActionListener(e2 -> {
                System.out.println("I- " + i.toString());
                if (i.intValue() == 0){
                    text_area.setText(this.step_res.get(0) + "\nЭто первый шаг!");
                }
                else {
                    i.decrementAndGet();
                    text_area.setText(this.step_res.get(i.get()));
                }
            });
        });


        create_graph.addActionListener(e -> {
            del_edge.setVisible(true);
            del_vertex.setVisible(true);
            add_vertex.setVisible(true);
            add_edge.setVisible(true);
            stop_add.setVisible(true);
//            weight.setVisible(true);

        });

        add_vertex.addActionListener(e -> {
            make_False_Default();
            graph_panel.addVertexListenerIsActive = true;
        });

        add_edge.addActionListener(e -> {
            make_False_Default();
            graph_panel.addEdgeListenerIsActive = true;
        });

        del_edge.addActionListener(e -> {
            make_False_Default();
            graph_panel.deleteEdgeListenerIsActive = true;

        });

        del_vertex.addActionListener(e -> {
            make_False_Default();
            graph_panel.deleteVertexListenerIsActive = true;
        });

        stop_add.addActionListener(e -> {
            del_edge.setVisible(false);
            del_vertex.setVisible(false);
            add_edge.setVisible(false);
            stop_add.setVisible(false);
            add_vertex.setVisible(false);
//            weight.setVisible(false);
            make_False_Default();
        });

        stop_alg.addActionListener(e -> {
            next_step.setVisible(false);
            prev_step.setVisible(false);
            stop_alg.setVisible(false);
            text_area.setText("Пояснение к работе алгоритма");
        });

        return tool_bar;
    }
    private void  make_False_Default(){
        graph_panel.deleteVertexListenerIsActive = false;
        graph_panel.deleteEdgeListenerIsActive = false;
        graph_panel.addVertexListenerIsActive = false;
        graph_panel.addEdgeListenerIsActive = false;
    }

    private Node get_starte(){
        int optionPane = JOptionPane.QUESTION_MESSAGE;
        String answer = JOptionPane.showInputDialog(
                null, "Set the start",
                "The setting of start",
                optionPane);
        for(Node node : alg.nodes){
            if(node.getName().equals(answer.strip().toUpperCase(Locale.ROOT))){
                return node;
            }
        }

        return null;
    }

}
