package com.example.weatherforcastapp.local

import androidx.lifecycle.LiveData
import com.example.weatherforcastapp.model.Alertlocal
import com.example.weatherforcastapp.model.FavouriteLocation
import com.example.weatherforcastapp.model.MyRespons

interface LocalSourceInterface {
    fun insert(weather: MyRespons)
    fun insertFavouriteLocation(favouriteLocation: FavouriteLocation)
    val getWeather: LiveData<MyRespons>
    val getFavourites: LiveData<List<FavouriteLocation>>


    //alerts
    fun insertAlert(alert: Alertlocal)
   val getAlert:LiveData< List<Alertlocal>>
    fun deleteAlert(alert: Alertlocal)
}