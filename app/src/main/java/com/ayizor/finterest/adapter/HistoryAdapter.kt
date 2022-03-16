package com.ayizor.finterest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.finterest.R
import com.ayizor.finterest.helper.HistoryHelper

class HistoryAdapter(context: Context, var helperList: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val prefsManager = HistoryHelper.getInstance(context)

        private var itemClicked: RecyclerViewItemClick? = null

        fun addHelper(text: String) {
            if (helperList.contains(text)) helperList.remove(text)
            val newList = ArrayList<String>()
            newList.add(text)
            newList.addAll(helperList)
            helperList.clear()
            helperList.addAll(newList)
            prefsManager!!.saveArrayList(HistoryHelper.KEY_ARRAY_LIST, helperList)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun clearHelper(element: String) {
            helperList.remove(element)
            prefsManager!!.saveArrayList(HistoryHelper.KEY_ARRAY_LIST, helperList)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_helper_text, parent, false)
            return HelperTextViewHolder(view)
        }

        class HelperTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvHelp: TextView = view.findViewById(R.id.tv_history)
            val ivClear: ImageView = view.findViewById(R.id.iv_clear)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val text = helperList[position]
            if (holder is HelperTextViewHolder) {
                holder.tvHelp.text = text

                holder.ivClear.setOnClickListener {
                    clearHelper(text)
                }

                holder.tvHelp.setOnClickListener {
                    itemClicked?.onItemClick(text);
                    notifyDataSetChanged()
                }
            }
        }

        override fun getItemCount(): Int {
            return helperList.size
        }

        fun onItemClick(mclick: RecyclerViewItemClick) {
            this.itemClicked = mclick
        }

        interface RecyclerViewItemClick {
            fun onItemClick(text: String)
        }
    }