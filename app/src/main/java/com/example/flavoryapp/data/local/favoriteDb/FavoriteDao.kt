package com.example.flavoryapp.data.local.favoriteDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flavoryapp.data.model.RecipeData

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: RecipeData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllRecipes(recipes: List<RecipeData>)

    @Query("Select * From recipes")
    fun getAllRecipes(): List<RecipeData>

    @Query("Select * From recipes WHERE id = :recipeId")
    fun getRecipe(recipeId: Int): RecipeData?

    @Delete
    suspend fun removeRecipe(recipe: RecipeData)

}