# Pure Palate

Pure Palate adalah aplikasi Android yang memungkinkan pengguna menjelajahi berbagai resep makanan, mencari informasi resep berdasarkan nama atau bahan, serta menyimpan resep favorit secara lokal. Aplikasi ini menggunakan API dari TheMealDB untuk menyediakan berbagai pilihan resep dari seluruh dunia. Selain itu, Pure Palate mendukung pengaturan tema gelap/terang yang dapat disimpan secara lokal.

## Fitur
- Pencarian resep berdasarkan nama atau bahan.
- Daftar resep langsung ditampilkan di halaman utama.
- Favorit: Simpan resep yang disukai untuk diakses kapan saja secara offline.
- Tema Terang/Gelap: Tema aplikasi dapat disimpan dan diterapkan secara lokal.
- Offline Support untuk daftar favorit dengan SQLite.

## Penggunaan
1.	Luncurkan aplikasi Pure Palate di perangkat Android Anda.
2.	Di halaman utama (Home), jelajahi resep atau gunakan fitur Pencarian untuk menemukan resep yang diinginkan.
3.	Klik salah satu resep untuk melihat detail resep, bahan-bahan, serta langkah-langkah memasak.
4.	Tekan ikon Favorit untuk menyimpan resep favorit Anda.
5.	Akses tab Favorites untuk melihat daftar resep yang telah Anda simpan.
6.	Gunakan tombol Dark Mode/ Light Mode di halaman utama untuk mengubah tema aplikasi.

## Implementasi Teknis

Pure Palate dikembangkan sebagai aplikasi Android berbasis Java yang memanfaatkan API dari TheMealDB untuk menyediakan berbagai resep makanan. Aplikasi ini menggunakan fragment-based navigation dengan dua bagian utama, yaitu Home dan Favorites, yang dikelola melalui BottomNavigationView. Pada bagian Home, pengguna dapat melihat daftar resep atau mencari resep berdasarkan nama menggunakan fitur pencarian yang terintegrasi dengan API.

Pengambilan data dari API dilakukan menggunakan Retrofit, sebuah pustaka HTTP yang efisien dan fleksibel. Setiap resep yang diterima dari API diproses untuk menampilkan informasi lengkap, seperti nama, gambar, bahan-bahan, dan langkah memasak. Untuk mendukung pengalaman offline, aplikasi menyediakan fitur Favorit, di mana pengguna dapat menyimpan resep ke database lokal menggunakan SQLite. Proses penyimpanan dan pengelolaan data favorit dilakukan melalui kelas FavoriteDbHelper.

Selain itu, aplikasi mendukung pengaturan Tema Gelap/Terang yang dapat diubah langsung oleh pengguna melalui tombol di halaman Home. Pilihan tema ini disimpan secara lokal menggunakan SharedPreferences, sehingga akan tetap diterapkan meskipun aplikasi ditutup atau dijalankan kembali.