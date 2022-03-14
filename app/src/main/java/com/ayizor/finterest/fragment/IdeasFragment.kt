package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afdhal_fa.imageslider.ImageSlider
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.CreatorsIdeasAdapter
import com.ayizor.finterest.adapter.IdeasAdapter
import com.ayizor.finterest.adapter.PopularsAdapter
import com.ayizor.finterest.adapter.SliderAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.model.Photo
import com.ayizor.finterest.model.Topic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IdeasFragment : Fragment() {
    lateinit var creatorIdeasRecyclerView: RecyclerView
    lateinit var ideasRecyclerView: RecyclerView
    lateinit var popularsRecyclerView: RecyclerView
    lateinit var search: CardView
    lateinit var slider: ImageSlider
    var creatorIdeasadapter: CreatorsIdeasAdapter? = null
    var ideasadapter: IdeasAdapter? = null
    var sliderAdapter: SliderAdapter? = null
    var popularsadapter: PopularsAdapter? = null
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ideas, container, false)
        intis(view)
        return view
    }

    private fun intis(view: View) {
        creatorIdeasRecyclerView = view.findViewById(R.id.rv_ideas_from_creators)
        ideasRecyclerView = view.findViewById(R.id.rv_ideas_for_you)
        popularsRecyclerView = view.findViewById(R.id.rv_popular)
        search = view.findViewById(R.id.cv_search)
        search.setOnClickListener {
            replaceFragment(SearchResultFragment(parentFragment?.javaClass?.name))
        }
        slider = view.findViewById(R.id.imageSlide)
        val creatorIdeasLayoutManager = GridLayoutManager(
            context, 1, RecyclerView.HORIZONTAL, false
        )
        val ideaslayoutManager = GridLayoutManager(
            context, 2, RecyclerView.VERTICAL, false
        )
        val popularslayoutManager = GridLayoutManager(
            context, 2, RecyclerView.VERTICAL, false
        )
        creatorIdeasRecyclerView.layoutManager = creatorIdeasLayoutManager
        ideasRecyclerView.layoutManager = ideaslayoutManager
        popularsRecyclerView.layoutManager = popularslayoutManager


        dataService = Client.getClient()?.create(ApiInterface::class.java)
        loadCreatorsIdeas()
        loadIdeas()
        loadPopulars()
        loadSlider()
    }


    private fun loadCreatorsIdeas() {
        dataService!!.getPhotos(1, 5, "latest").enqueue(object : Callback<List<Photo>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                Log.d("CreatorIdeas:", "Photos Fetched " + (response.body()?.size))
                creatorIdeasadapter =
                    context?.let { CreatorsIdeasAdapter(response.body() as ArrayList<Photo>?, it) }
                creatorIdeasRecyclerView.adapter = creatorIdeasadapter
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.d("CreatorIdeas:", "Failure ")
            }
        })

    }

    private fun loadIdeas() {
        dataService!!.getTopics(1, 8, "latest").enqueue(object : Callback<List<Topic>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Topic>>, response: Response<List<Topic>>) {
                Log.d("Ideas:", "Photos Fetched " + (response.body()?.size))
                ideasadapter =
                    context?.let { IdeasAdapter(response.body() as ArrayList<Topic>?, it) }
                ideasRecyclerView.adapter = ideasadapter
            }

            override fun onFailure(call: Call<List<Topic>>, t: Throwable) {
                Log.d("Ideas:", "Failure ")
            }
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = parentFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment)
        ft.addToBackStack(backStateName)
        ft.commit()
    }

    private fun loadPopulars() {
        dataService!!.getTopics(1, 6, "featured").enqueue(object : Callback<List<Topic>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Topic>>, response: Response<List<Topic>>) {
                Log.d("Populars:", "Photos Fetched " + (response.body()?.size))
                popularsadapter =
                    context?.let { PopularsAdapter(response.body() as ArrayList<Topic>?, it) }
                popularsRecyclerView.adapter = popularsadapter
            }

            override fun onFailure(call: Call<List<Topic>>, t: Throwable) {
                Log.d("Populars:", "Failure ")
            }
        })

    }

    private fun loadSlider() {
        dataService!!.getTopics(1, 7, "oldest")
            .enqueue(object : Callback<List<Topic>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<Topic>>,
                    response: Response<List<Topic>>
                ) {
                    Log.d("Slider", "Photos Fetched " + (response.body()?.size))

                    var imageList: ArrayList<Topic> =
                        response.body() as ArrayList<Topic> // Create image list
                    sliderAdapter =
                        context?.let { SliderAdapter(imageList, it) }
                    slider.setImageListWithAdapter(sliderAdapter, imageList.size)
                }

                override fun onFailure(call: Call<List<Topic>>, t: Throwable) {
                    Log.d("Slider", "Failure ")
                }
            })

    }


}