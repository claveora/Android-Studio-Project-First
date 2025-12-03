package com.byatara.aplikasipertama

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        // Mengatur padding sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Inisialisasi View
        val logo = findViewById<ImageView>(R.id.profileimage)
        val brandName = findViewById<TextView>(R.id.brand_name)
        val brandSub = findViewById<TextView>(R.id.brand_sub)
        val versionText = findViewById<TextView>(R.id.tv_version)
        // Baris ini diaktifkan untuk mengambil view "Memuat..."
        val tvLoading = findViewById<TextView>(R.id.tv_loading)

        // 2. Setup Posisi Awal (Agar bisa di-animasikan masuk)
        // Geser logo ke atas layar (supaya nanti jatuh)
        logo.translationY = -1000f
        logo.alpha = 0f

        // Geser teks sedikit ke bawah
        brandName.translationY = 100f
        brandSub.translationY = 100f

        // Atur agar teks "Memuat..." awalnya transparan
        tvLoading.alpha = 0f

        // 3. Eksekusi Animasi Logo (Jatuh & Memantul)
        val logoFall = ObjectAnimator.ofFloat(logo, View.TRANSLATION_Y, 0f).apply {
            duration = 1500
            interpolator = BounceInterpolator() // Efek memantul
        }
        val logoFade = ObjectAnimator.ofFloat(logo, View.ALPHA, 1f).apply {
            duration = 500
        }

        // 4. Eksekusi Animasi Teks (Muncul dari bawah perlahan)
        val nameSlide = ObjectAnimator.ofFloat(brandName, View.TRANSLATION_Y, 0f).apply {
            duration = 1000
            interpolator = DecelerateInterpolator()
        }
        val nameFade = ObjectAnimator.ofFloat(brandName, View.ALPHA, 1f).apply {
            duration = 1000
        }

        val subSlide = ObjectAnimator.ofFloat(brandSub, View.TRANSLATION_Y, 0f).apply {
            duration = 1000
            startDelay = 200 // Sedikit delay biar muncul setelah judul
            interpolator = DecelerateInterpolator()
        }
        val subFade = ObjectAnimator.ofFloat(brandSub, View.ALPHA, 1f).apply {
            duration = 1000
            startDelay = 200
        }

        // Animasi untuk teks "Memuat..." (muncul perlahan)
        val loadingFade = ObjectAnimator.ofFloat(tvLoading, View.ALPHA, 1f).apply {
            duration = 1500
            startDelay = 1000 // Mulai muncul setelah 1 detik
        }

        // 5. Jalankan semua animasi secara berurutan/bersamaan
        val animatorSet = AnimatorSet()
        animatorSet.play(logoFall).with(logoFade) // Logo jatuh & muncul
        animatorSet.play(nameSlide).with(nameFade).after(500) // Teks muncul setelah logo mulai jatuh
        animatorSet.play(subSlide).with(subFade).with(nameSlide) // Subjudul bareng judul
        // Tambahkan animasi loading ke dalam set
        animatorSet.play(loadingFade).after(nameSlide)
        animatorSet.start()

        // 6. Pindah ke Halaman Utama setelah beberapa detik
        Handler(Looper.getMainLooper()).postDelayed({
            // Tambahkan animasi keluar (fade out) sebelum pindah activity (opsional)
            val intent = Intent(this, tugaspertama::class.java)
            startActivity(intent)
            // Transisi halus antar activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 4000) // Waktu total splash screen (4 detik agar animasi selesai dinikmati)
    }
}
