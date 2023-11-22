package com.example.flavoryapp.data.model

import com.example.flavoryapp.uis.view.IngredientInstructionInterface

data class AnalyzedInstructions (
    var name  : String?          = null,
    var steps : ArrayList<Steps>? = arrayListOf()
): IngredientInstructionInterface
