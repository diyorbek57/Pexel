package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.CreatorsIdeasAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.model.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    var adapter: CreatorsIdeasAdapter? = null
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        intis(view)
        return view
    }

    private fun intis(view: View) {
        recyclerView = view.findViewById(R.id.rv_ideas_from_creators)
        val layoutManager = GridLayoutManager(
            context, 1, RecyclerView.HORIZONTAL, false
        )
        recyclerView.layoutManager = layoutManager
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        loadCreatorsIdeas()
    }


    private fun loadCreatorsIdeas() {

        dataService!!.getPhotos(1, 5, "latest").enqueue(object : Callback<List<Photo>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                Log.d("CreatorIdeas:", "Photos Fetched " + (response.body()?.size))

                adapter =
                    context?.let { CreatorsIdeasAdapter(response.body() as ArrayList<Photo>?, it) }
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.d("CreatorIdeas:", "Failure ")
            }
        })

    }
}