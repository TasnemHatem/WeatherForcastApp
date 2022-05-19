package com.example.weatherforcastapp.favourite.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforcastapp.MainActivity
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.map.view.MapsActivity
import com.example.weatherforcastapp.model.FavouriteLocation

class FavouriteAdapter(var context: Context?, var favouritList: List<FavouriteLocation>,var listner :OnClick) : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>(){
   var onClickFavourite = FavouriteFragment() as OnClickFavourite
    fun setList(favouritList: List<FavouriteLocation>) {
        this.favouritList = favouritList
        notifyDataSetChanged()
    }

    class ViewHolder (val itemView: View): RecyclerView.ViewHolder(itemView){
        var constraintlayout :ConstraintLayout = itemView.findViewById(R.id.layout_favourit)
        var txtFavouriteName: TextView = itemView.findViewById(R.id.favourit_text_name)
        var imageView: ImageButton = itemView.findViewById(R.id.delete_favourite)
    }


    override fun getItemCount(): Int = favouritList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_row,parent,false)
        return FavouriteAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.ViewHolder, position: Int) {
        var current = favouritList[position]

        holder.txtFavouriteName.setText(current.name)
        holder.constraintlayout.setOnClickListener { listner.onClickCityView(current)}
            holder.imageView.setOnClickListener{
                Log.i("tasnem","in delet adapter")
                onClickFavourite.deleteFavourite(current)}
            //listner.deleteFavourite(current)}





    }
}