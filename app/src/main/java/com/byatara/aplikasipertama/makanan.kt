package com.byatara.aplikasipertama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class makanan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Menggunakan layout yang sesuai dengan file XML Anda (MenuPesananLayout)
        setContentView(R.layout.activity_makanan)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Jangan lupa memanggil fungsi setupListeners di sini
        setupListeners()
    }

    private fun setupListeners() {
        // Inisialisasi tombol menggunakan findViewById
        val btnLanjut = findViewById<Button>(R.id.btn_lanjut)

        // Inisialisasi input untuk mengambil data (Opsional, agar bisa dihitung)
        val etNominal1 = findViewById<EditText>(R.id.et_nominal)  // Creamy Latte (20k)
        val etNominal2 = findViewById<EditText>(R.id.et_nominal2) // Americano (15k)
        val etNominal3 = findViewById<EditText>(R.id.et_nominal3) // Latte (15k)

        btnLanjut.setOnClickListener {
            try {
                // Contoh logika sederhana menghitung total (bisa disesuaikan)
                val jumlah1 = etNominal1.text.toString().toIntOrNull() ?: 0
                val jumlah2 = etNominal2.text.toString().toIntOrNull() ?: 0
                val jumlah3 = etNominal3.text.toString().toIntOrNull() ?: 0

                val totalHarga = (jumlah1 * 20000) + (jumlah2 * 15000) + (jumlah3 * 15000)

                // Membuat Intent ke Activity nota (Diubah dari hasillform ke nota)
                val intent = Intent(this, nota::class.java)
                startActivity(intent)

            } catch (e: Exception) {
                Toast.makeText(this, "Terjadi kesalahan input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}