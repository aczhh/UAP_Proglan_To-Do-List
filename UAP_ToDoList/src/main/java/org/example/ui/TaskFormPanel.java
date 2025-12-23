package org.example.ui;

import org.example.model.Task;
import org.example.util.FileHandler;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskFormPanel extends JPanel {
    private DashboardFrame parent;
    private JTextField txtTitle, txtDeadline;
    private JTextArea txtDescription;
    private JComboBox<String> cbPriority;
    private int editingTaskId = -1;

    public TaskFormPanel(DashboardFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout(15, 15));
        // Latar belakang utama kuning seirama dengan TaskListPanel
        setBackground(Color.decode("#FFF8B8"));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ===== 1. HEADER SECTION =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#FFF8B8"));

        JLabel title = new JLabel("Form Tugas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(98, 149, 89));

        // Spacer kiri agar judul tetap di tengah
        JLabel leftSpacer = new JLabel("");
        leftSpacer.setPreferredSize(new Dimension(110, 30));
        headerPanel.add(leftSpacer, BorderLayout.WEST);

        JButton btnBack = createStyledButton("Kembali");
        btnBack.addActionListener(e -> {
            clearForm();
            parent.showPanel("dashboard");
        });
        headerPanel.add(btnBack, BorderLayout.EAST);
        headerPanel.add(title, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // ===== 2. FORM PANEL (HIJAU MUDA) =====
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setBackground(Color.decode("#E0FFCC")); // Warna hijau form
        formWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#CAF2C2"), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 12, 12, 12); // Jarak antar input diperbesar

        Font labelFont = new Font("Arial", Font.BOLD, 16); // Font label lebih besar
        Font inputFont = new Font("Arial", Font.PLAIN, 15); // Font input lebih besar

        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblJudul = new JLabel("Judul:");
        lblJudul.setFont(labelFont);
        lblJudul.setForeground(Color.decode("#AF7D77"));
        formWrapper.add(lblJudul, gbc);

        gbc.gridx = 1;
        txtTitle = new JTextField(20);
        txtTitle.setFont(inputFont);
        txtTitle.setBackground(Color.decode("#FEFFD6"));
        txtTitle.setForeground(Color.decode("#AF7D77"));
        formWrapper.add(txtTitle, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblDesc = new JLabel("Deskripsi:");
        lblDesc.setFont(labelFont);
        lblDesc.setForeground(Color.decode("#AF7D77"));
        formWrapper.add(lblDesc, gbc);

        gbc.gridx = 1;
        txtDescription = new JTextArea(4, 20);
        txtDescription.setFont(inputFont);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBackground(Color.decode("#FEFFD6"));
        txtDescription.setForeground(Color.decode("#AF7D77"));
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        formWrapper.add(scrollDesc, gbc);

        // Deadline
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblDeadline = new JLabel("Deadline:");
        lblDeadline.setFont(labelFont);
        lblDeadline.setForeground(Color.decode("#AF7D77"));
        formWrapper.add(lblDeadline, gbc);

        gbc.gridx = 1;
        txtDeadline = new JTextField(20);
        txtDeadline.setFont(inputFont);
        txtDeadline.setBackground(Color.decode("#FEFFD6"));
        txtDeadline.setForeground(Color.decode("#AF7D77"));
        txtDeadline.setText(LocalDate.now().toString());
        formWrapper.add(txtDeadline, gbc);

        // Priority
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblPrioritas = new JLabel("Prioritas:");
        lblPrioritas.setFont(labelFont);
        lblPrioritas.setForeground(Color.decode("#AF7D77"));
        formWrapper.add(lblPrioritas, gbc);

        gbc.gridx = 1;
        cbPriority = new JComboBox<>(new String[]{"Urgent", "Normal", "Non Urgent"});
        cbPriority.setFont(inputFont);
        cbPriority.setBackground(Color.decode("#FEFFD6"));
        cbPriority.setForeground(Color.decode("#AF7D77"));
        formWrapper.add(cbPriority, gbc);

        add(formWrapper, BorderLayout.CENTER);

        // ===== 3. BUTTON PANEL Bawah =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.decode("#FFF8B8"));

        JButton btnSave = createStyledButton("Simpan");
        JButton btnClear = createStyledButton("Reset");

        btnSave.addActionListener(e -> saveTask());
        btnClear.addActionListener(e -> clearForm());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Fungsi styling tombol agar seirama
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.decode("#FFD6C9"));
        btn.setForeground(Color.decode("#AF7D77"));
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        return btn;
    }

    private void saveTask() {
        try {
            if (txtTitle.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Judul tidak boleh kosong!");
            }

            LocalDate deadline;
            try {
                deadline = LocalDate.parse(txtDeadline.getText().trim());
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Format tanggal salah! Gunakan YYYY-MM-DD");
            }

            ArrayList<Task> tasks = FileHandler.loadTasks();

            if (editingTaskId == -1) {
                int newId = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
                Task newTask = new Task(
                        newId,
                        txtTitle.getText().trim(),
                        txtDescription.getText().trim(),
                        deadline,
                        cbPriority.getSelectedItem().toString(),
                        false
                );
                tasks.add(newTask);
                FileHandler.addHistory("CREATE", newTask.getTitle());
                JOptionPane.showMessageDialog(this, "Tugas berhasil ditambahkan!");
            } else {
                for (Task task : tasks) {
                    if (task.getId() == editingTaskId) {
                        task.setTitle(txtTitle.getText().trim());
                        task.setDescription(txtDescription.getText().trim());
                        task.setDeadline(deadline);
                        task.setPriority(cbPriority.getSelectedItem().toString());
                        FileHandler.addHistory("UPDATE", task.getTitle());
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Tugas berhasil diperbarui!");
            }

            FileHandler.saveTasks(tasks);
            parent.refreshTaskList();
            clearForm();
            parent.showPanel("tasklist");

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearForm() {
        txtTitle.setText("");
        txtDescription.setText("");
        txtDeadline.setText(LocalDate.now().toString());
        cbPriority.setSelectedIndex(1);
        editingTaskId = -1;
    }

    public void loadTask(int taskId) {
        ArrayList<Task> tasks = FileHandler.loadTasks();
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                txtTitle.setText(task.getTitle());
                txtDescription.setText(task.getDescription());
                txtDeadline.setText(task.getDeadline().toString());
                cbPriority.setSelectedItem(task.getPriority());
                editingTaskId = taskId;
                break;
            }
        }
    }
}