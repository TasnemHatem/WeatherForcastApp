package com.example.weatherforcastapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteLocation")
data class FavouriteLocation( var lat: String,var lng: String,@PrimaryKey var name:String)