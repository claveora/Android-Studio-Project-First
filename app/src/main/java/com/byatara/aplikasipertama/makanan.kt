package com.byatara.aplikasipertama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.byatara.aplikasipertama.R

class makanan : AppCompatActivity() {

    private lateinit var etNama: TextInputEditText
    private lateinit var jumlahSoto: EditText
    private lateinit var jumlahNasgor: EditText
    private lateinit var jumlahSop: EditText
    private lateinit var jumlahEsTeh: EditText
    private lateinit var jumlahEsJeruk: EditText
    private lateinit var jumlahKupi: EditText
    private lateinit var btnLanjut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        // ✅ Aktifkan Edge-to-Edge mode (supaya tampilan bisa full hingga ke status bar)
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan)

        // ✅ Sesuaikan padding dengan area status/navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi View
        etNama = findViewById(R.id.etNama)
        jumlahSoto = findViewById(R.id.jumlahSoto)
        jumlahNasgor = findViewById(R.id.jumlahNasgor)
        jumlahSop = findViewById(R.id.jumlahSop)
        jumlahEsTeh = findViewById(R.id.jumlahEsTeh)
        jumlahEsJeruk = findViewById(R.id.jumlahEsJeruk)
        jumlahKupi = findViewById(R.id.jumlahKupi)
        btnLanjut = findViewById(R.id.btnLanjut)

        // Tombol lanjut ke halaman nota
        btnLanjut.setOnClickListener {
            val intent = Intent(this, nota::class.java).apply {
                putExtra("nama", etNama.text.toString())
                putExtra("soto", jumlahSoto.text.toString())
                putExtra("nasgor", jumlahNasgor.text.toString())
                putExtra("sop", jumlahSop.text.toString())
                putExtra("esteh", jumlahEsTeh.text.toString())
                putExtra("esjeruk", jumlahEsJeruk.text.toString())
                putExtra("kopi", jumlahKupi.text.toString())
            }
            startActivity(intent)
        }
    }
}
