package com.example.weatherforcastapp.alert.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforcastapp.model.RepositoryInterfase

class AlertViewModelFactory (
    private val _irepo: RepositoryInterfase,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            AlertViewModel(_irepo,context) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }


}