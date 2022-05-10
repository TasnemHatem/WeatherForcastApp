package com.example.weatherforcastapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforcastapp.model.RepositoryInterfase
import java.lang.IllegalArgumentException

class HomeViewModelFactory (val repository: RepositoryInterfase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}