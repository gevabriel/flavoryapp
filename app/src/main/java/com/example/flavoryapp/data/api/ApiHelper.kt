package com.example.flavoryapp.data.api

import com.example.flavoryapp.data.model.RecipeData
import com.example.flavoryapp.data.model.RecipeResponseData
import com.example.flavoryapp.data.model.SearchedRecipe
import com.example.flavoryapp.data.model.SearchedRecipeData

interface ApiHelper {
    suspend fun getData(tag: String): RecipeResponseData

    suspend fun searchRecipes(query: String): SearchedRecipeData

    suspend fun getRecipeDetail(id: Int): RecipeData

    suspend fun searchRecipesByIngredients(query: String): ArrayList<SearchedRecipe>
}