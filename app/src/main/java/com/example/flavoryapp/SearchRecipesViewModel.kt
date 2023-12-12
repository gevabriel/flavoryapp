package com.example.flavoryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson

class SearchRecipesViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<Recipe>>()
    val searchResults: LiveData<List<Recipe>> get() = _searchResults

    fun searchRecipes(query: String) {
        val url = "http://10.0.2.2/api/search-reseps?searchToken=$query"

        Fuel.get(url)
            .responseString { _, _, result ->
                result.fold(
                    success = { data ->
                        val gson = Gson()
                        val searchResultsList: List<Recipe> =
                            gson.fromJson(data, Array<Recipe>::class.java).toList()

                        _searchResults.postValue(searchResultsList)
                    },
                    failure = { error ->
                        println("Error: $error")
                    }
                )
            }
    }
}
