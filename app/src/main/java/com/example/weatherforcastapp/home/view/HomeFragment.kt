package com.example.weatherforcastapp.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.home.viewmodel.HomeViewModel
import com.example.weatherforcastapp.home.viewmodel.HomeViewModelFactory
import com.example.weatherforcastapp.local.ConcreteLocalSource
import com.example.weatherforcastapp.model.*
import com.example.weatherforcastapp.network.RemoteSource
import com.google.android.gms.location.*
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    //hourly
    private lateinit var hourrecyclerView: RecyclerView
    private lateinit var hourList: List<Hourly>
    private lateinit var hourAdapter: HoursAdapter
   //Daily
    private lateinit var dayRecyclerView: RecyclerView
    private lateinit var dayList: List<Daily>
    private lateinit var dayAdapter: DaysAdapter

    private lateinit var viewModel: HomeViewModel
    private lateinit var homeViewModelFactory: HomeViewModelFactory
    //current
    private lateinit var txtCity:TextView
    private lateinit var txtDate:TextView
    private lateinit var txtStatus:TextView
    private lateinit var txtTemp : TextView

    //Detailed current
    private lateinit var txtPressure:TextView
    private lateinit var txtHumadity:TextView
    private lateinit var txtWind:TextView
    private lateinit var txtTCloud : TextView
    private lateinit var txtUV: TextView
    private lateinit var txtTVisabilty : TextView

    private lateinit var progressBar:ProgressBar
    private lateinit var image:CircleImageView

     var myLat:String ="30.033333"
   var myLong :String = "-90"//"31.233334"
    lateinit var language:String
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       language = Locale.getDefault().getDisplayLanguage()
        initUI(view)
        initHoursRecycler(view)
        initDaysRecycler(view)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireActivity())
       // getLastLocation()

        //// be awerrrrrrrrrrrrrrrrrr of activity !!
        homeViewModelFactory = HomeViewModelFactory(Repository.getInstance(RemoteSource.getInstance(), ConcreteLocalSource(requireActivity().applicationContext), activity?.applicationContext))
        viewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]


        if(isNetworkAvailable(requireContext())) {

            getLastLocation()

//                    viewModel.getWeather(myLat, myLong, "metric", "en")
//                        .observe(viewLifecycleOwner) { weather ->
//                            if (weather != null)
//                                updateUI(weather)
 //                       }

        }else{
            viewModel.getWeatherfromDataBase().observe(viewLifecycleOwner){weather->
                if(weather!=null) {
                    updateUI(weather)

                }
            }
        }
    }

    private fun initUI(view: View){
        txtCity = view.findViewById(R.id.city_textView)
        txtDate = view.findViewById(R.id.date_textView)
        txtStatus = view.findViewById(R.id.status_Card_textView)
        txtTemp = view.findViewById(R.id.temp_card_textView)

        txtPressure = view.findViewById(R.id.pressure_text)
        txtHumadity = view.findViewById(R.id.humidity_text)
        txtWind = view.findViewById(R.id.wind_text)
        txtTCloud = view.findViewById(R.id.cloud_text)
        txtUV = view.findViewById(R.id.uv_text)
        txtTVisabilty = view.findViewById(R.id.visibility_text)
        progressBar = view.findViewById(R.id.progress)
        image = view.findViewById(R.id.weather_image)
    }

    private fun initHoursRecycler(view: View){
        hourrecyclerView = view.findViewById(R.id.houers_recyclerview)
        hourList = ArrayList() //listOf(Hourly(2.30,30.0), Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0))
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        hourrecyclerView.layoutManager = layoutManager
        hourAdapter = HoursAdapter(activity?.applicationContext, hourList)
        hourrecyclerView.adapter = hourAdapter
    }

    private fun initDaysRecycler(view: View){
        dayRecyclerView = view.findViewById(R.id.days_recyclerview)
        dayList = ArrayList()//listOf(Daily(2,30.0), Daily(3,27.0),Daily(3,27.0),Daily(3,27.0),Daily(3,27.0),Daily(3,27.0),Daily(3,27.0))
        val layoutManager2 = LinearLayoutManager(activity?.applicationContext)
        layoutManager2.orientation = RecyclerView.VERTICAL
        dayRecyclerView.layoutManager = layoutManager2
        dayAdapter = DaysAdapter(activity?.applicationContext,dayList)
        dayRecyclerView.adapter = dayAdapter
    }

    private fun updateUI(weather:MyRespons){
        txtCity.text = weather.timezone
        txtDate.text = viewModel.timeFormat(weather.current.dt)//weather.current.dt.toString()
        txtTemp.text = weather.current.temp.toInt().toString()
        txtStatus.text = weather.current.weather[0].description

        txtPressure.text = weather.current.pressure.toString()
        txtHumadity.text = weather.current.humidity.toString()
        txtWind.text = weather.current.windSpeed.toString()
        txtTCloud.text = weather.current.clouds.toString()
        txtUV.text = weather.current.uvi.toString()
        txtTVisabilty.text = weather.current.visibility.toString()

        hourAdapter.setList(weather.hourly)
        dayAdapter.setList(weather.daily)

        viewModel.insertToDataBase(weather)
        Glide.with(context?.applicationContext!!).load("https://openweathermap.org/img/wn/${weather.current.weather[0].icon}@2x.png").into(image)

        progressBar.visibility = View.GONE
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    @SuppressLint("MissingPermission")
   private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                getLatAndLong()

            } else {

               // locationNotEnable()
                Toast.makeText(requireContext(),"Turn your location",Toast.LENGTH_LONG).show()
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            requestPremision()
        }
    }

    companion object{
     private const val PERMISSION_REQUEST_ACESS_LOCATION =100
 }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun requestPremision() {
        requestPermissions( arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                                                                     Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_ACESS_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACESS_LOCATION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()

            }else{
                requestPremision()
            }
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLatAndLong(){

       fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
            val location: Location? = task.result
            if (location == null) {
                requestNewLocationData()
               Toast.makeText(requireContext(),"no location",Toast.LENGTH_LONG).show()
            } else {
                myLat = location?.latitude.toString()
                myLong = location?.longitude.toString()
                viewModel.getWeather(myLat, myLong, "metric", language)
                    .observe(viewLifecycleOwner) { weather ->
                        if (weather != null)
                            updateUI(weather)
                    }
            }
        }

    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)

    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
           val location = locationResult.lastLocation
            myLat = location?.latitude.toString()
            myLong = location?.longitude.toString()
            viewModel.getWeather(myLat, myLong, "metric", language)
                .observe(viewLifecycleOwner) { weather ->
                    if (weather != null)
                        updateUI(weather)
                }
        }
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
    }
}