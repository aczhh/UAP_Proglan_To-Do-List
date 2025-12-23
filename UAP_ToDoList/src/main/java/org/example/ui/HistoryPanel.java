package org.example.ui;

import org.example.util.FileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;

    public HistoryPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#FFF8B8"));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#FFF8B8"));

        JLabel title = new JLabel("Riwayat Aktivitas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(98, 149, 89));

        JLabel leftSpacer = new JLabel("");
        leftSpacer.setPreferredSize(new Dimension(100, 30));
        headerPanel.add(leftSpacer, BorderLayout.WEST);

        JButton btnBack = createStyledButton("Kembali");
        btnBack.addActionListener(e -> {
            Container parentContainer = getParent();
            if (parentContainer instanceof JPanel) {
                CardLayout layout = (CardLayout) parentContainer.getLayout();
                layout.show(parentContainer, "dashboard");
            }
        });
        headerPanel.add(btnBack, BorderLayout.EAST);
        headerPanel.add(title, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Waktu", "Aksi", "Nama Tugas"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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

        refreshHistory();
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

    public void refreshHistory() {
        ArrayList<String> history = FileHandler.loadHistory();
        tableModel.setRowCount(0);

        for (String record : history) {
            try {
                String[] parts = record.split(",");
                if (parts.length >= 3) {
                    String timestamp = parts[0].replace("T", " ");
                    if (timestamp.length() > 19) {
                        timestamp = timestamp.substring(0, 19);
                    }
                    tableModel.addRow(new Object[]{timestamp, parts[1], parts[2]});
                }
            } catch (Exception e) {
                System.err.println("Error parsing history: " + e.getMessage());
            }
        }
    }
}
