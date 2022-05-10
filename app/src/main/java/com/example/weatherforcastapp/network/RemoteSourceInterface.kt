package com.example.weatherforcastapp.network

import com.example.weatherforcastapp.model.MyRespons
import retrofit2.http.Query

interface RemoteSourceInterface {
    suspend  fun getWeather(lat: String,  lon: String, units: String, lang: String): MyRespons
}