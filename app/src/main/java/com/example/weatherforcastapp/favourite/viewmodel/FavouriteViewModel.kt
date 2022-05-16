package com.example.weatherforcastapp.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforcastapp.model.FavouriteLocation
import com.example.weatherforcastapp.model.RepositoryInterfase

class FavouriteViewModel(val repository: RepositoryInterfase): ViewModel() {

    fun getFavouritefromDataBase(): LiveData<List<FavouriteLocation>> {
        return repository.getFavourites
    }
}

