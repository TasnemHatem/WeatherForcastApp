package com.example.weatherforcastapp.local

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherforcastapp.model.MyRespons

class ConcreteLocalSource(context: Context):LocalSourceInterface{
    private val dao : WeatherDAO?
    override val getWeather: LiveData<MyRespons>

    init {
        val db: MyDataBase? = MyDataBase.getInstance(context)
        dao = db?.weatherDAO()
        getWeather = dao!!.getWeather
    }

    override fun insert(weather: MyRespons) {
        dao?.insert(weather)
    }


}