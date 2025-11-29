package com.byatara.aplikasipertama
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pesanan(
    val namaMenu: String,
    val hargaSatuan: Int,
    val jumlah: Int,
    val varian: String? = null, // Untuk Es/Panas (bisa null jika makanan)
    val totalHarga: Int
) : Parcelable