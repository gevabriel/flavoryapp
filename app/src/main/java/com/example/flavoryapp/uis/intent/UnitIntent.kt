package com.example.flavoryapp.uis.intent

sealed class UnitIntent {
    data class ConvertUnit(
        val toConvertFrom: String,
        val toConvertInto: String,
        val amount: String
    ) :
        UnitIntent()
}
