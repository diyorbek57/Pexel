package com.ayizor.finterest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.SavedPinsAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.room.AppDatabase
import com.ayizor.finterest.room.model.Pin

class ProfileFragment : Fragment() {


    private lateinit var adapter: SavedPinsAdapter
    lateinit var savesRecycler: RecyclerView
    var dataService: ApiInterface? = null
    var list: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        inits(view)
        return view
    }

    private fun inits(view: View) {
        savesRecycler = view.findViewById<RecyclerView>(R.id.rv_saved_photos)
        val layoutManager = GridLayoutManager(context, 2)
        savesRecycler.layoutManager = layoutManager
        adapter = SavedPinsAdapter(requireContext())
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        getSavedPinsFromRoom()


        savesRecycler.adapter = adapter
    }

    private fun getSavedPinsFromRoom() {
        val db =
            context?.let {
                Room.databaseBuilder(
                    it.applicationContext,
                    AppDatabase::class.java, "saved_pins"
                ).allowMainThreadQueries().build()
            }
        val pinDao = db?.pinDao()

        val pinsList = pinDao!!.getAll()

adapter.addPhotos(pinsList as ArrayList<Pin>)
    }


}