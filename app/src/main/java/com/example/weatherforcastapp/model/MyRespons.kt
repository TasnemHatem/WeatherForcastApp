package com.example.weatherforcastapp.model


import androidx.room.*
import com.google.gson.annotations.SerializedName
@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class MyRespons(
    @TypeConverters(Converters::class)
    var current: Current,
    @TypeConverters(Converters::class)
    var daily: List<Daily>,
    @TypeConverters(Converters::class)
    var hourly: List<Hourly>,
    var lat: Double,
    var lon: Double,
    var timezone: String,
    @SerializedName("timezone_offset")
    var timezoneOffset: Int//){
   , @TypeConverters(Converters::class)
    var alerts: List<Alert>?){
    @PrimaryKey(autoGenerate = false)
    var id : Int =0
}