package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;


public class GUI extends JFrame {
    private JPanel main_panel;
    private JPanel graph_panel;
    private JLabel status_text;
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
        graph_panel = new JPanel();
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
        JButton create_graph = new JButton("Создать граф");
        JButton save_graph = new JButton("Сохранить граф");
        JButton run_algorithm = new JButton("Запустить алгоритм");
        JButton next_step = new JButton("Следующий шаг");
        JButton prev_step = new JButton("Предыдущий шаг");
        JButton stop_alg = new JButton("Отановить");
        tool_bar.setFloatable(false);

        next_step.setVisible(false);
        prev_step.setVisible(false);
        stop_alg.setVisible(false);

        tool_bar.addSeparator(new Dimension(5, 5));
        tool_bar.add(create_graph);
        tool_bar.addSeparator(new Dimension(5, 5));
        tool_bar.add(save_graph);
        tool_bar.addSeparator(new Dimension(5, 5));
        tool_bar.add(run_algorithm);

        tool_bar.addSeparator(new Dimension(5, 80));
        tool_bar.add(next_step);
        tool_bar.addSeparator(new Dimension(5, 5));
        tool_bar.add(prev_step);
        tool_bar.addSeparator(new Dimension(5, 5));
        tool_bar.add(stop_alg);


        run_algorithm.addActionListener(e -> {
            next_step.setVisible(true);
            prev_step.setVisible(true);
            stop_alg.setVisible(true);
        });

        stop_alg.addActionListener(e -> {
            next_step.setVisible(false);
            prev_step.setVisible(false);
            stop_alg.setVisible(false);
        });

        return tool_bar;
    }
}
