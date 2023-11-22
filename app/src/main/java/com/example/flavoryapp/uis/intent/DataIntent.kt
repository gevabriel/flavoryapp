package com.example.flavoryapp.uis.intent

import com.example.flavoryapp.data.model.RecipeData
import com.example.flavoryapp.data.model.SearchedRecipe

sealed class DataIntent {
    data class FetchRecipeData(
        val tag: String
    ) : DataIntent()

    data class FetchRecipeDetail (
        val recipeId: Int
    ): DataIntent()

    data class ChangeFavoriteStatus(
        val recipe: RecipeData,
        val isToFavorite: Boolean
    ) : DataIntent()

    data class FetchFavoriteRecipe(val isFromFavorite: Boolean?) : DataIntent()

    data class SearchRecipe(val query: String) : DataIntent()

    data class ChangeFavoriteStatusFromSearch(
        val recipe: SearchedRecipe,
        val isToFavorite: Boolean
    ) : DataIntent()

    data class SearchRecipesByNutrients(val query: String) : DataIntent()
}
