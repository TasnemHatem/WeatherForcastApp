package com.example.weatherforcastapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcastapp.model.MyRespons
import com.example.weatherforcastapp.model.RepositoryInterfase
import com.example.weatherforcastapp.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel (val repository: RepositoryInterfase): ViewModel() {
    private var _Weather = MutableLiveData<MyRespons>()
    val weather: LiveData<MyRespons> =_Weather



    fun getWeather(lat: String,  lon: String, units: String, lang: String) : LiveData<MyRespons> {
        viewModelScope.launch (Dispatchers.IO ){
            _Weather.postValue(repository.getWeather(lat, lon, units, lang))
        }
        return weather
    }

    fun timeFormat(millisSeconds:Int ): String {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis((millisSeconds * 1000).toLong())
        val format = SimpleDateFormat("hh:00 aaa")
        return format.format(calendar.time)
    }

    fun insertToDataBase(weather:MyRespons){
        viewModelScope.launch (Dispatchers.IO ){
            repository.insert(weather)
        }
    }

    fun getWeatherfromDataBase():LiveData<MyRespons>{
        return repository.getWeather
    }


}