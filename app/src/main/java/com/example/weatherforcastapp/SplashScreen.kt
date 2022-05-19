package com.example.weatherforcastapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {
    var mySong: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val image:ImageView = findViewById(R.id.splashImage)
        image.alpha = 0f
        image.animate().setDuration(1000).alpha(1f).withEndAction{
            mySong= MediaPlayer.create(this,R.raw.sounds)
            mySong?.start();
            startActivity(Intent(this,MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}