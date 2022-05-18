package com.example.weatherforcastapp.alert.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcastapp.model.Alert
import com.example.weatherforcastapp.model.Alertlocal
import com.example.weatherforcastapp.model.RepositoryInterfase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlertViewModel  (   iRepo: RepositoryInterfase,
myContext: Context
) : ViewModel() {
    private val weatherRepo: RepositoryInterfase = iRepo

    private var alertResponseMutableLiveData = MutableLiveData<List<Alert>>()
    var alertResponseLiveData: LiveData<List<Alert>> = alertResponseMutableLiveData

//    private var alertMutableLiveData = MutableLiveData<List<AlertTable>>()
//    var alertLiveData: LiveData<List<AlertTable>> = alertMutableLiveData

    private lateinit var lang: String
    private lateinit var lat: String
    private lateinit var lng: String

    private lateinit var sharedPreferences: SharedPreferences

//    init {
//        sharedPreferences =
//            myContext.getSharedPreferences(SettingViewModel.SHARED_PREF_NAME, Context.MODE_PRIVATE)
//
//        getSetting()
//    }


//    fun getSetting(): Int {
//        val language = sharedPreferences.getInt(SettingViewModel.LANGUAGE_NAME, 0)
//        lat = sharedPreferences.getString("lat", "0")!!
//        lng = sharedPreferences.getString("lng", "0")!!
//
//        lang = when (language) {
//            0 -> "en"
//            1 -> "ar"
//            else -> "en"
//        }
//
//        return sharedPreferences.getInt(NOTIFICATION_NAME, 1)
//    }


    fun insertAlertToDb(alert: Alertlocal) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepo.insertAlert(alert)
            getAlert()
        }
    }

    fun getAlert():LiveData<List<Alertlocal>> {
       return weatherRepo.getAlert
    }

    fun deleteAlert(alert: Alertlocal) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepo.deleteAlert(alert)
            getAlert()
        }
    }

}