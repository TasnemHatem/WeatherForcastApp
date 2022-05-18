package com.example.weatherforcastapp.alert.view

import com.example.weatherforcastapp.model.Alertlocal


interface AlertCommunicator {
    fun deleteAlert(alert: Alertlocal)
}