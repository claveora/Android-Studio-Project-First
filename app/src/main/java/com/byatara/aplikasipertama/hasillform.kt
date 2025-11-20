package com.byatara.aplikasipertama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class hasillform : AppCompatActivity() {

    private lateinit var tvNama: TextView
    private lateinit var tvAlamat: TextView
    private lateinit var tvNomor: TextView
    private lateinit var tvAgama: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvHobi: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // ✅ Mengaktifkan edge-to-edge layout
        setContentView(R.layout.activity_hasillform)

        val btnKembali = findViewById<Button>(R.id.btnKembali)
        btnKembali.setOnClickListener {
            finish()
        }

        // Inisialisasi TextView
        tvNama = findViewById(R.id.tvNama)
        tvAlamat = findViewById(R.id.tvAlamat)
        tvNomor = findViewById(R.id.tvNomor)
        tvAgama = findViewById(R.id.tvAgama)
        tvGender = findViewById(R.id.tvGender)
        tvHobi = findViewById(R.id.tvHobi)

        // Ambil data dari Intent
        val nama = intent.getStringExtra("nama")
        val alamat = intent.getStringExtra("alamat")
        val nohp = intent.getStringExtra("nohp")
        val agama = intent.getStringExtra("agama")
        val gender = intent.getStringExtra("gender")
        val hobi = intent.getStringExtra("hobi")

        // Tampilkan data
        tvNama.text = "Nama: $nama"
        tvAlamat.text = "Alamat: $alamat"
        tvNomor.text = "Nomor HP: $nohp"
        tvAgama.text = "Agama: $agama"
        tvGender.text = "Gender: $gender"
        tvHobi.text = "Hobi: $hobi"
    }
}