# PMD XML Generator GUI & Dashboard

Aplikasi berbasis Java yang bikin proses *generate* XML dari analisis kode statis [PMD](https://pmd.github.io/) jadi jauh lebih praktis. Nggak perlu lagi repot ngetik perintah panjang di terminal/CLI, cukup pakai GUI sederhana ini.

Setelah file XML berhasil di-*generate*, aplikasi akan otomatis membuka **PMD Code Analysis Dashboard Pro** di browser. Kamu tinggal *upload* file hasil *generate* tadi ke web untuk melihat visualisasi datanya.

## ✨ Fitur Utama
* **GUI Simpel:** Tinggal *browse* folder dan file tanpa ribet setup *command line*.
* **Auto-Open Dashboard:** Browser akan otomatis terbuka ke halaman web *dashboard* setelah proses *generate* selesai.
* **Dashboard Visualisasi Pro:**
  * Menampilkan ringkasan level *severity*: **Critical (P1)**, **High (P2)**, **Medium (P3)**, dan **Low (P4)**.
  * Grafik *Pie Chart* interaktif untuk melihat porsi pelanggaran kode.
  * Grafik *Bar Chart* untuk Top 10 *Rules* yang dilanggar (misal: `LocalVariableCouldBeFinal`, dll).
  * Tabel detail temuan kode lengkap dengan fitur pencarian dan *pagination*.
  * Fitur *Export* ke CSV dan *Download* grafik format PNG.

## 📦 Persiapan (Prerequisites)
Sebelum menjalankan aplikasi ini, pastikan kamu sudah menyiapkan:
1. **Java (JRE/JDK):** Pastikan sudah terinstal di PC/Laptop kamu.
2. **File Bin PMD:** * Download PMD versi terbaru langsung dari situs resminya: [https://pmd.github.io/](https://pmd.github.io/)
   * Ekstrak file zip tersebut. Kamu akan butuh *path* folder `bin`-nya untuk dimasukkan ke dalam aplikasi.

## 🚀 Cara Penggunaan

### 1. Generate XML via GUI
1. Buka aplikasi **PMD XML Generator**.
2. Isi *form* yang tersedia:
   * **Folder 'bin' PMD:** Arahkan ke folder `bin` dari PMD yang sudah diekstrak (contoh: `C:\pmd\bin`).
   * **Source Directory (-d):** Pilih folder *source code* Java yang mau dianalisis.
   * **Ruleset XML (-R):** Pilih file *ruleset* XML PMD yang kamu pakai.
   * **Output XML (-r):** Tentukan lokasi dan nama file *output* (contoh: `pmd-result.xml`).
3. Klik tombol **"Generate XML Sekarang"**.
4. Tunggu sampai status di bawah menunjukkan proses selesai.

### 2. Lihat Hasil di Web Dashboard
1. Browser akan otomatis terbuka mengarah ke halaman *dashboard*.
2. Klik tombol **"Choose File"** (Upload File `pmd-result.xml` Anda).
3. Pilih file `pmd-result.xml` yang baru saja kamu buat.
4. Selesai! Kamu bisa langsung melihat hasil analisa statis dari kodemu dalam bentuk grafik dan tabel.

## 🤝 Kontribusi
Punya ide buat bikin aplikasi ini makin keren? Silakan *fork* repo ini dan buat *Pull Request*, atau buka *Issue* di [https://github.com/zamagi17/pmd-generator-gui](https://github.com/zamagi17/pmd-generator-gui).
