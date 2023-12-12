package com.example.flavoryapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flavoryapp.databinding.FragmentLoginBinding
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            // Handle registration or navigate to the registration screen
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login(email: String, password: String) {
        val apiUrl = "http://10.0.2.2/api/auth"

        // Use Fuel library to make a POST request with form data to the login API
        Fuel.post(apiUrl)
            .header("Content-Type" to "application/x-www-form-urlencoded")
            .body("username=$email&password=$password")
            .response { _, response, result ->
                // Handle the API response
                when (response.statusCode) {
                    200 -> {
                        // Login successful
                        saveCredentials(email, password)
                        navigateToMain()
                    }
                    else -> {
                        // Login failed
                        showToast("Gagal Login.")
                        // Add a log statement to check if this block is being reached
                        Log.e("LoginActivity", "Login failed. Status Code: ${response.statusCode}")
                    }
                }
            }
    }

    private fun saveCredentials(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
