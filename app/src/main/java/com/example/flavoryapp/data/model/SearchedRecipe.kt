package com.example.flavoryapp.data.model

data class SearchedRecipe(
    var id: Int? = null,
    var title: String? = null,
    var image: String? = null,
    var imageType: String? = null,
//    Local variable
    var isFavorite: Boolean = false
)
