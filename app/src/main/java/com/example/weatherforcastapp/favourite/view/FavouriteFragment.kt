package com.example.weatherforcastapp.favourite.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforcastapp.favourite.viewmodel.FavouriteViewModelFactory
import com.example.weatherforcastapp.home.viewmodel.HomeViewModel
import com.example.weatherforcastapp.home.viewmodel.HomeViewModelFactory
import com.example.weatherforcastapp.local.ConcreteLocalSource
import com.example.weatherforcastapp.map.view.MapsActivity
import com.example.weatherforcastapp.model.FavouriteLocation
import com.example.weatherforcastapp.model.Repository
import com.example.weatherforcastapp.network.RemoteSource
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavouriteFragment : Fragment() {

    private lateinit var favouriterecyclerView: RecyclerView
    private lateinit var favouritList: List<FavouriteLocation>
    private lateinit var favouriteAdapter: FavouriteAdapter
    lateinit  var fab: FloatingActionButton
    lateinit var  layoutManager:LinearLayoutManager

    lateinit var favouriteViewModelFactory: FavouriteViewModelFactory
    lateinit var viewModel:FavouriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        initViewMadel()
        viewModel.getFavouritefromDataBase().observe(viewLifecycleOwner) { favoruites ->
                            if (favoruites != null)
                                updateUI(favoruites)
                                   }
        fab.setOnClickListener(View.OnClickListener {
            startActivity(Intent(requireContext(), MapsActivity::class.java))
        })

    }


    fun initUI(view: View){
       fab = view.findViewById(R.id.floting_add_btn)
       favouriterecyclerView = view.findViewById(R.id.favourite_recyclerview)
       favouritList = ArrayList() //listOf(Hourly(2.30,30.0), Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0),Hourly(3.30,27.0))
       layoutManager = LinearLayoutManager(activity?.applicationContext)
       layoutManager.orientation = RecyclerView.VERTICAL
       favouriterecyclerView.layoutManager = layoutManager
        var myActivity =    activity as  OnClick
       favouriteAdapter = FavouriteAdapter(activity?.applicationContext, favouritList,myActivity)
       favouriterecyclerView.adapter = favouriteAdapter
    }

    fun initViewMadel(){
        favouriteViewModelFactory = FavouriteViewModelFactory(Repository.getInstance(RemoteSource.getInstance(), ConcreteLocalSource(requireActivity().applicationContext), activity?.applicationContext))
        viewModel = ViewModelProvider(this, favouriteViewModelFactory)[FavouriteViewModel::class.java]
    }

    private fun updateUI(favoruites: List<FavouriteLocation>) {
        favouriteAdapter.setList(favoruites)

    }




}