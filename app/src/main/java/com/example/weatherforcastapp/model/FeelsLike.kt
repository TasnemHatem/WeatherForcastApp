package com.example.weatherforcastapp.model


import com.google.gson.annotations.SerializedName

data class FeelsLike(
    var day: Double,
    var eve: Double,
    var morn: Double,
    var night: Double
)