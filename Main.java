// Import library Scanner untuk membaca input dari pengguna
import java.util.Scanner;

// Kelas utama program restoran
public class Main {
    // Deklarasi objek Scanner untuk membaca input dari keyboard
    static Scanner input = new Scanner(System.in);

    // Array untuk menyimpan daftar menu restoran (objek dari class Menu)
    static Menu[] daftarMenu = new Menu[20];
    // Variabel penghitung jumlah menu yang tersedia
    static int jumlahMenu = 0;

    // --- METHOD UTAMA ---
    public static void main(String[] args) {
        // Memanggil method untuk mengisi data awal menu
        inisialisasiMenu();
        // Menjalankan menu utama aplikasi
        menuUtama();  
    }

    // --- METHOD UNTUK INISIALISASI MENU ---
    static void inisialisasiMenu() {
        // Menambahkan data menu awal ke dalam array daftarMenu
        daftarMenu[jumlahMenu++] = new Menu("Nasi Goreng", 25000, "Makanan");
        daftarMenu[jumlahMenu++] = new Menu("Mie Ayam", 20000, "Makanan");
        daftarMenu[jumlahMenu++] = new Menu("Sate Ayam", 30000, "Makanan");
        daftarMenu[jumlahMenu++] = new Menu("Udang Bakar", 22000, "Makanan");

        daftarMenu[jumlahMenu++] = new Menu("Es Teh", 5000, "Minuman");
        daftarMenu[jumlahMenu++] = new Menu("Americano", 10000, "Minuman");
        daftarMenu[jumlahMenu++] = new Menu("POP Mactha", 12000, "Minuman");
        daftarMenu[jumlahMenu++] = new Menu("Air Mineral", 4000, "Minuman");
    }

