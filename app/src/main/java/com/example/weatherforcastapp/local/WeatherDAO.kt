package com.example.weatherforcastapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherforcastapp.model.FavouriteLocation

import com.example.weatherforcastapp.model.MyRespons

@Dao
interface WeatherDAO {
    @get:Query("SELECT * From weather")
    val getWeather: LiveData<MyRespons>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: MyRespons)

    @get:Query("SELECT * From favouriteLocation")
    val getFavourites: LiveData<List<FavouriteLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteLocation(favouriteLocation: FavouriteLocation)


}