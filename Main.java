/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mahasiswa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Sani Rahmatani
 */
public class Main {
    // Nama file data sesuai spesifikasi [cite: 13]
    private static final String FILENAME = "mahasiswa.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan = 0;
        do {
            tampilkanMenu();
            System.out.print("Masukkan Pilihan: ");
            try {
                // Membaca input pilihan
                pilihan = Integer.parseInt(scanner.nextLine());
                System.out.println("------------------------------------");

                switch (pilihan) {
                    case 1:
                        inputAndSave(); // Menu 1: Input dan Simpan [cite: 23, 27]
                        break;
                    case 2:
                        readAndDisplay(); // Menu 2: Tampilkan Data [cite: 24, 27]
                        break;
                    case 3:
                        recursiveSearchMenu(); // Menu 3: Pencarian Rekursif [cite: 25, 27]
                        break;
                    case 4:
                        System.out.println("Terima kasih, program berakhir."); // Menu 4: Keluar [cite: 26]
                        break;
                    default:
                        System.err.println("Pilihan tidak valid. Silakan coba lagi.");
                        break;
                }
            } catch (NumberFormatException e) {
                // Penanganan error jika input menu bukan angka [cite: 28]
                System.err.println("Input harus berupa angka!");
            } catch (Exception e) {
                // Penanganan error umum [cite: 28]
                System.err.println("Terjadi kesalahan tak terduga: " + e.getMessage());
            }
            System.out.println();
        } while (pilihan != 4);
    }

    // Method untuk menampilkan Menu Utama [cite: 22-26]
    private static void tampilkanMenu() {
        System.out.println("=== PROGRAM DATA MAHASISWA ===");
        System.out.println("1. Input dan Simpan Data ke File");
        System.out.println("2. Tampilkan Data dari File");
        System.out.println("3. Pencarian Rekursif (method recursion)");
        System.out.println("4. Keluar");
        System.out.println("------------------------------------");
    }

    // Method 1: Input dan Simpan Data [cite: 23, 27]
    private static void inputAndSave() {
        System.out.println("--- Input Data Mahasiswa ---");
        System.out.print("NIM: ");
        String nim = scanner.nextLine();
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        int umur = 0;
        double ipk = 0.0;

        try {
            System.out.print("Umur (angka): ");
            umur = Integer.parseInt(scanner.nextLine());
            System.out.print("IPK (misal 3.75): ");
            ipk = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Input Umur dan IPK harus berupa angka! Pembatalan input.");
            return;
        }

        Mahasiswa mhs = new Mahasiswa(nim, nama, umur, ipk);

        // Gunakan try-with-resources untuk PrintWriter 
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILENAME, true))) {
            pw.println(mhs.toFileString());
            System.out.println("\n✅ Data berhasil disimpan ke " + FILENAME);
        } catch (IOException e) {
            // Penanganan error I/O saat menulis file [cite: 28]
            System.err.println("❌ Gagal menulis ke file: " + e.getMessage());
        }
    }

    // Method 2: Tampilkan Data dari File [cite: 24, 27]
    private static void readAndDisplay() {
        System.out.println("--- Tampilan Data Mahasiswa dari File ---");
        System.out.println("+------------+----------------------+-------+-------+");
        System.out.println("| NIM        | NAMA                 | UMUR  | IPK   |");
        System.out.println("+------------+----------------------+-------+-------+");

        // Gunakan try-with-resources untuk BufferedReader 
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            boolean dataAda = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Mahasiswa mhs = new Mahasiswa(
                        parts[0].trim(),
                        parts[1].trim(),
                        Integer.parseInt(parts[2].trim()),
                        Double.parseDouble(parts[3].trim())
                    );
                    System.out.println(mhs.toString()); // Tampilkan data menggunakan toString()
                    dataAda = true;
                }
            }
            if (!dataAda) {
                System.out.println("| ** Tidak ada data mahasiswa. ** |");
            }
        } catch (IOException e) {
            // Penanganan error I/O saat membaca file [cite: 28]
            System.err.println("❌ Gagal membaca file " + FILENAME + ". Pastikan file sudah ada atau tidak corrupt.");
        } catch (NumberFormatException e) {
             // Penanganan error format data di dalam file [cite: 28]
            System.err.println("❌ Format data di file tidak valid.");
        }
        System.out.println("+------------+----------------------+-------+-------+");
    }

    // Method 3.a: Menu Pemicu Pencarian Rekursif
    private static void recursiveSearchMenu() {
        System.out.println("--- Pencarian Data Mahasiswa Rekursif ---");
        System.out.print("Masukkan NIM yang dicari: ");
        String targetNIM = scanner.nextLine();

        // Muat data dari file untuk pencarian
        List<Mahasiswa> listMahasiswa = loadDataFromFile();

        if (listMahasiswa.isEmpty()) {
            System.out.println("Data mahasiswa kosong. Lakukan input terlebih dahulu.");
            return;
        }

        // Panggil method rekursif [cite: 25]
        Mahasiswa result = recursiveSearch(listMahasiswa, targetNIM, 0);

        if (result != null) {
            System.out.println("\n✅ Data Ditemukan!");
            System.out.println("+------------+----------------------+-------+-------+");
            System.out.println("| NIM        | NAMA                 | UMUR  | IPK   |");
            System.out.println("+------------+----------------------+-------+-------+");
            System.out.println(result.toString());
            System.out.println("+------------+----------------------+-------+-------+");
        } else {
            System.out.println("❌ NIM '" + targetNIM + "' tidak ditemukan.");
        }
    }

    // Method Pembantu: Memuat data dari file ke List
    private static List<Mahasiswa> loadDataFromFile() {
        List<Mahasiswa> listMahasiswa = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    listMahasiswa.add(new Mahasiswa(
                        parts[0].trim(),
                        parts[1].trim(),
                        Integer.parseInt(parts[2].trim()),
                        Double.parseDouble(parts[3].trim())
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Tidak perlu error yang keras, hanya mengembalikan list kosong
        }
        return listMahasiswa;
    }

    // Method 3.b: Implementasi Rekursif (Pencarian Berdasarkan NIM) [cite: 10, 25]
    private static Mahasiswa recursiveSearch(List<Mahasiswa> list, String targetNIM, int index) {
        // Base Case 1: Seluruh list sudah diiterasi dan data tidak ditemukan
        if (index >= list.size()) {
            return null;
        }
        
        // Base Case 2: Data ditemukan
        if (list.get(index).getNim().equals(targetNIM)) {
            return list.get(index);
        }

        // Recursive Step: Lanjutkan pencarian ke elemen berikutnya
        return recursiveSearch(list, targetNIM, index + 1);
    }
}
    
