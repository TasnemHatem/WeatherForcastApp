package com.example.weatherforcastapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforcastapp.model.MyRespons

@Database(entities = [MyRespons::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO?

    companion object {
        private var instance: MyDataBase? = null
        @Synchronized
        fun getInstance(context: Context): MyDataBase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java, "weather"
                ).build()
            }
            return instance
        }
    }
}