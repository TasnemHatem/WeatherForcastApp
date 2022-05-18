package com.example.weatherforcastapp.model

import androidx.room.Entity


@Entity(tableName = "alert", primaryKeys = ["date", "time"])
data class Alertlocal(
    var date: String,
    var time: String
)
