package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.RelatedPhotosAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.helper.EndlessRecyclerViewScrollListener
import com.ayizor.finterest.helper.SpacesItemDecoration
import com.ayizor.finterest.model.Explore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment : Fragment() {

    companion object {
        private const val KEY_STRING = "string"
        fun newInstance(text: String): ExploreFragment {
            val args = Bundle()
            args.putString(KEY_STRING, text)
            val newFragment = ExploreFragment()
            newFragment.arguments = args
            return newFragment
        }
    }
    var firstLoad: Boolean = true
    private var page = 1
    var counter = 0
    var progressBar: LottieAnimationView? = null
    var dataService: ApiInterface? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RelatedPhotosAdapter
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        adapter = RelatedPhotosAdapter(requireContext())
        getSearchPhotos()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        recyclerView.adapter = adapter
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.rv_search)
        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        layoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = layoutManager
        val decoration = SpacesItemDecoration(10)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(decoration)
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.d("####", totalItemsCount.toString())

                timer()
            }
        })
    }
    private fun timer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressBar!!.visibility = View.VISIBLE
                counter++
            }

            override fun onFinish() {
                getSearchPhotos()
            }
        }.start()

    }
    private fun getSearchPhotos() {
        Log.e("!!!", "1")
        val text = arguments?.getString(KEY_STRING)!!
        dataService?.getSearchPhoto(currentPage++, text, 20)?.enqueue(object : Callback<Explore> {
                override fun onResponse(call: Call<Explore>, response: Response<Explore>
                ) {
                    Log.e("!!!", response.body()!!.results!!.size.toString())
                    adapter.addPhotos(response.body()!!.results!!)
                    progressBar!!.visibility = View.GONE
                }

                override fun onFailure(call: Call<Explore>, t: Throwable) {
                    Log.e("!!!", t.message.toString())
                }
            })
    }

}