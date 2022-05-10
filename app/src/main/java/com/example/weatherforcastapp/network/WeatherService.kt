package com.example.weatherforcastapp.network

import com.example.weatherforcastapp.model.MyRespons
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "640189df24a520862d556e638f470c75"

interface WeatherService {
    @GET("onecall")
    suspend  fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String,
                            @Query("units") units: String = "metric",@Query ("exclude") exclude : String = "minutely",
                              @Query("lang") lang: String = "en",
                              @Query("APPID") app_id: String = API_KEY): Response<MyRespons>

}