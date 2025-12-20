package org.example.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private TaskListPanel taskListPanel;
    private TaskFormPanel taskFormPanel;



    public DashboardFrame() {
        setTitle("To-Do List App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        taskListPanel = new TaskListPanel(this);
        taskFormPanel = new TaskFormPanel(this);

        mainPanel.add(createDashboardPanel(), "dashboard");
        mainPanel.add(taskListPanel, "tasklist");
        mainPanel.add(taskFormPanel, "taskform");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#FFE7D1"));

        JPanel headerWrapper = new JPanel(new BorderLayout());
        headerWrapper.setBackground(Color.decode("#FFE7D1"));
        headerWrapper.setBorder(new EmptyBorder(0, 60, 0, 60));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#CAF2C2"));
        headerPanel.setPreferredSize(new Dimension(0, 120));

        JLabel title = new JLabel("To-Do List Manager", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.decode("#AF7D77"));

        headerPanel.add(title, BorderLayout.CENTER);
        headerWrapper.add(headerPanel, BorderLayout.CENTER);

        panel.add(headerWrapper, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(Color.decode("#FFE7D1"));
        centerWrapper.setBorder(new EmptyBorder(20, 60, 40, 60));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.decode("#FEFFD6"));

        JPanel innerButtonGrid = new JPanel(new GridLayout(3, 1, 20, 20));
        innerButtonGrid.setBackground(Color.decode("#FEFFD6"));
        innerButtonGrid.setPreferredSize(new Dimension(500, 250));

        JButton btnTaskList = createMenuButton("Daftar Tugas");
        JButton btnAddTask = createMenuButton("Tambah Tugas");
        JButton btnHistory = createMenuButton("Riwayat");

        btnTaskList.addActionListener(e -> showPanel("tasklist"));
        btnAddTask.addActionListener(e -> {
            taskFormPanel.clearForm();
            showPanel("taskform");
        });

        innerButtonGrid.add(btnTaskList);
        innerButtonGrid.add(btnAddTask);
        innerButtonGrid.add(btnHistory);

        buttonPanel.add(innerButtonGrid);

        centerWrapper.add(buttonPanel, BorderLayout.CENTER);
        panel.add(centerWrapper, BorderLayout.CENTER);

        return panel;
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(Color.decode("#FFD6C9"));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    public void refreshTaskList() {
        taskListPanel.refreshTable();
    }

    public void editTask(int taskId) {
        taskFormPanel.loadTask(taskId);
        showPanel("taskform");
    }
}