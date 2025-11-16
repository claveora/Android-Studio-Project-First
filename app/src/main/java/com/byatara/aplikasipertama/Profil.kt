package com.byatara.aplikasipertama

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class Profil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Pastikan nama layout XML sesuai
        setContentView(R.layout.activity_profil)

        // Atur padding untuk tampilan penuh (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Contoh inisialisasi jika ada data yang ingin diubah secara dinamis:
        // val tvNama = findViewById<TextView>(R.id.tv_profile_name)
        // tvNama.text = "Data dari user lain"
    }
}