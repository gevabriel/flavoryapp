package com.example.flavoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // Waktu tampilan splash screen (dalam milidetik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay untuk menampilkan splash screen selama beberapa detik
        Handler().postDelayed({
            // Setelah waktu tampilan splash screen selesai, pindah ke activity lain
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Tutup SplashActivity agar tidak bisa kembali saat menekan tombol back
            finish()
        }, SPLASH_TIME_OUT)
    }
}
