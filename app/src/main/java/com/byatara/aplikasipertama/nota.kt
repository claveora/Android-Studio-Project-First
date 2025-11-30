package com.byatara.aplikasipertama

import android.os.Bundle
import android.graphics.Typeface
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class nota : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nota)

        val container = findViewById<LinearLayout>(R.id.containerPesanan)
        val txtTotal = findViewById<TextView>(R.id.txtTotal)
        val btnBayar = findViewById<Button>(R.id.btnSelesai)
        // Dapatkan referensi ke TextView Nama Pemesan
        val txtNamaPemesan = findViewById<TextView>(R.id.txtNamaPemesan)

        val listPesanan = intent.getParcelableArrayListExtra<Pesanan>("DATA_PESANAN")

        // --- FOKUS: Menerima dan Menampilkan Nama Pemesan ---
        val namaPemesan = intent.getStringExtra("NAMA_PEMESAN") ?: "Anonim"
        txtNamaPemesan.text = "Pemesan: $namaPemesan"
        // ----------------------------------------------------

        var totalBayar = 0

        if (listPesanan != null) {
            for (item in listPesanan) {
                val itemView = createItemView(item)
                container.addView(itemView)

                totalBayar += item.totalHarga
            }
        }

        // --- LOGIKA FORMAT HARGA BARU (MENGGUNAKAN DecimalFormat) ---
        val localeID = Locale("in", "ID")
        val symbols = DecimalFormatSymbols(localeID)

        // Pola kustom: 'Rp.' sebagai prefix, #,##0 untuk format angka (titik sebagai ribuan, tanpa desimal)
        val customPattern = "'Rp.' #,##0"

        val decimalFormat = DecimalFormat(customPattern, symbols)

        txtTotal.text = "Total: ${decimalFormat.format(totalBayar)}"
        // -----------------------------------------------------------

        btnBayar.setOnClickListener {
            Toast.makeText(this, "Pembayaran Berhasil! Terima kasih.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    // Fungsi untuk membuat Tampilan Text per Item Pesanan
    private fun createItemView(pesanan: Pesanan): LinearLayout {
        // ... (Fungsi ini tidak berubah, tetap sama seperti sebelumnya) ...
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.weightSum = 1f
        layout.setPadding(0, 8, 0, 8)

        // Text: Nama & Varian & Qty (Kiri)
        val tvDesc = TextView(this)
        val varianText = if (pesanan.varian != "-") "(${pesanan.varian})" else ""
        tvDesc.text = "${pesanan.jumlah}x ${pesanan.namaMenu} $varianText"
        tvDesc.textSize = 16f
        tvDesc.setTextColor(resources.getColor(R.color.colorPrimaryText, null))
        val paramDesc = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f)
        tvDesc.layoutParams = paramDesc

        // Text: Harga Subtotal (Kanan)
        val tvPrice = TextView(this)

        // --- LOGIKA FORMAT HARGA BARU DI createItemView ---
        val localeID = Locale("in", "ID")
        val symbols = DecimalFormatSymbols(localeID)
        val customPattern = "'Rp.' #,##0"
        val decimalFormat = DecimalFormat(customPattern, symbols)

        tvPrice.text = decimalFormat.format(pesanan.totalHarga)
        // ----------------------------------------------------

        tvPrice.textSize = 16f
        tvPrice.typeface = Typeface.DEFAULT_BOLD
        tvPrice.gravity = Gravity.END
        tvPrice.setTextColor(resources.getColor(R.color.colorPrimaryText, null))
        val paramPrice = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f)
        tvPrice.layoutParams = paramPrice

        layout.addView(tvDesc)
        layout.addView(tvPrice)

        return layout
    }
}