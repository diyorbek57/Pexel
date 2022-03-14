package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.HomeAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.helper.EndlessRecyclerViewScrollListener
import com.ayizor.finterest.helper.SpacesItemDecoration
import com.ayizor.finterest.model.Photo
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    var adapter: HomeAdapter? = null
    var progressBar: LottieAnimationView? = null
    var dataService: ApiInterface? = null
    var firstLoad: Boolean = true
    private var page = 1
    var counter = 0
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
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.rv_home)
        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        layoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = layoutManager
        val decoration = SpacesItemDecoration(10)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(decoration)
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        adapter = HomeAdapter(ArrayList<Photo>(), requireContext())
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.d("####", totalItemsCount.toString())

                timer()
            }
        })

        if (firstLoad)
            loadPhotos()


    }

    private fun timer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressBar!!.visibility = View.VISIBLE
                counter++
            }

            override fun onFinish() {
                loadPhotos()
            }
        }.start()

    }

    private fun loadPhotos() {

        dataService!!.getPhotos(page, 30, "latest").enqueue(object : Callback<List<Photo>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                firstLoad = false
                Log.d("Photos", "Photos Fetched " + (response.body()?.size))
                page++;
                response.body()?.let { adapter?.addPhotos(it) }
                progressBar!!.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                t.message?.let { Log.d("Photos", it) }
                progressBar!!.visibility = View.GONE
            }
        })

    }





}