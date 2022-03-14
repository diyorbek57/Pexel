package com.ayizor.finterest.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.model.Topic
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class IdeasAdapter(
    private val ideasList: ArrayList<Topic>?, val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ideas, parent, false)
        return IdeasAdapter.IdeasViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IdeasAdapter.IdeasViewHolder) {
            val topic = ideasList?.get(position)
            if (topic != null) {
//                holder.shimmer.visibility = View.VISIBLE
                Glide.with(context).load(topic.cover_photo.urls.getRegular()).into(holder.photo)
            }
            holder.title.text = topic?.title
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

    class IdeasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo: ImageView = itemView.findViewById(R.id.iv_ideas_cover)
        val title: TextView = itemView.findViewById(R.id.tv_ideas_title)
//        val shimmer: LottieAnimationView = itemView.findViewById(R.id.iv_c_shimmer)
        val card: CardView = itemView.findViewById(R.id.cv_ideas)
    }
}