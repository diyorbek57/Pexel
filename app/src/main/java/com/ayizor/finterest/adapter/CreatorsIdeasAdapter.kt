package com.ayizor.finterest.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class CreatorsIdeasAdapter(val ideasList: ArrayList<Photo>?, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ideas_creators, parent, false)
        return CreatorsIdeasViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CreatorsIdeasViewHolder) {
            val photo = ideasList?.get(position)
            if (photo != null) {
                holder.shimmer.visibility = View.VISIBLE
                Glide.with(context).load(photo.urls.getRegular())
                    .addListener(imageLoadingListener(holder.shimmer)).into(holder.photo)
                Glide.with(context).load(photo.user.profile_image.small)
                    .addListener(imageLoadingListener(holder.shimmer)).into(holder.photo)

            }
            holder.likes.text = photo?.likes.toString()
        }
    }

    override fun getItemCount(): Int {
        return ideasList!!.size
    }

    fun imageLoadingListener(pendingImage: LottieAnimationView): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                //hide the animation
                pendingImage.visibility = View.GONE
                return false //let Glide handle everything else
            }
        }
    }

    class CreatorsIdeasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo: ImageView = itemView.findViewById(R.id.iv_c_ideas)
        val profile: ImageView = itemView.findViewById(R.id.iv_c_profile)
        val likes: TextView = itemView.findViewById(R.id.tv_c_ideas_like)
        val shimmer: LottieAnimationView = itemView.findViewById(R.id.iv_c_shimmer)
    }
}