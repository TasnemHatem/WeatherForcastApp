package com.example.weatherforcastapp.alert.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.model.Alertlocal
import java.text.SimpleDateFormat

class AlertAdapter(private var communicator: AlertCommunicator) :
    RecyclerView.Adapter<AlertAdapter.ViewHolder>() {

    private var alerts: List<Alertlocal> = ArrayList()

    fun setalertsList(data: List<Alertlocal>) {
        this.alerts = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.alert_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var simpleDateFormat = SimpleDateFormat("H:mm")
        var date = simpleDateFormat.parse(alerts[position].time)
        holder.time.text = SimpleDateFormat("h:mm aa").format(date)
        holder.date.text = alerts[position].date
        holder.delete.setOnClickListener {
            communicator.deleteAlert(alerts[position])
        }
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView = itemView.findViewById(R.id.time_alert)
        var delete: ImageView = itemView.findViewById(R.id.delete_alert)
        var date: TextView = itemView.findViewById(R.id.date_alert)
    }
}