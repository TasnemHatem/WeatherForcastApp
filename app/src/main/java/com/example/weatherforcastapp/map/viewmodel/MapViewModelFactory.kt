package com.example.weatherforcastapp.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforcastapp.model.RepositoryInterfase
import java.lang.IllegalArgumentException

class MapViewModelFactory (val repository: RepositoryInterfase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MapViewModel::class.java)) {
            MapViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}