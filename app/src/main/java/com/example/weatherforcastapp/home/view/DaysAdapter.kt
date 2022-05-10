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
import com.example.weatherforcastapp.model.Daily
import java.util.*

class DaysAdapter(var context: Context?, var dayList: List<Daily> ) : RecyclerView.Adapter<DaysAdapter.ViewHolder>(){

    fun setList(dayList: List<Daily>) {
        this.dayList = dayList
        notifyDataSetChanged()
    }

    class ViewHolder (val itemView: View): RecyclerView.ViewHolder(itemView){

        var txtDay: TextView = itemView.findViewById(R.id.txt_hour_day)
        var txtTemp: TextView =  itemView.findViewById(R.id.txt_temp_days)
        var txtStatus: TextView = itemView.findViewById(R.id.txt_status_days)

        var image: ImageView = itemView.findViewById(R.id.img_icon_days)

    }


    override fun getItemCount(): Int = dayList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.days_row,parent,false)
        return DaysAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysAdapter.ViewHolder, position: Int) {
        var current = dayList[position]
        holder.txtDay.setText(dateFormat(current.dt))
        holder.txtTemp.setText("${current.temp.max.toInt()}/${current.temp.min.toInt()}")
        holder.txtStatus.setText(current.weather[0].description)


        Glide.with(context?.applicationContext!!).load("https://openweathermap.org/img/wn/${current.weather[0].icon}@2x.png").into(holder.image)
    }

    fun dateFormat(milliSeconds: Int):String{
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds.toLong() * 1000)
       // var month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        var day=calendar.get(Calendar.DAY_OF_WEEK)
        return when (day) {
            1 ->  "Sun"
            2 ->  "Mon"
            3 ->  "Tue"
            4 ->  "Wed"
            5 ->  "Thu"
            6 ->  "Fri"
            7 ->  "Sat"
            else -> "no"
        }
       // var year=calendar.get(Calendar.YEAR).toString()
      //  return day//+"/"+month// +"/"+year

    }
}