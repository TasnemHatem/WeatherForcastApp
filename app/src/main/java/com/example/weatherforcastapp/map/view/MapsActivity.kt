package com.example.weatherforcastapp.map.view

//import android.app.AlertDialog
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
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforcastapp.home.viewmodel.HomeViewModel
import com.example.weatherforcastapp.home.viewmodel.HomeViewModelFactory
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
            showConfirmDialog(point)
        }
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun showConfirmDialog(point: LatLng) {
        val builder: AlertDialog.Builder =  AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(getString(R.string.confirmation_message))
        builder.setMessage(getString(R.string.Do_You_want))
        builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->

            val geocoder = Geocoder(applicationContext, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            //  val address: String = addresses[0].getAddressLine(0)
                val city: String = addresses[0].getLocality()
            // val state: String = addresses[0].getAdminArea()
            //  val zip: String = addresses[0].getPostalCode()
            val country: String = addresses[0].getCountryName()
            viewModel.insertFavouriteLocation(FavouriteLocation(point.latitude.toString(),point.longitude.toString(),city))
            Toast.makeText(
                applicationContext,
                point.latitude.toString() + ", " + point.longitude+country+" ,"+city, Toast.LENGTH_SHORT).show()
            finish()
            //startActivity()
        }
        builder.setNegativeButton(android.R.string.cancel){ dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}