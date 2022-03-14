package com.ayizor.finterest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.finterest.R
import com.ayizor.finterest.model.Topic
import com.bumptech.glide.Glide

class SliderAdapter(var items: List<Topic>, var context: Context) :
    RecyclerView.Adapter<SliderAdapter.VHolder>() {


    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.imageSlide)
        val title: TextView = itemView.findViewById(R.id.textTitle)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        if (holder is VHolder) {
            val topic = items[position]
            Glide.with(context).load(topic.cover_photo.urls.getRegular()).into(holder.photo)
            holder.title.text = topic?.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
    )
}