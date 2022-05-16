package com.example.weatherforcastapp.home.view

import android.annotation.SuppressLint
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
        holder.txtDay.setText(dateFormat(current.dt,context))
        holder.txtTemp.setText("${current.temp.max.toInt()}/${current.temp.min.toInt()}")
        holder.txtStatus.setText(current.weather[0].description)


        Glide.with(context?.applicationContext!!).load("https://openweathermap.org/img/wn/${current.weather[0].icon}@2x.png").into(holder.image)
    }

    @SuppressLint("ResourceType")
    fun dateFormat(milliSeconds: Int, context: Context?):String{
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds.toLong() * 1000)
        var day=calendar.get(Calendar.DAY_OF_WEEK)

        return when (day) {
            1 -> context?.resources?.getString(R.string.sun).toString() //"Sun"
            2 -> context?.resources?.getString(R.string.mon).toString() //"Mon"
            3 -> context?.resources?.getString(R.string.tue).toString() // "Tue"
            4 -> context?.resources?.getString(R.string.wed).toString()//"Wed"
            5 -> context?.resources?.getString(R.string.thu).toString()//"Thu"
            6 -> context?.resources?.getString(R.string.fri).toString()// "Fri"
            7 -> context?.resources?.getString(R.string.sat).toString()//"Sat"
            else -> "no"
        }

    }
}