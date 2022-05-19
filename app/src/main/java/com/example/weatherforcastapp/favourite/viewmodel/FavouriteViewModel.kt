package com.example.weatherforcastapp.favourite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcastapp.model.FavouriteLocation
import com.example.weatherforcastapp.model.RepositoryInterfase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(val repository: RepositoryInterfase): ViewModel() {

    fun getFavouritefromDataBase(): LiveData<List<FavouriteLocation>> {
        return repository.getFavourites
    }
    fun deleteFavourite(favouriteLocation: FavouriteLocation){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("tasnem","in delet viewModel")
            repository.deleteFavourite(favouriteLocation)

          //  repository.getFavourites
        }
    }
}

