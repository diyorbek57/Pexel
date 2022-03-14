package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.HomeAdapter
import com.ayizor.finterest.adapter.UpdatesAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.helper.EndlessRecyclerViewScrollListener
import com.ayizor.finterest.model.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatesFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    var adapter: UpdatesAdapter? = null
    var progressBar: LottieAnimationView? = null
    var dataService: ApiInterface? = null

    private var page = 1
    var counter = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_updates, container, false)
        inits(view)
        return view
    }

    private fun inits(view: View) {
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.rv_updates)
        val layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        recyclerView.layoutManager = layoutManager
        adapter = context?.let { UpdatesAdapter(ArrayList<Photo>(), it) }
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.d("####", totalItemsCount.toString())
                timer()
            }
        })


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

        dataService!!.getPhotos(page, 50, "latest").enqueue(object : Callback<List<Photo>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {

                page++;
                response.body()?.let { adapter?.addPhotos(it) }
                progressBar!!.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                progressBar!!.visibility = View.GONE
            }
        })

    }
}