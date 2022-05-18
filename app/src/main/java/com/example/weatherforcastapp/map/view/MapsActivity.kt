package com.example.weatherforcastapp.map.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.location.Geocoder
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.weatherforcastapp.MainActivity
import com.example.weatherforcastapp.home.view.HomeFragment
import com.example.weatherforcastapp.local.ConcreteLocalSource
import com.example.weatherforcastapp.map.viewmodel.MapViewModel
import com.example.weatherforcastapp.map.viewmodel.MapViewModelFactory
import com.example.weatherforcastapp.model.FavouriteLocation
import com.example.weatherforcastapp.model.Repository
import com.example.weatherforcastapp.network.RemoteSource
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var mapViewModelFactory: MapViewModelFactory
    lateinit var viewModel:MapViewModel
     lateinit var city: String
    lateinit var  country: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapViewModelFactory = MapViewModelFactory(Repository.getInstance(RemoteSource.getInstance(), ConcreteLocalSource(applicationContext), this))
        viewModel = ViewModelProvider(this, mapViewModelFactory)[MapViewModel::class.java]
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener { point ->
            val geocoder = Geocoder(applicationContext, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(point.latitude, point.longitude, 1)

            if(!addresses[0].getLocality() .isNullOrEmpty()){
                city = addresses[0].getLocality()
                country = addresses[0].getCountryName()
            showConfirmDialog(point)}
        }
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun showConfirmDialog(point: LatLng ) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val builder: AlertDialog.Builder =  AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(getString(R.string.confirmation_message))
        builder.setMessage(getString(R.string.Do_You_want))
        builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
            if(sharedPreferences.getBoolean("USE_DEVICE_LOCATION",true)){
            viewModel.insertFavouriteLocation(FavouriteLocation(point.latitude.toString(),point.longitude.toString(),city))
            Toast.makeText(
                applicationContext,
                point.latitude.toString() + ", " + point.longitude+country+" ,"+city, Toast.LENGTH_SHORT).show()
                //startActivity(Intent(applicationContext,MainActivity::class.java))

               finish()
            }else{
                val intent = Intent(applicationContext,MainActivity::class.java)
//                intent.putExtra("lat",point.latitude.toString())
//                intent.putExtra("lng",point.longitude.toString())
                val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefrence", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("mapLat",point.latitude.toString())
                editor.putString("mapLng",point.longitude.toString())
                editor.commit()
                startActivity(intent)
            }
        }
        builder.setNegativeButton(android.R.string.cancel){ dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}