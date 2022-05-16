package com.example.weatherforcastapp.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforcastapp.model.RepositoryInterfase
import java.lang.IllegalArgumentException

class FavouriteViewModelFactory (val repository: RepositoryInterfase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            FavouriteViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}