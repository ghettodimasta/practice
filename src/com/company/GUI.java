package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;


public class GUI extends JFrame {
    private JPanel main_panel;
    private JPanel graph_panel;
    private JLabel status_text;
    private JTextArea text_area;
    private int window_width;
    private int window_height;

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

        setMinimumSize(new Dimension(this.window_width/2, this.window_height/2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init_main_panel();
    }

    private void init_main_panel() {
        main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.X_AXIS));
        main_panel.add(init_graph_panel());
        add(main_panel);
        add(init_tool_bar(), BorderLayout.WEST);
        add(init_status_panel(), BorderLayout.SOUTH);
        setJMenuBar(init_menu());
    }


    private JPanel init_graph_panel() {
        graph_panel = new Graph_panel();
        graph_panel.setBackground(Color.WHITE);
        graph_panel.setBorder(new LineBorder(new Color(230, 230, 230)));
        return graph_panel;
    }


    private JPanel init_status_panel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(getWidth(), 42));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        status_text = new JLabel("   ...");
        status_text.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(status_text);

        return statusPanel;
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
        JPanel button_panel = new JPanel(new GridLayout(20, 2));
        JButton create_graph = new JButton("Создать граф");
        JButton add_edge = new JButton("Добавить ребро");
        JButton add_vertex = new JButton("Добавить вершину");
        JButton del_edge = new JButton("Удалить ребро");
        JButton del_vertex = new JButton("Удалить вершину");
        JButton stop_add = new JButton("Завершить создание");
        JButton save_graph = new JButton("Сохранить граф");
        JButton run_algorithm = new JButton("Запустить алгоритм");
        JButton next_step = new JButton("Следующий шаг");
        JButton prev_step = new JButton("Предыдущий шаг");
        JButton stop_alg = new JButton("Остановить");
        this.text_area = new JTextArea("Пояснения работы\nалгоритма");
        JButton bord = new JButton("");
        bord.setVisible(false);
        JButton bord2 = new JButton("");
        bord2.setVisible(false);
        button_panel.add(create_graph);
        button_panel.add(save_graph);
        button_panel.add(run_algorithm);
        button_panel.add(bord);
        button_panel.add(next_step);
        button_panel.add(prev_step);
        button_panel.add(stop_alg);
        button_panel.add(bord2);
        button_panel.add(add_edge);
        button_panel.add(add_vertex);
        button_panel.add(del_edge);
        button_panel.add(del_vertex);
        button_panel.add(stop_add);
        text_area.setEditable(false);
        text_area.setPreferredSize(new Dimension(button_panel.getWidth(), 200));
        tool_bar.setFloatable(false);

        next_step.setVisible(false);
        prev_step.setVisible(false);
        stop_alg.setVisible(false);
        del_edge.setVisible(false);
        del_vertex.setVisible(false);
        add_edge.setVisible(false);
        stop_add.setVisible(false);
        add_vertex.setVisible(false);
        tool_bar.add(button_panel);
        tool_bar.add(text_area);

        run_algorithm.addActionListener(e -> {
            next_step.setVisible(true);
            prev_step.setVisible(true);
            stop_alg.setVisible(true);
        });

        create_graph.addActionListener(e -> {
            del_edge.setVisible(true);
            del_vertex.setVisible(true);
            add_vertex.setVisible(true);
            add_edge.setVisible(true);
            stop_add.setVisible(true);
        });

        stop_add.addActionListener(e -> {
            del_edge.setVisible(false);
            del_vertex.setVisible(false);
            add_edge.setVisible(false);
            stop_add.setVisible(false);
            add_vertex.setVisible(false);
        });

        stop_alg.addActionListener(e -> {
            next_step.setVisible(false);
            prev_step.setVisible(false);
            stop_alg.setVisible(false);
        });

        return tool_bar;
    }
}
