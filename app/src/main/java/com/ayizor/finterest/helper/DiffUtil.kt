package com.ayizor.finterest.helper

import androidx.recyclerview.widget.DiffUtil
import com.ayizor.finterest.model.Photo

class DiffUtil(private var newList: List<Photo>, private var oldList: List<Photo>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].likes != newList[newItemPosition].likes -> {
                false
            }
            oldList[oldItemPosition].urls != newList[newItemPosition].urls -> {
                false
            }
            else -> true
        }
    }
}