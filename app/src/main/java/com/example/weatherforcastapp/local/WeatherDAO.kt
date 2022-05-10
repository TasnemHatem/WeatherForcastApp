package com.example.weatherforcastapp.local

import androidx.lifecycle.LiveData
import androidx.room.*

import com.example.weatherforcastapp.model.MyRespons

@Dao
interface WeatherDAO {
    @get:Query("SELECT * From weather")
    val getWeather: LiveData<MyRespons>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: MyRespons)


}