package com.example.weatherforcastapp.alert.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforcastapp.MainActivity
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.alert.viewmodel.AlertViewModel
import com.example.weatherforcastapp.alert.viewmodel.AlertViewModelFactory
import com.example.weatherforcastapp.local.ConcreteLocalSource
import com.example.weatherforcastapp.model.Alertlocal
import com.example.weatherforcastapp.model.Repository
import com.example.weatherforcastapp.network.RemoteSource
import com.example.weatherforcastapp.workmanger.WorkManagerAccess
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*




class AlertFragment(private var myContext: Context) : Fragment(), AlertCommunicator {
    private lateinit var alertViewModel: AlertViewModel
    private lateinit var alertViewModelFactory: AlertViewModelFactory

    private lateinit var recyclerView: RecyclerView
    private lateinit var alertAdapter: AlertAdapter

    private lateinit var addAlert: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAlert = view.findViewById(R.id.floting_addAlert_btn)
        addAlert.setOnClickListener {
            showDialog()
        }


        recyclerView = view.findViewById(R.id.alert_recyclerview)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        alertAdapter = AlertAdapter(this)
        recyclerView.adapter = alertAdapter

        alertViewModelFactory = AlertViewModelFactory(
            Repository.getInstance(RemoteSource.getInstance(), ConcreteLocalSource(myContext),myContext), myContext)
                    alertViewModel = ViewModelProvider(this, alertViewModelFactory)[AlertViewModel::class.java]

        //alertViewModel.getAlert()
        alertViewModel.getAlert().observe(viewLifecycleOwner) {
            if (it != null) {
                alertAdapter.setalertsList(it)
                alertAdapter.notifyDataSetChanged()
              //  if (alertViewModel.getSetting() == 0) {
                    val workManagerAccess: WorkManagerAccess =
                        WorkManagerAccess.getInstance(myContext)
                    workManagerAccess.setWorkManager(it)
              //  }
            }
        }

    }

    private fun showDialog() {
        val dialog = Dialog(myContext)
        dialog.setContentView(R.layout.add_alert_dialog)

        var calendar = Calendar.getInstance()
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)
        var min = calendar.get(Calendar.MINUTE)
        var hour = calendar.get(Calendar.HOUR_OF_DAY)


        var timePicker: TimePicker = dialog.findViewById(R.id.time_picker)
        timePicker.hour = hour
        timePicker.minute = min

        var datePicker: CalendarView = dialog.findViewById(R.id.calendar)
        datePicker.minDate = calendar.timeInMillis

        datePicker.setOnDateChangeListener { calendarView, i, i1, i2 ->
            day = i2
            month = i1
            year = i
        }

        var bt: Button = dialog.findViewById(R.id.set_alert)
        bt.setOnClickListener {
            var c = Calendar.getInstance()
            c.set(Calendar.DAY_OF_MONTH, day)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MINUTE, timePicker.minute)
            c.set(Calendar.HOUR_OF_DAY, timePicker.hour)

            var dateStr = SimpleDateFormat("d-MM-yyyy").format(c.time)
            var timeStr = SimpleDateFormat("H:mm").format(c.time)
            var alert = Alertlocal(dateStr, timeStr)

          //  if (alertViewModel.getSetting() == 0) {
                alertViewModel.insertAlertToDb(alert)
//            } else {
//                Toast.makeText(myContext, "Notification Disabled", Toast.LENGTH_SHORT).show()
//            }
           dialog.cancel()
        }

        dialog.show()
    }

    override fun deleteAlert(alert: Alertlocal) {
        alertViewModel.deleteAlert(alert)
    }
}