package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.MessagesAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    var adapter: MessagesAdapter? = null
    var progressBar: LottieAnimationView? = null
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        inits(view)
        return view
    }

    private fun inits(view: View) {
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.rv_messages)
        val layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        recyclerView.layoutManager = layoutManager
//        adapter = context?.let { MessagesAdapter(loadMessages(), it) }
//        recyclerView.adapter = adapter

    }

//    private fun loadMessages(): ArrayList<User>? {
//        progressBar!!.visibility = View.VISIBLE
//        var usersList: ArrayList<User>? = null
//        dataService!!.getUsers().enqueue(object : Callback<List<User>> {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                Log.d("users", response.body()?.size.toString())
//                usersList = response.body() as ArrayList<User>?
//                progressBar!!.visibility = View.GONE
//            }
//
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                Log.d("users", "fail")
//                progressBar!!.visibility = View.GONE
//            }
//        })
//        return usersList
//    }
}