package com.byatara.aplikasipertama // Ganti dengan package name proyek Anda

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge // Perlu jika menggunakan template Android terbaru

class Temperatur: AppCompatActivity() {

    // Deklarasi variabel untuk elemen UI
    private lateinit var etInputTemp: EditText
    private lateinit var spinnerSource: Spinner
    private lateinit var spinnerTarget: Spinner
    private lateinit var btnConvert: Button
    private lateinit var tvOutputResult: TextView

    // Daftar unit suhu yang digunakan, sesuai dengan array di strings.xml
    private val unitList = arrayOf("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Gunakan enableEdgeToEdge jika Anda ingin tampilan penuh layar
        enableEdgeToEdge()
        setContentView(R.layout.activity_temperatur)

        // Penanganan insets untuk tampilan edge-to-edge (opsional, tergantung layout Anda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi elemen UI
        etInputTemp = findViewById(R.id.et_input_temp)
        spinnerSource = findViewById(R.id.spinner_source)
        spinnerTarget = findViewById(R.id.spinner_target)
        btnConvert = findViewById(R.id.btn_convert)
        tvOutputResult = findViewById(R.id.tv_output_result)

        // Set listener untuk tombol konversi
        btnConvert.setOnClickListener {
            performConversion()
        }

        // Set listener untuk Spinner, agar hasil direset ketika pilihan diubah
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Mereset hasil setiap kali pilihan spinner berubah
                tvOutputResult.text = "0.0"
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerSource.onItemSelectedListener = spinnerListener
        spinnerTarget.onItemSelectedListener = spinnerListener
    }

    /**
     * Mengambil input, pilihan Spinner, dan menjalankan konversi.
     */
    private fun performConversion() {
        // 1. Validasi Input
        val inputStr = etInputTemp.text.toString()
        if (inputStr.isBlank()) {
            Toast.makeText(this, "Masukkan nilai suhu terlebih dahulu", Toast.LENGTH_SHORT).show()
            tvOutputResult.text = "Error"
            return
        }

        // 2. Ambil Pilihan
        val inputValue = inputStr.toDoubleOrNull()
        val sourceUnit = unitList[spinnerSource.selectedItemPosition]
        val targetUnit = unitList[spinnerTarget.selectedItemPosition]

        // Cek jika input bukan angka yang valid
        if (inputValue == null) {
            Toast.makeText(this, "Input tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        // Cek jika satuan asal sama dengan satuan tujuan
        if (sourceUnit == targetUnit) {
            val formattedResult = String.format("%.2f %s", inputValue, getUnitSymbol(targetUnit))
            tvOutputResult.text = formattedResult
            Toast.makeText(this, "Satuan asal dan tujuan sama.", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Lakukan Konversi
        try {
            val resultValue = convertTemperature(inputValue, sourceUnit, targetUnit)
            val formattedResult = String.format("%.2f %s", resultValue, getUnitSymbol(targetUnit))
            tvOutputResult.text = formattedResult
        } catch (e: Exception) {
            Toast.makeText(this, "Terjadi kesalahan dalam perhitungan.", Toast.LENGTH_SHORT).show()
            tvOutputResult.text = "Error"
        }
    }

    /**
     * Fungsi utama untuk konversi suhu.
     * Mengkonversi semua unit ke Celsius terlebih dahulu sebagai unit perantara.
     * [Image of Temperature conversion chart]
     * @param value Nilai suhu yang akan dikonversi.
     * @param sourceUnit Satuan asal (ex: "Celsius (°C)").
     * @param targetUnit Satuan tujuan (ex: "Fahrenheit (°F)").
     * @return Nilai suhu hasil konversi.
     */
    private fun convertTemperature(value: Double, sourceUnit: String, targetUnit: String): Double {
        // 1. Konversi ke basis Celsius terlebih dahulu
        val celsius: Double = when (sourceUnit) {
            unitList[0] -> value // Dari Celcius ke Celcius (tetap)
            unitList[1] -> (value - 32) * 5.0 / 9.0 // Dari Fahrenheit ke Celcius
            unitList[2] -> value - 273.15 // Dari Kelvin ke Celcius
            else -> throw IllegalArgumentException("Satuan sumber tidak valid")
        }

        // 2. Konversi dari Celsius ke unit tujuan
        return when (targetUnit) {
            unitList[0] -> celsius // Ke Celcius
            unitList[1] -> (celsius * 9.0 / 5.0) + 32 // Ke Fahrenheit
            unitList[2] -> celsius + 273.15 // Ke Kelvin
            else -> throw IllegalArgumentException("Satuan tujuan tidak valid")
        }
    }

    /**
     * Mengembalikan simbol unit yang sesuai.
     * @param unit Unit penuh (ex: "Celsius (°C)").
     * @return Simbol unit (ex: "°C").
     */
    private fun getUnitSymbol(unit: String): String {
        return when (unit) {
            unitList[0] -> "°C"
            unitList[1] -> "°F"
            unitList[2] -> "K"
            else -> ""
        }
    }
}