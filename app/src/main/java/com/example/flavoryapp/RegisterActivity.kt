package com.example.flavoryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flavoryapp.databinding.FragmentRegisterBinding
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.username.text.toString()
            val name = binding.name.text.toString()
            val password = binding.password.text.toString()

            register(username, name, password)
        }
    }

    private fun register(username: String, name: String, password: String) {
        val apiUrl = "http://10.0.2.2/api/register"

        // Use Fuel library to make a POST request with form data to the register API
        Fuel.post(apiUrl)
            .header("Content-Type" to "application/x-www-form-urlencoded")
            .body("username=$username&name=$name&password=$password")
            .response { _, response, result ->
                // Handle the API response
                when (response.statusCode) {
                    200 -> {
                        // Registration successful
                        showToast("Registration successful.")
                        navigateToLogin()
                        // You may want to navigate to the main screen or perform other actions here
                    }
                    else -> {
                        // Registration failed
                        showToast("Failed to register.")
                        // Add a log statement to check if this block is being reached
                        Log.e("RegisterActivity", "Registration failed. Status Code: ${response.statusCode}")
                    }
                }
            }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}