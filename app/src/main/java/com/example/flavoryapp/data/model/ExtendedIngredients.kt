package com.example.flavoryapp.data.model

import com.example.flavoryapp.uis.view.IngredientInstructionInterface

data class ExtendedIngredients (
    var id           : Int?              = null,
    var name         : String?           = null,
    var original     : String?           = null
): IngredientInstructionInterface