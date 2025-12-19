package org.example;

import org.example.ui.DashboardFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run application
        SwingUtilities.invokeLater(() -> {
            new DashboardFrame();
        });
    }
}