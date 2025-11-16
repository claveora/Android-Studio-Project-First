package com.byatara.aplikasipertama

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.byatara.aplikasipertama.ExitConfirmationFragment
import de.hdodenhof.circleimageview.CircleImageView

class tugaspertama : AppCompatActivity(), ExitConfirmationFragment.ExitDialogListener {

    private lateinit var profileImageMain: CircleImageView

    // 🛠️ Properti CardView
    private lateinit var cardMenuList: MaterialCardView
    private lateinit var cardMenuForm: MaterialCardView
    private lateinit var cardMenuCalculator: MaterialCardView
    private lateinit var cardMenuTemperature: MaterialCardView
    private lateinit var cardMenuContact: MaterialCardView
    private lateinit var cardMenuExit: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tugaspertama)

        initViews()
        setupListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        // INISIALISASI CircleImageView
        profileImageMain = findViewById(R.id.profile_image_main)

        // 🛠️ ID CardView lainnya
        cardMenuList = findViewById(R.id.card_menu_list)
        cardMenuForm = findViewById(R.id.card_menu_form)
        cardMenuCalculator = findViewById(R.id.card_menu_calculator)
        cardMenuTemperature = findViewById(R.id.card_menu_temperature)
        cardMenuContact = findViewById(R.id.card_menu_contact)
        cardMenuExit = findViewById(R.id.card_menu_exit)
    }

    private fun setupListeners() {
        // LISTENER NAVIGASI PROFIL
        profileImageMain.setOnClickListener {
            val intent = Intent(this, Profil::class.java)
            startActivity(intent)
        }

        // Listener CardView lainnya
        cardMenuList.setOnClickListener {
            val intent = Intent(this, makanan::class.java)
            startActivity(intent)
            Toast.makeText(this, "Menu Diklik", Toast.LENGTH_SHORT).show()
        }

        cardMenuForm.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java).apply {
                putExtra("Nama", "Byatara")
                putExtra("Alamat", "Surakarta")
            }
            startActivity(intent)
            Toast.makeText(this, "Form Diklik", Toast.LENGTH_SHORT).show()
        }

        cardMenuCalculator.setOnClickListener {
            val intent = Intent(this, Kalkulator::class.java)
            startActivity(intent)
            Toast.makeText(this, "Kalkulator Diklik", Toast.LENGTH_SHORT).show()
        }

        cardMenuTemperature.setOnClickListener {
            Toast.makeText(this, "Temperatur Diklik", Toast.LENGTH_SHORT).show()
        }

        cardMenuContact.setOnClickListener {
            val emailAddress = "gitaris.byan@gmail.com"
            openEmailContact(emailAddress)
        }

        cardMenuExit.setOnClickListener {
            ExitConfirmationFragment().show(supportFragmentManager, "ExitConfirmationDialog")
        }
    }

    private fun openEmailContact(emailAddress: String) {
        try {
            // 1. Buat Intent dengan aksi ACTION_SEND
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                // Tipe MIME yang menyarankan aplikasi email (RFC822)
                type = "message/rfc822"
                // Menentukan alamat penerima
                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                // Menentukan subjek email
                putExtra(Intent.EXTRA_SUBJECT, "Pertanyaan dari Aplikasi Android Anda")
                // Menentukan isi email awal (opsional)
                putExtra(Intent.EXTRA_TEXT, "Halo, saya ingin mengajukan pertanyaan terkait aplikasi.")
            }

            // 2. Cek apakah ada aplikasi yang bisa menangani intent
            if (emailIntent.resolveActivity(packageManager) != null) {
                // 3. Tampilkan pemilih (chooser) aplikasi agar user bisa memilih aplikasi email
                startActivity(Intent.createChooser(emailIntent, "Kirim email menggunakan:"))
                Toast.makeText(this, "Membuka pilihan aplikasi", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Tidak ada aplikasi email yang ditemukan untuk mengirim pesan.", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal membuka aplikasi Email: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onExitConfirmed() {
        finish()
    }
}