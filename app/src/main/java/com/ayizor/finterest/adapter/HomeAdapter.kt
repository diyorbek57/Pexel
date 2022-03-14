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
import com.ayizor.finterest.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson


class HomeAdapter(
    val pinsList: ArrayList<Photo>?,
    val context: Context,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pin_item, parent, false)
        return HomeViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeViewHolder) {
            val photo = pinsList?.get(position)
            if (photo != null) {
                holder.shimmer.visibility = View.VISIBLE
                Glide.with(context).load(photo.urls.getRegular())
                    .addListener(imageLoadingListener(holder.shimmer)).into(holder.photo)
            }

            if (photo?.description.isNullOrEmpty()) {
                if (photo != null) {
                    holder.description.text = "«" + photo.user.username + "»"
                }
            } else {
                holder.description.text = photo?.description
            }
            if (photo?.likes!! < 5) {
                holder.title.text = photo.user.bio
                if (photo.user.bio.isNullOrEmpty()) {
                    holder.title.text = photo.user.instagram_username
                }else{
                    holder.title.text = photo.user.twitter_username
                }
            }else{
                holder.title.text = "\uD83D\uDC93" + photo.likes.toString()
            }

            holder.photo.setOnClickListener {
                callDetailsFragment(position)
            }
            holder.more.setOnClickListener {
                bottomSheet()
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun bottomSheet() {
        val sheetDialog = BottomSheetDialog(context)
        val bottomSheetView: View =
            LayoutInflater.from(context).inflate(R.layout.pin_options_bottomsheet, null)
        sheetDialog.setContentView(bottomSheetView)
        val pm: PackageManager = context.getPackageManager()
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"

        val resInfo = pm.queryIntentActivities(sendIntent, 0)
        val dialogResInfo = ArrayList<ResolveInfo>()

        for (i in resInfo.indices) {
            val ri = resInfo[i]
            val packageName = ri.activityInfo.packageName
            if (packageName.contains("android.email") ||
                packageName.contains("twitter") ||
                packageName.contains("facebook") ||
                packageName.contains("telegram")
            ) {
                dialogResInfo.add(ri)

            }
        }
        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
    }

    override fun getItemCount(): Int {
        return pinsList!!.size
    }

    fun addPhotos(photos: List<Photo>) {
        val lastCount = itemCount
        pinsList?.addAll(photos)
        notifyItemRangeInserted(lastCount, photos.size)
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

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo: ImageView = itemView.findViewById(R.id.iv_pin)
        val description: TextView = itemView.findViewById(R.id.tv_desc)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val shimmer: LottieAnimationView = itemView.findViewById(R.id.iv_shimmer)
        val more: ImageView = itemView.findViewById(R.id.iv_more)


    }

    private fun callDetailsFragment(position: Int) {
        val intent = Intent(context, DetailsFragment::class.java)
        val json = Gson().toJson(pinsList)
        intent.putExtra("list", json)
        intent.putExtra("position", position)
        context.startActivity(intent)

    }


}