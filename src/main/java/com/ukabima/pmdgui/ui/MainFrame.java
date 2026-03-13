/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ukabima.pmdgui.ui;

import com.ukabima.pmdgui.service.PmdRunner;
import com.ukabima.pmdgui.util.FileValidator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/**
 *
 * @author teamu
 */
public class MainFrame extends JFrame {
    private JTextField pmdBinField, srcDirField, rulesetField, outputXMLField;
    private JButton generateBtn;
    private JProgressBar progressBar;

    public MainFrame() {
        super("PMD XML Generator");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 420);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        try {
            // Mencari di dalam folder resources (untuk JAR)
            java.net.URL imgURL = getClass().getResource("/logo-ebpr.png");
            
            System.out.println("log ebpr = " + imgURL);

            Image originalImage;
            if (imgURL != null) {
                // Jika ketemu di resources
                originalImage = new ImageIcon(imgURL).getImage();
            } else {
                // Jika tidak ketemu (fallback mencari di folder aplikasi)
                originalImage = new ImageIcon("logo-ebpr.png").getImage();
            }

            // Resize image menjadi 64x64
            Image scaledImage = originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            setIconImage(scaledImage);

        } catch (Exception e) {
            System.out.println("Gagal memuat logo: " + e.getMessage());
        }

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        pmdBinField = new JTextField("C:\\Office\\zaky\\pmd\\Aplikasi PMD\\pmd-dist-7.20.0-bin\\pmd-bin-7.20.0\\bin");
        srcDirField = new JTextField("C:\\Office\\zaky\\v12\\ebpr\\src\\main\\java");
        rulesetField = new JTextField("C:\\Office\\zaky\\pmd\\quality-tools\\pmd\\ruleset-java8.xml");
        outputXMLField = new JTextField("C:\\Office\\zaky\\pmd\\pmd-result.xml");

        addInputRow(inputPanel, "Folder 'bin' PMD:", pmdBinField, JFileChooser.DIRECTORIES_ONLY);
        addInputRow(inputPanel, "Source Directory (-d):", srcDirField, JFileChooser.DIRECTORIES_ONLY);
        addInputRow(inputPanel, "Ruleset XML (-R):", rulesetField, JFileChooser.FILES_ONLY);
        addInputRow(inputPanel, "Output XML (-r):", outputXMLField, JFileChooser.FILES_ONLY);

        // Panel Bottom
        generateBtn = new JButton("Generate XML Sekarang");
        generateBtn.setBackground(new Color(40, 167, 69));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Status: Menunggu instruksi...");

        JLabel creditLabel = new JLabel("Created by Zaky | UKABIMA Teams");
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditLabel.setForeground(Color.GRAY);

        JLabel githubLabel = new JLabel("<html><a href=''>View on GitHub</a></html>");
        githubLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        githubLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/zamagi17/pmd-generator-gui"));
                } catch (Exception ex) {
                    System.out.println("Gagal membuka link: " + ex.getMessage());
                }
            }
        });

        // Panel penampung untuk teks credit dan link github agar sejajar kiri-kanan
        JPanel footerInfoPanel = new JPanel(new BorderLayout());
        footerInfoPanel.add(creditLabel, BorderLayout.WEST);
        footerInfoPanel.add(githubLabel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.add(generateBtn, BorderLayout.NORTH);
        
        JPanel progressPanel = new JPanel(new BorderLayout(0, 5));
        progressPanel.add(progressBar, BorderLayout.CENTER);
        progressPanel.add(footerInfoPanel, BorderLayout.SOUTH); // Mengganti creditLabel langsung dengan panel footer
        bottomPanel.add(progressPanel, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        generateBtn.addActionListener(e -> startGeneration());
    }

    private void addInputRow(JPanel panel, String label, JTextField field, int mode) {
        panel.add(new JLabel(label));
        JPanel p = new JPanel(new BorderLayout(5, 0));
        JButton btn = new JButton("...");
        btn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(mode);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                field.setText(fc.getSelectedFile().getAbsolutePath());
            }
        });
        p.add(field, BorderLayout.CENTER);
        p.add(btn, BorderLayout.EAST);
        panel.add(p);
    }

    private void startGeneration() {
        String error = FileValidator.validateInputs(pmdBinField.getText(), srcDirField.getText(), 
                                                   rulesetField.getText(), outputXMLField.getText());
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        generateBtn.setEnabled(false);
        PmdRunner runner = new PmdRunner(
            pmdBinField.getText().trim(),
            srcDirField.getText().trim(),
            rulesetField.getText().trim(),
            outputXMLField.getText().trim(),
            progressBar,
            () -> generateBtn.setEnabled(true)
        );
        runner.execute();
    }
}