package com.example.flavoryapp.data.local.favoriteDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flavoryapp.data.model.RecipeData

@Database(entities = [RecipeData::class], version = 1)
@TypeConverters(RecipeTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}