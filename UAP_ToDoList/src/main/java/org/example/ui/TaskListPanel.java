package org.example.ui;

import org.example.model.Task;
import org.example.util.FileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskListPanel extends JPanel {
    private DashboardFrame parent;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Task> tasks;
    private JTextField searchField;

    public TaskListPanel(DashboardFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#FFF8B8"));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#FFF8B8"));

        JLabel title = new JLabel("Daftar Tugas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(98, 149, 89));

        JLabel leftSpacer = new JLabel("");
        leftSpacer.setPreferredSize(new Dimension(100, 30));
        headerPanel.add(leftSpacer, BorderLayout.WEST);

        JButton btnBack = createStyledButton("Kembali");
        btnBack.addActionListener(e -> parent.showPanel("dashboard"));
        headerPanel.add(btnBack, BorderLayout.EAST);
        headerPanel.add(title, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBackground(Color.decode("#FFF8B8"));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        searchField = new JTextField(20);
        searchField.setMaximumSize(new Dimension(200, 30));

        JButton btnSearch = createStyledButton("Cari");
        JButton btnSortPriority = createStyledButton("Priority");
        JButton btnSortDeadline = createStyledButton("Deadline");
        JButton btnEdit = createStyledButton("Edit");
        JButton btnDelete = createStyledButton("Hapus");
        JButton btnComplete = createStyledButton("Selesai");

        btnSearch.addActionListener(e -> searchTasks());
        btnSortPriority.addActionListener(e -> sortByPriority());
        btnSortDeadline.addActionListener(e -> sortByDeadline());

        controlPanel.add(new JLabel(" Cari: "));
        controlPanel.add(searchField);
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        controlPanel.add(btnSearch);
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        controlPanel.add(btnSortPriority);
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        controlPanel.add(btnSortDeadline);
        controlPanel.add(Box.createHorizontalGlue());
        controlPanel.add(btnEdit);
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        controlPanel.add(btnDelete);
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        controlPanel.add(btnComplete);

        add(controlPanel, BorderLayout.SOUTH);

        String[] columns = {"ID", "Judul", "Deskripsi", "Deadline", "Priority", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.decode("#FFD6C9"));
        table.setBackground(Color.decode("#E0FFCC"));
        table.setForeground(Color.decode("#AF7D77"));

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(Color.decode("#CAF2C2"));
                label.setForeground(Color.decode("#AF7D77"));
                label.setFont(new Font("Arial", Font.BOLD, 13));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#FFD6C9")));
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.decode("#FEFFD6"));
        add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.decode("#FFD6C9"));
        btn.setForeground(Color.decode("#AF7D77"));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return btn;
    }

    public void refreshTable() {
        tasks = FileHandler.loadTasks();
        updateTable(tasks);
    }

    private void updateTable(ArrayList<Task> taskList) {
        tableModel.setRowCount(0);
        for (Task task : taskList) {
            tableModel.addRow(new Object[]{
                    task.getId(), task.getTitle(), task.getDescription(),
                    task.getDeadline(), task.getPriority(),
                    task.isCompleted() ? "Selesai" : "Belum"
            });
        }
    }

    private void searchTasks() {
        String keyword = searchField.getText().toLowerCase();
        ArrayList<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(keyword) ||
                    task.getDescription().toLowerCase().contains(keyword)) {
                filtered.add(task);
            }
        }
        updateTable(filtered);
    }

    private void sortByPriority() {
        tasks.sort((t1, t2) -> {
            int p1 = getPriorityValue(t1.getPriority());
            int p2 = getPriorityValue(t2.getPriority());

            return Integer.compare(p1, p2);
        });
        updateTable(tasks);
    }

    private int getPriorityValue(String priority) {
        switch (priority) {
            case "Urgent": return 1;
            case "Normal": return 2;
            case "Non Urgent": return 3;
            default: return 4;
        }
    }

    private void sortByDeadline() {
        tasks.sort(Comparator.comparing(Task::getDeadline));
        updateTable(tasks);
    }
}
