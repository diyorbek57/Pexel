package com.ayizor.finterest.fragment

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.DetailsPagerAdapter
import com.ayizor.finterest.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DetailsFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_details)
        Log.d("fff","0")
        inits()
    }


    private fun inits() {
        Log.d("fff","1")
        val vpDetails =findViewById<ViewPager>(R.id.vp_details)
        refreshAdapter(vpDetails, getList(), getPosition())
    }

    private fun refreshAdapter(viewPager: ViewPager, photoList: ArrayList<Photo>, position: Int) {
        Log.d("fff","2")
        val adapter = DetailsPagerAdapter(supportFragmentManager)
        for (photo in photoList) {
            adapter.addFragment(DetailsItemFragment(photo))
        }
        viewPager.adapter = adapter
        viewPager.currentItem = position
    }

    private fun getList(): ArrayList<Photo> {
        val json = intent.getStringExtra("list")
        val type: Type = object : TypeToken<ArrayList<Photo>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun getPosition(): Int {
        return intent.getIntExtra("position", 0)
    }


}