    // --- MENU UTAMA APLIKASI ---
    static void menuUtama() {
        // Perulangan agar menu utama tampil terus sampai user memilih keluar
        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN ===");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Manajemen Menu Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            String pilih = input.nextLine();

            // Struktur keputusan untuk navigasi menu utama
            switch (pilih) {
                case "1" -> menuPelanggan(); // Masuk ke menu pelanggan
                case "2" -> manajemenMenu(); // Masuk ke menu pengelolaan restoran
                case "3" -> {
                    System.out.println("Terima kasih! Program selesai.");
                    return; // Menghentikan program
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // --- MENAMPILKAN DAFTAR MENU MAKANAN DAN MINUMAN ---
    static void tampilkanMenu() {
        System.out.println("\n--- MENU MAKANAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            // Menampilkan hanya kategori "Makanan"
            if (daftarMenu[i].getKategori().equalsIgnoreCase("Makanan")) {
                System.out.println((i + 1) + ". " + daftarMenu[i]);
            }
        }
        System.out.println("\n--- MENU MINUMAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            // Menampilkan hanya kategori "Minuman"
            if (daftarMenu[i].getKategori().equalsIgnoreCase("Minuman")) {
                System.out.println((i + 1) + ". " + daftarMenu[i]);
            }
        }
    }

    // --- FITUR UNTUK PELANGGAN MELAKUKAN PEMESANAN ---
    static void menuPelanggan() {
        String[] pesanan = new String[50];  // Array untuk menyimpan nama pesanan
        int[] jumlah = new int[50];         // Array untuk menyimpan jumlah tiap pesanan
        int jumlahPesanan = 0;              // Menghitung total pesanan
        double total = 0;                   // Total harga seluruh pesanan

        // Pengulangan untuk proses pemesanan
        while (true) {
            tampilkanMenu(); // Menampilkan daftar menu
            System.out.print("\nMasukkan nama menu yang ingin dipesan (atau ketik 'selesai'): ");
            String nama = input.nextLine();

            // Jika pengguna mengetik 'selesai', maka keluar dari loop
            if (nama.equalsIgnoreCase("selesai")) break;

            // Mencari menu berdasarkan nama
            Menu menu = cariMenu(nama);
            if (menu == null) {
                System.out.println("Menu tidak ditemukan, silakan coba lagi!");
                continue;
            }

            // Input jumlah pesanan
            System.out.print("Masukkan jumlah: ");
            int qty = Integer.parseInt(input.nextLine());

            // Menyimpan data pesanan ke array
            pesanan[jumlahPesanan] = menu.getNama();
            jumlah[jumlahPesanan] = qty;
            jumlahPesanan++; 

            // Menambahkan total harga pesanan
            total += menu.getHarga() * qty;
        }

        // Setelah selesai memesan, cetak struk pembayaran
        cetakStruk(pesanan, jumlah, jumlahPesanan, total);
    }

    // --- MENCARI MENU BERDASARKAN NAMA ---
    static Menu cariMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].getNama().equalsIgnoreCase(nama)) {
                return daftarMenu[i]; // Mengembalikan objek Menu jika ditemukan
            }
        }
        return null; // Jika tidak ditemukan
    }

    // --- MENAMPILKAN STRUK PEMBAYARAN ---
    static void cetakStruk(String[] pesanan, int[] jumlah, int jumlahPesanan, double total) {
        // Menghitung pajak dan biaya tambahan
        double pajak = total * 0.10;   // Pajak 10%
        double service = 20000;        // Biaya layanan tetap
        double diskon = 0;             // Diskon awal = 0
        boolean promoMinuman = false;  // Status promo

        // Logika struktur keputusan untuk promo dan diskon
        if (total > 100000) {
            diskon = total * 0.10; // Diskon 10%
        } else if (total > 50000) {
            promoMinuman = true; // Promo beli 1 gratis 1 minuman
        }

        // Menampilkan detail struk pembayaran
        System.out.println("\n======= STRUK PEMBAYARAN =======");
        for (int i = 0; i < jumlahPesanan; i++) {
            Menu m = cariMenu(pesanan[i]);
            double subtotal = m.getHarga() * jumlah[i];
            System.out.println(pesanan[i] + " x" + jumlah[i] + " = Rp" + subtotal);
        }

        System.out.println("-------------------------------");
        System.out.println("Subtotal        : Rp" + total);
        System.out.println("Pajak (10%)     : Rp" + pajak);
        System.out.println("Biaya Pelayanan : Rp" + service);

        if (diskon > 0) {
            System.out.println("Diskon (10%)    : -Rp" + diskon);
        } else if (promoMinuman) {
            System.out.println("Promo: Beli 1 Gratis 1 Minuman (otomatis diterapkan)");
        }

        double totalBayar = total + pajak + service - diskon;
        System.out.println("-------------------------------");
        System.out.println("TOTAL BAYAR     : Rp" + totalBayar);
        System.out.println("======= TERIMA KASIH =======");
    }

    // --- MENU UNTUK PEMILIK RESTORAN (ADMIN) ---
    static void manajemenMenu() {
        while (true) {
            System.out.println("\n=== MANAJEMEN MENU RESTORAN ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih menu: ");
            String pilih = input.nextLine();

            // Struktur keputusan untuk memilih operasi manajemen
            switch (pilih) {
                case "1" -> tambahMenu(); // Tambahkan menu baru
                case "2" -> ubahHargaMenu(); // Ubah harga menu
                case "3" -> hapusMenu(); // Hapus menu
                case "4" -> {
                    return; // Kembali ke menu utama
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // --- MENAMBAHKAN MENU BARU ---
    static void tambahMenu() {
        System.out.print("Masukkan nama menu baru: ");
        String nama = input.nextLine();
        System.out.print("Masukkan harga: ");
        double harga = Double.parseDouble(input.nextLine());
        System.out.print("Masukkan kategori (Makanan/Minuman): ");
        String kategori = input.nextLine();

        daftarMenu[jumlahMenu++] = new Menu(nama, harga, kategori);
        System.out.println("Menu baru berhasil ditambahkan!");
    }

    // --- MENGUBAH HARGA MENU ---
    static void ubahHargaMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah: ");
        String nama = input.nextLine();

        Menu menu = cariMenu(nama);
        if (menu == null) {
            System.out.println("Menu tidak ditemukan!");
            return;
        }

        System.out.print("Apakah Anda yakin ingin mengubah harga menu ini? (Ya/Tidak): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            System.out.print("Masukkan harga baru: ");
            double hargaBaru = Double.parseDouble(input.nextLine());
            menu.setHarga(hargaBaru);
            System.out.println("Harga berhasil diubah!");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    // --- MENGHAPUS MENU ---
    static void hapusMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = input.nextLine();

        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].getNama().equalsIgnoreCase(nama)) {
                System.out.print("Apakah yakin ingin menghapus menu ini? (Ya/Tidak): ");
                String konfirmasi = input.nextLine();
                if (konfirmasi.equalsIgnoreCase("Ya")) {
                    // Menggeser elemen array agar tidak ada elemen kosong
                    for (int j = i; j < jumlahMenu - 1; j++) {
                        daftarMenu[j] = daftarMenu[j + 1];
                    }
                    jumlahMenu--;
                    System.out.println("Menu berhasil dihapus!");
                } else {
                    System.out.println("Penghapusan dibatalkan.");
                }
                return;
            }
        }
        System.out.println("Menu tidak ditemukan!");
    }
}
