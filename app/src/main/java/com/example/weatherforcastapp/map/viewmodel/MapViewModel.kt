package com.example.weatherforcastapp.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcastapp.model.FavouriteLocation
import com.example.weatherforcastapp.model.MyRespons
import com.example.weatherforcastapp.model.RepositoryInterfase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel  (val repository: RepositoryInterfase): ViewModel() {



    fun insertFavouriteLocation(favouriteLocation: FavouriteLocation){
        viewModelScope.launch (Dispatchers.IO ){
            repository.insertFavouriteLocation(favouriteLocation)
        }
    }


}