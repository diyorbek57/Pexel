package com.ayizor.finterest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.finterest.R
import com.ayizor.finterest.model.Photo
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class UpdatesAdapter(private val updatesList: ArrayList<Photo>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_updates, parent, false)
        return UpdatesViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UpdatesViewHolder) {
            val updates = updatesList?.get(position)
            Glide.with(context).load(updates.urls.getRegular()).into(holder.photo)

            if (updates.description.isNullOrEmpty()) {
                val text =
                    "<strong>1 pin</strong>" + "<font>" + context.getString(R.string.uploaded_by) + "«" + "<strong>" + updates.user.name + "</strong>" + "»" + "</font>"
                holder.description.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)
            } else {

                val text =
                    "<strong>" + updates.description + "</strong><font>" + context.getString(R.string.uploaded_by) + "</font>" +
                             "«" + "<strong>" + updates.user.name + "</strong>" + "»"
                holder.description.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)

            }
            val strDate = updates.created_at
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var convertedDate: Date
            try {
                convertedDate = dateFormat.parse(strDate)
                val sdfnewformat = SimpleDateFormat("MMM dd")
                val finalDateString: String = sdfnewformat.format(convertedDate)
                holder.time.text = finalDateString
            } catch (e: ParseException) {
                e.printStackTrace()
            }


        }
    }

    override fun getItemCount(): Int {
        return updatesList.size
    }

    fun addPhotos(photos: List<Photo>) {
        val lastCount = itemCount
        updatesList.addAll(photos)
        notifyItemRangeInserted(lastCount, photos.size)
    }

    class UpdatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo: ImageView = itemView.findViewById(R.id.iv_updates_image)
        val description: TextView = itemView.findViewById(R.id.tv_updates_desc)
        val time: TextView = itemView.findViewById(R.id.tv_updates_time)
    }
}