package com.example.weatherforcastapp.network

import com.example.weatherforcastapp.model.MyRespons

class RemoteSource private constructor():RemoteSourceInterface{
    override suspend fun getWeather(lat: String,  lon: String, units: String, lang: String): MyRespons {
       // var list = emptyList<MyRespons>()
       // lat var list = MyRespons()
        lateinit var list:MyRespons
        val service = RetrofitHelper.getInstance().create(WeatherService::class.java)
        val respons = service.getWeather(lat=lat, lon=lon, units=units,lang= lang)
        if(respons.isSuccessful){
           // list = respons.body() ?: emptyList()
            list = (respons.body()?: null) as MyRespons
        }

        return list
    }

    companion object{
        private var instance: RemoteSource? = null
        fun getInstance():RemoteSource{
            return instance?: RemoteSource()
        }
    }
}