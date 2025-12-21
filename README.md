# To-Do List Manager
Aplikasi manajemen tugas (To-Do List) desktop sederhana yang dibangun menggunakan Java Swing. Aplikasi ini memungkinkan pengguna untuk mencatat, mengelola, dan melacak riwayat aktivitas tugas dengan antarmuka yang berwarna dan intuitif.
## Fitur Utama
- **Manajemen Tugas:** Tambah, edit, dan hapus tugas dengan mudah
- **Prioritas & Pengurutan:** Atur tingkat urgensi (Urgent, Normal, Non Urgent) dan batas waktu pengerjaan
- **Pencarian & Pengurutan:** Cari tugas berdasarkan kata kunci atau urutkan berdasarkan prioritas dan tanggal deadline
- **Riwayat Aktivitas:** Melacak log setiap kali tugas dibuat, diperbarui, atau dihapus
- **Penyimpanan Lokal (CSV):** Data disimpan secara otomatis dalam format *.csv* di folder *data/* sehingga data tidak hilang saat aplikasi ditutup
- **Antarmuka Responsif:** Menggunakan *Cardlayout* untuk navigasi antar panel yang mulus
## Cara Menjalankan
**1. Prasyarat:** 
- Pastikan telah menginstal Java JDK versi terbaru
- IDE seperti Intellij IDEA, Eclipse, atau NetBeans
  
**2. Kloning Repositori:**
```
git clone https://github.com/username/todo-list-app.git
```
**3. Kompilasi dan Jalankan:** Jika menggunakan terminal/command prompt:
```
javac -d bin src/main/java/org/example/*.java
java -cp bin org.example.Main
```
Atau cukup jalankan kelas *Main.java* langsung dari IDE nya
## Tampilan Aplikasi
Aplikasi ini menggunakan skema warna pastel yang nyaman di mata:
- **Header:** Hijau Muda (#CAF2C2)
- **Background Utama:** Krem/Kuning Gading (#FFE7D1)
- **Tombol:** Salmon/Coral Muda (#FFD6C9)
