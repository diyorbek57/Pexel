package com.ayizor.finterest.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.HomeAdapter
import com.ayizor.finterest.model.Pin
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.UnsplashPickerActivity

class HomeFragment : Fragment() {
    lateinit var recycelerView: RecyclerView
    lateinit var pinsList: ArrayList<Pin>
    lateinit var adapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        inits(view)
        return view
    }

    private fun inits(view: View) {
        recycelerView = view.findViewById(R.id.rv_home)
        pinsList = ArrayList()
        recycelerView.layoutManager = GridLayoutManager(
            context, 2, RecyclerView.VERTICAL, false
        )
        startActivityForResult(
            context?.let {
                UnsplashPickerActivity.getStartingIntent(
                    it, false
                )
            }, REQUEST_CODE
        )
        refreshAdapter(loadPins())


    }

    private fun refreshAdapter(list: ArrayList<Pin>) {

    }

    private fun loadPins(): ArrayList<Pin> {
        // here we are receiving the result from the picker activity

        return pinsList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            // getting the photos
            val list: ArrayList<UnsplashPhoto>? =
                data?.getParcelableArrayListExtra(UnsplashPickerActivity.EXTRA_PHOTOS)
            // showing the preview
            adapter = context?.let { HomeAdapter(list, it) }!!
            recycelerView.adapter = adapter
            // telling the user how many have been selected
        }
    }

    companion object {
        // dummy request code to identify the request
        private const val REQUEST_CODE = 123
    }

}