/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ukabima.pmdgui.util;

import java.io.File;
/**
 *
 * @author teamu
 */
public class FileValidator {
    public static String validateInputs(String bin, String src, String rule, String out) {
        if (bin.isEmpty() || src.isEmpty() || rule.isEmpty() || out.isEmpty()) {
            return "Semua kolom input harus diisi.";
        }
        if (!new File(bin).isDirectory()) return "Folder 'bin' PMD tidak valid.";
        if (!new File(src).isDirectory()) return "Source Directory tidak ditemukan.";
        if (!new File(rule).isFile()) return "File Ruleset XML tidak ditemukan.";
        
        File outParent = new File(out).getParentFile();
        if (outParent != null && !outParent.exists()) return "Folder tujuan Output XML tidak ada.";
        
        return null; // Artinya valid
    }
}