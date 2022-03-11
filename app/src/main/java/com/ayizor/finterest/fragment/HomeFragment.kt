package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
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
import com.google.android.material.appbar.AppBarLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    var adapter: HomeAdapter? = null
    private var loading = true
    private var page = 1
    var counter = 0
    var searchLayout: FrameLayout? = null
    var appBarLayout: AppBarLayout? = null
    var searchBar: EditText? = null
    var pastVisiblesItems = 0
    var totalItemCount = 0
    var visibleItemCount = 0
    var progressBar: LottieAnimationView? = null
    var dataService: ApiInterface? = null
    var firstLoad: Boolean = false

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
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS;
        recyclerView.layoutManager = layoutManager
        val decoration = SpacesItemDecoration(10)

        recyclerView.addItemDecoration(decoration)
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        adapter = context?.let { HomeAdapter(ArrayList<Photo>(), it) }
        recyclerView.adapter=adapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.d("####", totalItemsCount.toString())
                timer()
            }
        })
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0) { //check for scroll down
//                    visibleItemCount = layoutManager.childCount
//                    totalItemCount = layoutManager.itemCount
//                    var firstVisibleItems: IntArray? = null
//                    firstVisibleItems =
//                        layoutManager.findFirstVisibleItemPositions(firstVisibleItems)
//                    if (firstVisibleItems != null && firstVisibleItems.size > 0) {
//                        pastVisiblesItems = firstVisibleItems[0]
//                    }
//
//                    if (loading) {
//                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
//                            loading = false
//                            Log.v("...", "Last Item Wow !")
//                            loadPhotos()
//                            loading = true
//                        }
//                    }
//                }
//            }
//        })

        if (!firstLoad)
            loadPhotos()


    }

    private fun timer() {
        object : CountDownTimer(3000, 1000) {
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
                firstLoad = true
                Log.d("Photos", "Photos Fetched " + (response.body()?.size))
                page++;
                response.body()?.let { adapter?.addPhotos(it) }

                progressBar!!.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.d("Photos", "Failure ")
                progressBar!!.visibility = View.GONE
            }
        })

    }
}