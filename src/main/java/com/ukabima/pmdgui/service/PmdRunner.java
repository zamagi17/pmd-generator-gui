/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ukabima.pmdgui.service;


import java.awt.Desktop;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author teamu
 */
public class PmdRunner extends SwingWorker<Boolean, String> {
    private final String binPath, srcPath, ruleset, output;
    private final JProgressBar progressBar;
    private final Runnable onComplete;
    private Process currentProcess;

    public PmdRunner(String bin, String src, String rule, String out, JProgressBar bar, Runnable callback) {
        this.binPath = bin;
        this.srcPath = src;
        this.ruleset = rule;
        this.output = out;
        this.progressBar = bar;
        this.onComplete = callback;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
            "cmd.exe", "/c", "pmd.bat", "check", "-d", srcPath, "-R", ruleset, "-f", "xml", "-r", output, "--progress"
        );
        pb.directory(new File(binPath));
        pb.redirectErrorStream(true);
        
        currentProcess = pb.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(currentProcess.getInputStream()))) {
            String line;
            Pattern pattern = Pattern.compile("(\\d+)%");
            while ((line = reader.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                if (m.find()) publish(m.group(1));
            }
        }
        int exitCode = currentProcess.waitFor();
        return exitCode == 0 || exitCode == 4;
    }

    @Override
    protected void process(List<String> chunks) {
        String val = chunks.get(chunks.size() - 1);
        progressBar.setValue(Integer.parseInt(val));
        progressBar.setString("Menganalisis: " + val + "%");
    }

    @Override
    protected void done() {
        onComplete.run();
        try {
            if (get()) {
                progressBar.setValue(100);
                progressBar.setString("Status: Selesai!");

                // 1. Ambil file dashboard dari resources
                // Karena dashboard-pmd.html statis, kita bisa buka langsung jika di luar JAR
                // Namun cara paling aman untuk "Dashboard Pro" kamu adalah:
                File xmlFile = new File(output);

                // Berikan info sukses
                JOptionPane.showMessageDialog(null,
                        "Mantap! File XML berhasil digenerate.\nLokasi: " + xmlFile.getAbsolutePath(),
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // 2. Buka Dashboard HTML
                // Kita asumsikan dashboard-pmd.html diletakkan sejajar dengan file .exe/jar nantinya
                // Atau kamu bisa menyalin dashboard tersebut dari resources ke folder output
                exportDashboard(xmlFile.getParentFile());

            } else {
                JOptionPane.showMessageDialog(null, "Gagal menjalankan PMD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

// Method untuk menyalin dashboard dari resources ke folder tujuan agar bisa dibuka browser
    private void exportDashboard(File targetDir) {
        try {
            File dashboardFile = new File(targetDir, "dashboard-pmd-v2.html");

            // Copy dari resources (src/main/resources/dashboard-pmd.html) ke folder output
            if (!dashboardFile.exists()) {
                java.nio.file.Files.copy(
                        getClass().getResourceAsStream("/dashboard-pmd-v2.html"),
                        dashboardFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
            }

            // Buka di Browser
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(dashboardFile.toURI());
            }
        } catch (Exception e) {
            System.out.println("Gagal membuka dashboard: " + e.getMessage());
        }
    }
}
