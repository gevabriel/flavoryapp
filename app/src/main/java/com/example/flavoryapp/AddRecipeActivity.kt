package com.example.flavoryapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var tilNamaResep: TextInputLayout
    private lateinit var tilTotalWaktu: TextInputLayout
    private lateinit var tilMasukBahan: TextInputLayout
    private lateinit var tilMasukAlat: TextInputLayout
    private lateinit var tilMasukLangkah: TextInputLayout
    private lateinit var btnUploadProfile: MaterialButton
    private lateinit var btnSimpan: MaterialButton

    private var selectedImage: Bitmap? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        tilNamaResep = findViewById(R.id.til_nama_resep)
        tilTotalWaktu = findViewById(R.id.til_total_waktu)
        tilMasukBahan = findViewById(R.id.til_masuk_bahan)
        tilMasukAlat = findViewById(R.id.til_masuk_alat)
        tilMasukLangkah = findViewById(R.id.til_masuk_langkah)
        btnUploadProfile = findViewById(R.id.btn_upload_profile)
        btnSimpan = findViewById(R.id.btn_simpan)

        btnUploadProfile.setOnClickListener {
            openGallery()
        }

        btnSimpan.setOnClickListener {
            uploadRecipe()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri = data.data!!
            val imageStream = contentResolver.openInputStream(imageUri)
            selectedImage = BitmapFactory.decodeStream(imageStream)
        }
    }

    private fun uploadRecipe() {
        val namaResep = tilNamaResep.editText?.text.toString()
        val totalWaktu = tilTotalWaktu.editText?.text.toString()
        val masukBahan = tilMasukBahan.editText?.text.toString()
        val masukAlat = tilMasukAlat.editText?.text.toString()
        val masukLangkah = tilMasukLangkah.editText?.text.toString()

        if (namaResep.isEmpty() || totalWaktu.isEmpty() || masukBahan.isEmpty() || masukAlat.isEmpty() || masukLangkah.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val password = sharedPreferences.getString("password", "")

        val headers = mapOf(
            "username" to username.toString(),
            "password" to password.toString()
        )


        val params = listOf(
            "nama" to namaResep,
            "waktu" to totalWaktu,
            "bahan" to masukBahan,
            "alat" to masukAlat,
            "langkah" to masukLangkah
        )

        val url = "http://10.0.2.2/api/create-resep"



        Fuel.post(url, params)
            .header(headers) // Convert Map to List<Pair<String, Any>> for Fuel library
            .response { request, response, result ->
                // Handle the response here
                // You may want to check response code and show appropriate messages to the user
                // For example:
                if (response.statusCode == 200) {
                    showToast("Uploaded")
                } else {
                    showToast("Upload Failed")
                    println("Something Wrong")
                    println(response)
                }
            }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@AddRecipeActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
