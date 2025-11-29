package com.byatara.aplikasipertama
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class makanan : AppCompatActivity() {

    private val daftarPesanan = ArrayList<Pesanan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_makanan)

        val btnLanjut = findViewById<Button>(R.id.btn_lanjut)

        btnLanjut.setOnClickListener {
            daftarPesanan.clear() // Reset list sebelum mendata ulang

            // --- PROSES MINUMAN ---
            // 1. Cappucino
            prosesItem(
                "Cappucino",
                findViewById(R.id.tv_menu_price),
                findViewById(R.id.et_nominal),
                findViewById(R.id.rg_temperature)
            )

            // 2. Americano
            prosesItem(
                "Americano",
                findViewById(R.id.tv_menu_price2),
                findViewById(R.id.et_nominal2),
                findViewById(R.id.rg_temperature2)
            )

            // 3. Butterscotch
            prosesItem(
                "Butterscotch",
                findViewById(R.id.tv_menu_price3),
                findViewById(R.id.et_nominal3),
                findViewById(R.id.rg_temperature3)
            )

            // --- PROSES MAKANAN (Tanpa Suhu) ---
            // 4. Croissant
            prosesItem(
                "Croissant",
                findViewById(R.id.tv_menu_price4),
                findViewById(R.id.et_nominal4),
                null
            )

            // 5. French Toast
            prosesItem(
                "French Toast",
                findViewById(R.id.tv_menu_price5),
                findViewById(R.id.et_nominal5),
                null
            )

            // 6. Spaghetti (ID di XML tv_menu_toast1)
            prosesItem(
                "Spaghetti",
                findViewById(R.id.tv_menu_price6),
                findViewById(R.id.et_nominal6),
                null
            )

            // --- CEK DAN PINDAH HALAMAN ---
            if (daftarPesanan.isNotEmpty()) {
                val intent = Intent(this, nota::class.java)
                intent.putParcelableArrayListExtra("DATA_PESANAN", daftarPesanan)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Mohon pilih minimal satu menu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun prosesItem(nama: String, tvHarga: TextView, etJumlah: EditText, rgSuhu: RadioGroup?) {
        val jumlahStr = etJumlah.text.toString()

        if (jumlahStr.isNotEmpty()) {
            val jumlah = jumlahStr.toInt()
            if (jumlah > 0) {
                // Parse harga: "Rp. 17.000" -> ambil angka saja -> 17000
                val hargaStr = tvHarga.text.toString().replace("[^0-9]".toRegex(), "")
                val harga = hargaStr.toInt()

                var varian: String? = "-"

                // Cek jika ada pilihan suhu (Minuman)
                if (rgSuhu != null) {
                    val selectedId = rgSuhu.checkedRadioButtonId
                    if (selectedId != -1) {
                        val radioButton = findViewById<RadioButton>(selectedId)
                        varian = radioButton.text.toString()
                    } else {
                        // Default jika user lupa pilih suhu
                        varian = "Panas"
                    }
                }

                val subtotal = harga * jumlah
                daftarPesanan.add(Pesanan(nama, harga, jumlah, varian, subtotal))
            }
        }
    }
}