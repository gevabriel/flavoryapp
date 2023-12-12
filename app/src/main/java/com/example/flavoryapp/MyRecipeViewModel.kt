package com.example.flavoryapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson

class MyRecipesViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    fun fetchData(context: Context) {
        // Retrieve credentials from SharedPreferences
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val password = sharedPreferences.getString("password", "")

        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            // Handle the case where credentials are not available
            return
        }

        var url = "http://10.0.2.2/api/my-reseps"
        val headers = mapOf(
            "username" to username,
            "password" to password
        )

        Fuel.get(url)
            .header(headers)
            .responseString { _, _, result ->
                result.fold(
                    success = { data ->
                        val gson = Gson()
                        val recipeList: List<Recipe> =
                            gson.fromJson(data, Array<Recipe>::class.java).toList()

                        _recipes.postValue(recipeList)
                    },
                    failure = { error ->
                        println("Error: $error")
                    }
                )
            }
    }
}

