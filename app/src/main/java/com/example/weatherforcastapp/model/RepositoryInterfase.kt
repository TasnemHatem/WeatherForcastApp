package com.example.weatherforcastapp.model

import androidx.lifecycle.LiveData

interface RepositoryInterfase {
    suspend  fun getWeather(lat: String,  lon: String, units: String, lang: String): MyRespons
    fun insert(weather: MyRespons)
    val getWeather: LiveData<MyRespons>

    fun insertFavouriteLocation(favouriteLocation: FavouriteLocation)
    val getFavourites: LiveData<List<FavouriteLocation>>

    //alerts
    fun insertAlert(alert: Alertlocal)
   val getAlert: LiveData<List<Alertlocal>>
    fun deleteAlert(alert: Alertlocal)

}