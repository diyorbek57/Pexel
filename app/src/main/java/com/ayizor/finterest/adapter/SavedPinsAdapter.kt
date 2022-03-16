package com.ayizor.finterest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.finterest.R
import com.ayizor.finterest.room.model.Pin
import com.bumptech.glide.Glide

class SavedPinsAdapter(var context: Context) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = ArrayList<Pin>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("%%%", "1")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_all_pins, parent, false)
        return AllSavedPinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AllSavedPinsViewHolder) {

            holder.title.text = "All pins"
            Glide.with(context).load(list[0].pin_url).into(holder.photo_0)
            Glide.with(context).load(list[1].pin_url).into(holder.photo_1)
            Glide.with(context).load(list[2].pin_url).into(holder.photo_2)
            Glide.with(context).load(list[3].pin_url).into(holder.photo_3)
            Glide.with(context).load(list[4].pin_url).into(holder.photo_4)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photos: ArrayList<Pin>) {
        list.addAll(photos)
        notifyDataSetChanged()
    }

    class AllSavedPinsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo_0: ImageView = itemView.findViewById(R.id.iv_allpins_0)
        val photo_1: ImageView = itemView.findViewById(R.id.iv_allpins_1)
        val photo_2: ImageView = itemView.findViewById(R.id.iv_allpins_2)
        val photo_3: ImageView = itemView.findViewById(R.id.iv_allpins_3)
        val photo_4: ImageView = itemView.findViewById(R.id.iv_allpins_4)
        val title: TextView = itemView.findViewById(R.id.tv_title)


    }
}