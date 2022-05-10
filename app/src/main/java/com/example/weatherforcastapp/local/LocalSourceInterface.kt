package com.example.weatherforcastapp.local

import androidx.lifecycle.LiveData
import com.example.weatherforcastapp.model.MyRespons

interface LocalSourceInterface {
    fun insert(weather: MyRespons)
    val getWeather: LiveData<MyRespons>
}