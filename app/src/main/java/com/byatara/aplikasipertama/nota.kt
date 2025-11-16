package com.byatara.aplikasipertama

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class nota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // ✅ Aktifkan tampilan edge-to-edge
        setContentView(R.layout.activity_nota)

        // Atur padding otomatis agar konten tidak tertutup status bar/navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nama = intent.getStringExtra("nama")
        val soto = intent.getStringExtra("soto")?.toIntOrNull() ?: 0
        val nasgor = intent.getStringExtra("nasgor")?.toIntOrNull() ?: 0
        val sop = intent.getStringExtra("sop")?.toIntOrNull() ?: 0
        val esteh = intent.getStringExtra("esteh")?.toIntOrNull() ?: 0
        val esjeruk = intent.getStringExtra("esjeruk")?.toIntOrNull() ?: 0
        val kopi = intent.getStringExtra("kopi")?.toIntOrNull() ?: 0

        // Harga satuan
        val hargaSoto = 8000
        val hargaNasgor = 15000
        val hargaSop = 10000
        val hargaEsTeh = 3000
        val hargaEsJeruk = 4000
        val hargaKopi = 25000

        // Hitung total
        val total = (soto * hargaSoto) + (nasgor * hargaNasgor) +
                (sop * hargaSop) + (esteh * hargaEsTeh) +
                (esjeruk * hargaEsJeruk) + (kopi * hargaKopi)

        // Tampilkan data ke layout
        findViewById<TextView>(R.id.pemesan).text = "Nama: $nama"

        val daftarPesanan = buildString {
            if (soto > 0) append("Soto Ayam x$soto - Rp ${soto * hargaSoto}\n")
            if (nasgor > 0) append("Nasi Goreng x$nasgor - Rp ${nasgor * hargaNasgor}\n")
            if (sop > 0) append("Sop Daging x$sop - Rp ${sop * hargaSop}\n")
            if (esteh > 0) append("Es Teh x$esteh - Rp ${esteh * hargaEsTeh}\n")
            if (esjeruk > 0) append("Es Jeruk x$esjeruk - Rp ${esjeruk * hargaEsJeruk}\n")
            if (kopi > 0) append("Kopi Claveooka x$kopi - Rp ${kopi * hargaKopi}\n")
        }

        // Gunakan menu1 untuk menampilkan daftar pesanan
        findViewById<TextView>(R.id.menu1).text = daftarPesanan.ifEmpty { "Tidak ada pesanan." }

        findViewById<TextView>(R.id.total).text = "Total: Rp $total"

        findViewById<Button>(R.id.btnKembali)?.setOnClickListener {
            finish()
        }
    }
}
