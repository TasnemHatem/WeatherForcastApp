package com.example.weatherforcastapp.local

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherforcastapp.model.FavouriteLocation
import com.example.weatherforcastapp.model.MyRespons

class ConcreteLocalSource(context: Context):LocalSourceInterface{


    private val dao : WeatherDAO?
    init {
        val db: MyDataBase? = MyDataBase.getInstance(context)
        dao = db?.weatherDAO()
    }
    override val getWeather: LiveData<MyRespons>

    init {

        getWeather = dao!!.getWeather
    }

    override val getFavourites: LiveData<List<FavouriteLocation>>
    init {
//        val db: MyDataBase? = MyDataBase.getInstance(context)
//        dao = db?.weatherDAO()
        getFavourites = dao!!.getFavourites
    }
       // get() = TODO("Not yet implemented")

    override fun insert(weather: MyRespons) {
        dao?.insert(weather)
    }

    override fun insertFavouriteLocation(favouriteLocation: FavouriteLocation) {
        dao?.insertFavouriteLocation(favouriteLocation)
    }


}