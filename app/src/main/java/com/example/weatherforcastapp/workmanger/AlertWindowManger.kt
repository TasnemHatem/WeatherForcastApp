package com.example.weatherforcastapp.workmanger

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.databinding.AlartWindoBinding

class AlertWindowManger (
    private val context: Context, private val description: String)
{

    private var windowManager: WindowManager? = null
    private var customNotificationDialogView: View? = null
    private var binding: AlartWindoBinding? = null

    fun setMyWindowManger() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        customNotificationDialogView =
            inflater.inflate(R.layout.alart_windo, null)
        binding = AlartWindoBinding.bind(customNotificationDialogView!!)
        bindView()
        val LAYOUT_FLAG: Int
        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
        val params = WindowManager.LayoutParams(
            width,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.TRANSLUCENT
        )
        windowManager!!.addView(customNotificationDialogView, params)
    }

    private fun bindView() {
        binding?.imageIcon?.setImageResource(R.drawable.cloud)
        binding?.textDescription?.text = description
        binding?.btnOk?.text = context.getString(R.string.ok)
        binding?.btnOk?.setOnClickListener {
            close()
        }
    }

    private fun close() {
        try {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(
                customNotificationDialogView
            )
            customNotificationDialogView!!.invalidate()
            (customNotificationDialogView!!.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }



}