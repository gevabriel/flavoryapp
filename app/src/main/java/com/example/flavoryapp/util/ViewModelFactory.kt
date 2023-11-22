package com.example.flavoryapp.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flavoryapp.data.api.ApiHelper
import com.example.flavoryapp.data.repository.MainRepository
import com.binay.recipeapp.data.repository.UnitConverterRepository
import com.example.flavoryapp.uis.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val mContext: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper), mContext) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}