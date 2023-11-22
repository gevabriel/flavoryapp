package com.example.flavoryapp.data.model

import com.example.flavoryapp.uis.view.IngredientInstructionInterface

data class Steps (
    var number      : Int?                   = null,
    var step        : String?                = null
): IngredientInstructionInterface
