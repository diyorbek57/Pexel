package com.ayizor.finterest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.finterest.R
import com.ayizor.finterest.model.Pin
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto

class HomeAdapter(val pinsList: ArrayList<UnsplashPhoto>?, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pin_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
if (holder is HomeViewHolder){

}
    }

    override fun getItemCount(): Int {
        return pinsList!!.size
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}