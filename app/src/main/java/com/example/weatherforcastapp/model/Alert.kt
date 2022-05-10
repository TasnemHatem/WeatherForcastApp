package com.example.weatherforcastapp.model

data class Alert(
    var sender_name: String,
    var event: String,
    var start: Int,
    var end: Int,
    var description: String,
    var tags: List<String>
)

