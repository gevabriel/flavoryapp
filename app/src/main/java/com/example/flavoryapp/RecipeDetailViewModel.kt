package com.example.flavoryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson

class RecipeDetailViewModel : ViewModel() {

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> get() = _recipe

    fun fetchRecipe(recipeId: Int) {
        val url = "http://10.0.2.2/api/get-resep"
        val params = listOf("id" to recipeId)
        val headers = mapOf(
            "username" to "aldeez",
            "password" to "Bambang512"
        )

        Fuel.get(url, params)
            .header(headers)
            .responseString { _, _, result ->
                result.fold(
                    success = { data ->
                        val gson = Gson()
                        val fetchedRecipe = gson.fromJson(data, Recipe::class.java)
                        _recipe.postValue(fetchedRecipe)
                    },
                    failure = { error ->
                        println("Error: $error")
                    }
                )
            }
    }
}
