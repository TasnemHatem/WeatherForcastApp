package com.example.weatherforcastapp.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherforcastapp.local.LocalSourceInterface
import com.example.weatherforcastapp.network.RemoteSourceInterface

class Repository  private constructor(var remoteSourceInterface: RemoteSourceInterface,
                                      var localSourceInterface: LocalSourceInterface,
                                      var context: Context?):RepositoryInterfase{

    companion object{
        private var instance: Repository? = null
        fun getInstance(remoteSourceInterface:RemoteSourceInterface,
                        localSourceInterface: LocalSourceInterface, context: Context?): Repository {

            return instance?: Repository(remoteSourceInterface,localSourceInterface,context)
        }
    }

    override suspend fun getWeather(lat: String, lon: String, units: String, lang: String): MyRespons {
        return remoteSourceInterface.getWeather(lat,lon, units, lang)
    }

    override fun insert(weather: MyRespons) {
        localSourceInterface.insert(weather)
    }

    override val getWeather: LiveData<MyRespons>
        get() = localSourceInterface.getWeather


}