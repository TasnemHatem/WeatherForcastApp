package com.example.weatherforcastapp.workmanger

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherforcastapp.MainActivity
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.local.ConcreteLocalSource
import com.example.weatherforcastapp.model.Repository
import com.example.weatherforcastapp.network.RemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class Work (var context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    var weatherRepo: Repository = Repository.getInstance(RemoteSource.getInstance(), ConcreteLocalSource(context), context)
    var alertWindowManger: AlertWindowManger? = null
    val sharedPreferencesseting = PreferenceManager.getDefaultSharedPreferences(context)
   var units = sharedPreferencesseting.getString("UNIT_SYSTEM","metric").toString()
  var  language =  sharedPreferencesseting.getString("LANGUAGE_SYSTEM","en").toString()
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefrence", Context.MODE_PRIVATE)

    var myLat = sharedPreferences.getString("lat", "30").toString()
   var myLong = sharedPreferences.getString("lng", "-90").toString()

    override suspend fun doWork(): Result {
        var response = weatherRepo.getWeather(myLat,  myLong, units, language)
        if (response?.alerts == null) {
            showNotification(" It's a good News", "The weather is good")
        } else {


            var calendar = Calendar.getInstance()

            val startDate = response!!.alerts!![0].start.toLong()
            val endDate = response!!.alerts!![0].end.toLong()

            if (checkDateIsInBetween(startDate, endDate)) {
                showNotification(
                    "Opps!! Bad News",
                    response!!.alerts!![0].event
                )
            } else {
                showNotification("Hurray!! It's a good News", "The weather is good")
            }
        }
        if (Settings.canDrawOverlays(context)) {
            GlobalScope.launch(Dispatchers.Main) {
                alertWindowManger = AlertWindowManger(context, "Hurray!! It's a good News")
                alertWindowManger!!.setMyWindowManger()
            }
        }
        return Result.success()
    }

    private fun showNotification(title: String, desc: String) {
        val notifyIntent = Intent(applicationContext, MainActivity::class.java)
        notifyIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        // Create the PendingIntent
        val notifyPendingIntent = PendingIntent.getActivity(
            applicationContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
        val soundUri = Uri.parse(
            "android.resource://" + applicationContext.packageName +
                    "/" + R.raw.sound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val name: CharSequence = "channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = desc
            channel.description = desc
            channel.setSound(
                soundUri, audioAttributes)
//                Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/raw/weathersound"),
//                audioAttributes
 //           )
            val notificationManager = applicationContext.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.cloud)
            .setContentTitle(title)
            .setContentText(desc)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true).build()
        val managerCompat = NotificationManagerCompat.from(
            applicationContext
        )
        managerCompat.notify(NOTIFY_ID, notification)
    }

    fun checkDateIsInBetween(start: Long, end: Long): Boolean {
        var flag = false

        var calendar = Calendar.getInstance()
        var timeInSec = calendar.timeInMillis / 1000

        if (timeInSec in start..end) {
            flag = true
        }

        return flag
    }

    companion object {
        private val CHANNEL_ID = "CH55"
        private val NOTIFY_ID = 1
    }
}