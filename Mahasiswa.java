/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mahasiswa;

/**
 *
 * @author Sani Rahmatani
 */
public class Mahasiswa {
    // 1. Atribut sesuai spesifikasi: nim, nama, umur, ipk [cite: 19]
    private String nim;
    private String nama;
    private int umur;
    private double ipk;

    // 2. Constructor [cite: 20]
    public Mahasiswa(String nim, String nama, int umur, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.umur = umur;
        this.ipk = ipk;
    }

    // Getter untuk digunakan di method rekursif
    public String getNim() {
        return nim;
    }

    // Method untuk format data saat disimpan ke file (dipisahkan koma)
    public String toFileString() {
        // Data dipisahkan oleh koma
        return nim + "," + nama + "," + umur + "," + ipk;
    }

    // 3. Method toString() untuk tampilan di console [cite: 20]
    @Override
    public String toString() {
        // Menggunakan format String.format untuk tampilan tabel rapi
        return String.format("| %-10s | %-20s | %-5d | %-5.2f |", nim, nama, umur, ipk);
    }
}