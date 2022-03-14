package com.example.pinterestappclone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.RelatedPhotosAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.model.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment(var text: String) : Fragment() {

    private lateinit var rvSearch: RecyclerView
    private lateinit var adapter: RelatedPhotosAdapter
    private var page = 1
    var dataService: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RelatedPhotosAdapter(requireContext())
        apiSearchPhotos()
    }

    override fun onResume() {
        super.onResume()
        rvSearch.adapter = adapter
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
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        rvSearch = view.findViewById(R.id.rv_search)
        rvSearch.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvSearch.canScrollVertically(1)) {
                    apiSearchPhotos()
                }
            }
        })
    }

    private fun apiSearchPhotos() {
        dataService?.getSearchPhoto(page, text, 40)?.enqueue(object : Callback<List<Photo>> {
            override fun onResponse(
                call: Call<List<Photo>>,
                response: Response<List<Photo>>
            ) {
                page++
                adapter.addPhotos(response.body() as ArrayList<Photo>)
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
            }
        })
    }

}