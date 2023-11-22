package com.example.flavoryapp.uis.viewstate

import com.example.flavoryapp.data.model.RecipeData
import com.example.flavoryapp.data.model.RecipeResponseData
import com.example.flavoryapp.data.model.SearchedRecipe
import com.example.flavoryapp.data.model.SearchedRecipeData

sealed class DataState {
    object Inactive : DataState()
    object Loading : DataState()
    data class ResponseData(val recipeResponseData: RecipeResponseData) : DataState()

    data class RecipeDetail(val recipeData: RecipeData) : DataState()

    data class Error(val error: String?) : DataState()

    data class AddToFavoriteResponse(val recipe: RecipeData) : DataState()

    data class FavoriteResponse(val recipes: ArrayList<RecipeData>?) : DataState()

    data class SearchRecipes(val searchRecipeData: SearchedRecipeData) : DataState()

    data class SearchRecipesByNutrients(val searchedRecipes: ArrayList<SearchedRecipe>) : DataState()
}
