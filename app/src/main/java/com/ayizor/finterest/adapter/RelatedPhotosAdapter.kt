package com.ayizor.finterest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.fragment.DetailsFragment
import com.ayizor.finterest.helper.HistoryHelper
import com.ayizor.finterest.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson

class RelatedPhotosAdapter(
    private var context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val prefsManager = HistoryHelper.getInstance(context)
    private var photoList = ArrayList<Photo>()

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photoList: ArrayList<Photo>) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPhotos(photoList: ArrayList<Photo>) {
        this.photoList.clear()
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pin_item, parent, false)
        return PhotoViewHolder(view)
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_pin)
        val tvDescription: TextView = view.findViewById(R.id.tv_title)
        val shimmer: LottieAnimationView = itemView.findViewById(R.id.iv_shimmer)
        val more: ImageView = view.findViewById(R.id.iv_more)
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photoItem = photoList[position]

        if (holder is PhotoViewHolder) {
            holder.tvDescription.text = photoItem.user.bio
            holder.shimmer.visibility = View.VISIBLE
            Glide.with(context).load(photoItem.urls.getRegular())
                .addListener(imageLoadingListener(holder.shimmer))
                .into(holder.ivPhoto)

            holder.ivPhoto.setOnClickListener {
                callDetailsFragment(position)
            }
            holder.more.setOnClickListener {

            }
        }

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

    private fun callDetailsFragment(position: Int) {
        val intent = Intent(context, DetailsFragment::class.java)
        val json = Gson().toJson(photoList)
        intent.putExtra("list", json)
        intent.putExtra("position", position)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }


}