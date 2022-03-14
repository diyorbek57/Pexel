package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.RelatedPhotosAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.helper.SpacesItemDecoration
import com.ayizor.finterest.model.Photo
import com.ayizor.finterest.model.RelatedPhotos
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsItemFragment(var photo: Photo) : Fragment() {

    private lateinit var adapter: RelatedPhotosAdapter
    private lateinit var tvRelated: TextView
    lateinit var relatedRecycler: RecyclerView
    private var page = 1
    var dataService: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details_item, container, false)
        initViews(view)
        return view
    }

    @SuppressLint("SetTextI18n", "Range", "CutPasteId")
    private fun initViews(view: View) {
        val backButton = view.findViewById<ImageView>(R.id.iv_btn_back)
        val moreButton = view.findViewById<ImageView>(R.id.iv_btn_more)
        val mainPhoto = view.findViewById<ImageView>(R.id.iv_photo)
        val profile = view.findViewById<ImageView>(R.id.iv_profile)
        val fullName = view.findViewById<TextView>(R.id.tv_fullName)
        val followers = view.findViewById<TextView>(R.id.tv_followers)
        val followButton = view.findViewById<TextView>(R.id.tv_btn_follow)
        val title = view.findViewById<TextView>(R.id.tv_title)
        val tvDescription = view.findViewById<TextView>(R.id.tv_description)
        val ivBtnComment = view.findViewById<ImageView>(R.id.iv_btn_comment)
        val tvBtnVisit = view.findViewById<TextView>(R.id.tv_btn_visit)
        val tvBtnSave = view.findViewById<TextView>(R.id.tv_btn_save)
        val ivBtnShare = view.findViewById<ImageView>(R.id.iv_btn_share)
        val tvIntro = view.findViewById<TextView>(R.id.tv_intro)
        val ivProfileMe = view.findViewById<ImageView>(R.id.iv_profile_me)
        tvRelated = view.findViewById(R.id.tv_related)
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        relatedRecycler = view.findViewById<RecyclerView>(R.id.rv_details)
        relatedRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = context?.let { RelatedPhotosAdapter(it) }!!
        relatedRecycler.adapter = adapter
        val decoration = SpacesItemDecoration(10)
        relatedRecycler.setHasFixedSize(true)
        relatedRecycler.addItemDecoration(decoration)
        val description = photo.description
        val username = photo.user.name

        val thumbnailRequest: RequestBuilder<Drawable> = Glide
            .with(requireContext())
            .load(photo.urls.getThumb())
        Glide
            .with(requireContext())
            .load(photo.urls.getFull())
            .thumbnail(thumbnailRequest).placeholder(ColorDrawable(Color.parseColor(photo.color)))
            .into(mainPhoto)
        Glide.with(requireContext()).load(photo.user.profile_image.large)
            .placeholder(ColorDrawable(Color.parseColor(photo.color))).into(profile)

        fullName.text = photo.user.name
        followers.text = "${photo.user.total_photos} Followers"
        tvDescription.text = setDescription(description, username)

//        Glide.with(requireContext()).load(profileMe)
//            .placeholder(ColorDrawable(Color.parseColor(photo.color))).into(ivProfileMe)
        relatedPhotos(photo.id)
        backButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun relatedPhotos(id: String) {
        dataService?.getRelatedPhotos(id, 50, page)?.enqueue(object : Callback<RelatedPhotos> {
            override fun onResponse(call: Call<RelatedPhotos>, response: Response<RelatedPhotos>) {
                val photosList: ArrayList<Photo>? = response.body()?.results
                if (photosList != null) {
                    Log.d("RelatedPhotos", photosList.size.toString())
                }

                if (photosList?.size!! > 0) {
                    adapter.addPhotos(photosList)
                    relatedRecycler.adapter = adapter
                    page++;
                } else {
                    tvRelated.text = getString(R.string.no_similar_images_found)
                }

            }

            override fun onFailure(call: Call<RelatedPhotos>, t: Throwable) {
                Log.e("RelatedPhotos", t.message.toString())
            }
        })
    }

    private fun setDescription(description: String?, username: String?): String {
        return when {
            description != null -> description.toString()
            username != null -> username.toString()
            else -> "Photo was made by $username"
        }
    }
}