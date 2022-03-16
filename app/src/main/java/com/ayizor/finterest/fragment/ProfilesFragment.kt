package com.ayizor.finterest.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.ProfilesAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.helper.EndlessRecyclerViewScrollListener
import com.ayizor.finterest.helper.SpacesItemDecoration
import com.ayizor.finterest.model.ResultProfiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilesFragment: Fragment() {

    companion object {
        private const val KEY_STRING = "string"
        fun newInstance(text: String): ProfilesFragment {
            val args = Bundle()
            args.putString(KEY_STRING, text)
            val newFragment = ProfilesFragment()
            newFragment.arguments = args
            return newFragment
        }
    }
    var counter = 0
    var progressBar: LottieAnimationView? = null
    var dataService: ApiInterface? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProfilesAdapter
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("@@@", "1")
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        adapter = ProfilesAdapter(requireContext())
        getSearchProfiles()
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = adapter
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
        Log.d("@@@", "2")
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.rv_search)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
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
                getSearchProfiles()
            }
        }.start()

    }
    private fun getSearchProfiles() {
        Log.d("@@@", "3")
        val text = arguments?.getString(KEY_STRING)!!
        Log.d("@@@", text)
        dataService?.getSearchProfile(currentPage++, text, 20)?.enqueue(object : Callback<ResultProfiles> {
                override fun onResponse(call: Call<ResultProfiles>, response: Response<ResultProfiles>) {
                    Log.d("@@@", "4")
                    Log.d("@@@", response.body()!!.results!!.size.toString())
                    adapter.addProfiles(response.body()!!.results!!)
                    progressBar!!.visibility = View.GONE
                }

                override fun onFailure(call: Call<ResultProfiles>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                }
            })
    }

}