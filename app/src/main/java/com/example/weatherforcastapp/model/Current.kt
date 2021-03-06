package com.example.weatherforcastapp.model


import com.google.gson.annotations.SerializedName

data class Current(
    var clouds: Int,
    @SerializedName("dew_point")
    var dewPoint: Double,
    var dt: Int,
    @SerializedName("feels_like")
    var feelsLike: Double,
    var humidity: Int,
    var pressure: Int,
    var sunrise: Int,
    var sunset: Int,
    var temp: Double,
    var uvi: Double,
    var visibility: Int,
    var weather: List<Weather>,
    @SerializedName("wind_deg")
    var windDeg: Int,
    @SerializedName("wind_speed")
    var windSpeed: Double
)