package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.ProfilesAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilesFragment(var text: String) : Fragment() {
    var dataService: ApiInterface? = null
    private lateinit var rvSearch: RecyclerView
    private lateinit var adapter: ProfilesAdapter
    private var page = 1
    private val perPage = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProfilesAdapter(requireContext())
        apiSearchProfiles()
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
        rvSearch.layoutManager = LinearLayoutManager(requireContext())
        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvSearch.canScrollVertically(1)) {
                    apiSearchProfiles()
                }
            }
        })
    }

    private fun apiSearchProfiles() {
        dataService?.getSearchProfile(page++, text, 40)
            ?.enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    adapter.addProfiles(response.body() as ArrayList<User>)
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
    }

}