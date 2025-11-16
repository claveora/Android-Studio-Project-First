package com.byatara.aplikasipertama

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referensi ke view
        val logo = findViewById<ImageView>(R.id.profileimage)
        val brandName = findViewById<TextView>(R.id.brand_name)
        val brandSub = findViewById<TextView>(R.id.brand_sub)

        // Animasi Fade-in + Scale-up
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1000)
            .start()

        brandName.animate()
            .alpha(1f)
            .setStartDelay(800)
            .setDuration(1000)
            .start()

        brandSub.animate()
            .alpha(1f)
            .setStartDelay(1600)
            .setDuration(1000)
            .start()

        // Setelah animasi, pindah ke layout berikut
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, tugaspertama::class.java)
            startActivity(intent)
            finish()
        }, 3000) // tampil 3 detik sebelum berpindah
    }
}
