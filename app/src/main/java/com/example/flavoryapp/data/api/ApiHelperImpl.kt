package com.example.flavoryapp.data.api

import android.util.Log
import com.example.flavoryapp.data.model.RecipeData
import com.example.flavoryapp.data.model.RecipeResponseData
import com.example.flavoryapp.data.model.SearchedRecipe
import com.example.flavoryapp.data.model.SearchedRecipeData

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getData(tag: String): RecipeResponseData {
        Log.d("haancha", "getData: ")
        return apiService.getData(tag)
    }

    override suspend fun searchRecipes(query: String): SearchedRecipeData {
        return apiService.searchRecipes(query)
    }

    override suspend fun getRecipeDetail(id: Int): RecipeData {
        return apiService.getRecipeDetail(id)
    }

    override suspend fun searchRecipesByIngredients(query: String): ArrayList<SearchedRecipe> {
        return apiService.searchRecipesByIngredients(query)
    }
}