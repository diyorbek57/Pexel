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
import com.ayizor.finterest.model.User
import com.bumptech.glide.Glide

class MessagesAdapter(
    private val ideasList: ArrayList<User>?, val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_messages, parent, false)
        return MessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MessagesViewHolder) {
            val user = ideasList?.get(position)
            if (user != null) {
                Glide.with(context).load(user.profile_image.medium).into(holder.photo)
            }
            if (user != null) {
                if (user.username.isNullOrEmpty()) {
                    holder.username.text = user.name
                } else {
                    holder.username.text = user.username
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return ideasList!!.size
    }

    class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo: ImageView = itemView.findViewById(R.id.iv_messages_profile)
        val username: TextView = itemView.findViewById(R.id.tv_messages_username)
        val message: CardView = itemView.findViewById(R.id.cv_messages_message)
    }
}