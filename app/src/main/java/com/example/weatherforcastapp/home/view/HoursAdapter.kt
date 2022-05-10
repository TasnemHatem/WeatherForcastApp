package com.example.weatherforcastapp.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.model.Hourly
import java.text.SimpleDateFormat
import java.util.*

class HoursAdapter(var context: Context?, var hourList: List<Hourly> ) : RecyclerView.Adapter<HoursAdapter.ViewHolder>(){

    fun setList(hourList: List<Hourly>) {
       this.hourList = hourList
        notifyDataSetChanged()
    }

    class ViewHolder (val itemView: View): RecyclerView.ViewHolder(itemView){

        var txtHoures: TextView = itemView.findViewById(R.id.txt_hour)
        var txtTemp: TextView =  itemView.findViewById(R.id.txt_temp)
        var image: ImageView = itemView.findViewById(R.id.img_icon)

    }


    override fun getItemCount(): Int = hourList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_row,parent,false)
        return HoursAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoursAdapter.ViewHolder, position: Int) {
        var current = hourList[position]
        holder.txtHoures.setText("${timeFormat(current.dt)}")
        holder.txtTemp.setText(current.temp.toInt().toString() )
        var f = current.weather[0].icon
        Glide.with(context?.applicationContext!!).load("https://openweathermap.org/img/wn/${current.weather[0].icon}@2x.png").into(holder.image)
    }

    fun timeFormat(millisSeconds:Int ): String {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis((millisSeconds * 1000).toLong())
        val format = SimpleDateFormat("hh:00 aaa")
        return format.format(calendar.time)
    }
}