package com.company;

import dijkstra.Algorithm;
import dijkstra.Node;
import dijkstra.StepResults;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Line2D;
import java.security.cert.TrustAnchor;
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
//    private ArrayList<String> step_res;
    private ArrayList<Statement> statements;
    private ArrayList<Statement> to_del = new ArrayList<>();

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

        save.addActionListener(actionEvent -> {
            try {
                graph_panel.write_to_the_file(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        save_as.addActionListener(actionEvent -> {
            JFileChooser j = new JFileChooser();
            j.showSaveDialog(null);
            filename = j.getSelectedFile().toString();
            System.out.println(filename);
            try {
                graph_panel.write_to_the_file(filename);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
        JButton add_vertex = new JButton("Добавить вершину");
        JButton del_edge = new JButton("Удалить ребро");
        JButton del_vertex = new JButton("Удалить вершину");
        JButton stop_add = new JButton("Завершить создание");
        JButton run_algorithm = new JButton("Запуск алгоритма");
        JButton next_step = new JButton("Следующий шаг");
        JButton prev_step = new JButton("Предыдущий шаг");
        JButton stop_alg = new JButton("Остановить");

        //---------------------------------------------------------

        tool_bar.setFloatable(false);
        tool_bar.add(create_graph);
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

            ArrayList<String> step_res = new ArrayList<String>();
            var results = new ArrayList<StepResults>();

//            for (StepResults i : alg.getResult()) {
//                step_res.add(i.getResult());
//            }

            for (StepResults i : alg.getResult()) {
                results.add(i);
            }

            this.statements = new ArrayList<>();
            set_statements(results);

            for (Statement i : this.statements) {
                step_res.add(i.step.getResult());
            }

            AtomicInteger i = new AtomicInteger();
//            i.set(0);
            set_colors(0);
            text_area.setText(step_res.get(i.get()));

            next_step.addActionListener(e1 -> {

                if (i.intValue() != step_res.size()-1) {
                    System.out.println("I+: " + i.toString());
                    i.incrementAndGet();
                    set_colors(i.get());
                    text_area.setText(step_res.get(i.get()));
                }
                else {
                    text_area.setText(text);
                }
            });
            prev_step.addActionListener(e2 -> {
                System.out.println("I- " + i.toString());
                if (i.intValue() == 0){
                    text_area.setText(step_res.get(0) + "\nЭто первый шаг!");
                }
                else {
                    i.decrementAndGet();
                    text_area.setText(step_res.get(i.get()));
                    set_colors(i.get());
                }
            });

            stop_alg.addActionListener(e3 -> {
                i.set(0);
                next_step.setVisible(false);
                prev_step.setVisible(false);
                stop_alg.setVisible(false);
                text_area.setText("Пояснение к работе алгоритма");
                delete_colors();
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

    private void color_graph(StepResults step_res) {
//        status 0 - unwatched; 1 - current; 2 - looking; 3 - watched
        var cur_node = graph_panel.vertex.get(graph_panel.vertex.indexOf(step_res.currentNode));
        cur_node.status = 1;
        var looking_node = graph_panel.vertex.get(graph_panel.vertex.indexOf(step_res.lookingNode));
        looking_node.status = 2;
        for (Edge ed : graph_panel.edges) {
            if (ed == null) {
                continue;
            }
            if (ed.node1.getName().equals(cur_node.getName()) &&
                    ed.node2.getName().equals(looking_node.getName())) {
                ed.status = 1;
                break;
            }
        }

        repaint();
    }

    private void set_colors(Integer index) {
        var now_statement = this.statements.get(index);
        graph_panel.vertex = now_statement.nodes;
        graph_panel.edges = now_statement.edges;
        repaint();
    }

    private void delete_colors() {
        for (Node node: graph_panel.vertex) {
            node.status = 0;
        }
        for (Edge ed: graph_panel.edges) {
            ed.status = 0;
        }
        repaint();
    }

    private void all_grey() {
        for (Node node: graph_panel.vertex) {
            node.status = 3;
        }
        for (Edge ed: graph_panel.edges) {
            ed.status = 0;
        }
        repaint();
    }

    private void set_statements(ArrayList<StepResults> step_results) {
        // status 0 - unwatched; 1 - current; 2 - looking; 3 - watched
        ArrayList<Node> watched = new ArrayList<>();
        Node cur = null;
        boolean skip = false;
        for (StepResults step : step_results) {
            var cur_node = step.currentNode;
            var looking_node = step.lookingNode;
            for (Node nd : watched) {
                if (looking_node.getName().equals(nd.getName())) {
                    skip = true;
                }
            }
            if (skip) {
                continue;
            }
            String cur_name = cur_node.getName();
            String look_name = looking_node.getName();

            if (cur == null) {
                cur = cur_node;
            }

            if (!cur.getName().equals(cur_node.getName())) {
                watched.add(cur);
                cur = cur_node;
            }

            var nodes = new ArrayList<Node>();
            var edges = new ArrayList<Edge>();

            for (Edge ed : graph_panel.edges) {
                if (ed == null) {
                    continue;
                }
                Edge edge = new Edge(ed.node1, ed.node2, ed.picture, ed.weight);
                String name1 = ed.node1.getName();
                String name2 = ed.node2.getName();
                if ((cur_name.equals(name1) && look_name.equals(name2)) ||
                        (look_name.equals(name1) && cur_name.equals(name2))) {
                    edge.status = 1;
//                    break;
                }
                System.out.print(edge.node1.getName() + edge.node2.getName());
                System.out.print(' ');
                System.out.println(edge.status);
                edges.add(edge);
            }

            for (Node node : graph_panel.vertex) {
                if (node == null) {
                    continue;
                }

                Node vertex = new Node(node.getName());
                vertex.x = node.x;
                vertex.y = node.y;
                vertex.status = 0;
                vertex.picture = node.picture;
                vertex.setDistance(node.getDistance());
//                TODO adj nodes + shortest_path
                if (step.currentNode.getName().equals(vertex.getName())) {
                    vertex.status = 1;
                }

                for (Node wan : watched) {
                    if (wan.getName().equals(vertex.getName())) {
                        vertex.status = 3;
                        break;
                    }
                }

                if (step.lookingNode.getName().equals(vertex.getName())) {
                    vertex.status = 2;
                    var vname = vertex.getName();
                    int count = 0;
                    System.out.println("Out count name = " + vname);
                    for (Edge line: edges) {
                        System.out.println("Out count " + line.node1.getName() + line.node2.getName());
                        if (line.node1.getName().equals(vname)) {
                            count += 1;
                            System.out.println(count);
                        }
                    }
                    if (count == 0) {
                        watched.add(vertex);
                    }
                }
                nodes.add(vertex);
                System.out.print(vertex.getName());
                System.out.print(' ');
                System.out.println(vertex.status);
            }

            for (Edge edge : edges) {
                String name1 = edge.node1.getName();
                String name2 = edge.node2.getName();

                for (Node node : nodes) {
                    if (node.getName().equals(name1)) {
                        for (Node vertex : nodes) {
                            if (vertex.getName().equals(name2)) {
                                node.addDestination(vertex, edge.weight);
                                vertex.addDestination(node, edge.weight);
                                System.out.println("Linked " + name1 + " " + name2 + " " + edge.weight);
                                break;
                            }
                        }
                    }
                }

            }

            System.out.println("------");
            this.statements.add(new Statement(nodes, edges, step));
        }
    }

}
