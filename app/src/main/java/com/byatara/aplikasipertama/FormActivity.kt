package com.byatara.aplikasipertama

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormActivity : AppCompatActivity() {

    // Deklarasi semua komponen UI
    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etNomor: EditText
    private lateinit var etSimpan: Button
    private lateinit var spinnerAgama: Spinner

    private lateinit var rgGender: RadioGroup
    private lateinit var rbLaki: RadioButton
    private lateinit var rbPerempuan: RadioButton

    private lateinit var cbMembaca: CheckBox
    private lateinit var cbMenulis: CheckBox
    private lateinit var cbMusik: CheckBox
    private lateinit var cbMemasak: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)

        // Inisialisasi semua komponen
        initViews() 
        setupSpinner()

        // Tombol Simpan
        etSimpan.setOnClickListener {
            val nama = etNama.text.toString()
            val alamat = etAlamat.text.toString()
            val nohp = etNomor.text.toString()
            val agama = spinnerAgama.selectedItem.toString()

            val gender = when (rgGender.checkedRadioButtonId) {
                R.id.laki -> "Laki-Laki"
                R.id.perempuan -> "Perempuan"
                else -> "Belum dipilih"
            }

            val hobiList = mutableListOf<String>()
            if (cbMembaca.isChecked) hobiList.add("Membaca")
            if (cbMenulis.isChecked) hobiList.add("Menulis")
            if (cbMusik.isChecked) hobiList.add("Mendengarkan Musik")
            if (cbMemasak.isChecked) hobiList.add("Memasak")
            val hobi = hobiList.joinToString(", ")

            val keHasil = Intent(this, hasillform::class.java).apply {
                putExtra("nama", nama)
                putExtra("alamat", alamat)
                putExtra("nohp", nohp)
                putExtra("agama", agama)
                putExtra("gender", gender)
                putExtra("hobi", hobi)
            }
            startActivity(keHasil)
        }

        // Handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MainActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        etNomor = findViewById(R.id.etNomor)
        etSimpan = findViewById(R.id.btSimpan)
        spinnerAgama = findViewById(R.id.spAgama)

        rgGender = findViewById(R.id.rgGender)
        rbLaki = findViewById(R.id.laki)
        rbPerempuan = findViewById(R.id.perempuan)

        cbMembaca = findViewById(R.id.cbMembaca)
        cbMenulis = findViewById(R.id.cbMenulis)
        cbMusik = findViewById(R.id.cbMusik)
        cbMemasak = findViewById(R.id.cbMemasak)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.list_agama,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAgama.adapter = adapter
    }
}