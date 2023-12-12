package com.example.flavoryapp

data class Recipe(
    val id: Int,
    val nama: String,
    val langkah: String,
    val bahan: String,
    val alat: String,
    val waktu: Int,
    val parent_id: Int?,
    val created_at: String?,
    val updated_at: String?,
    val user_id: String
)
