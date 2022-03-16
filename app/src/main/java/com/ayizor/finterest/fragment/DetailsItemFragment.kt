package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.RelatedPhotosAdapter
import com.ayizor.finterest.api.ApiInterface
import com.ayizor.finterest.api.Client
import com.ayizor.finterest.helper.SpacesItemDecoration
import com.ayizor.finterest.model.Photo
import com.ayizor.finterest.model.RelatedPhotos
import com.ayizor.finterest.room.AppDatabase
import com.ayizor.finterest.room.model.Pin
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        tvBtnSave.setOnClickListener {
            bottomSheet()
        }
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


    @SuppressLint("QueryPermissionsNeeded")
    private fun bottomSheet() {
        val db =
            context?.let {
                Room.databaseBuilder(
                    it.applicationContext,
                    AppDatabase::class.java, "saved_pins"
                ).allowMainThreadQueries().build()
            }
        val pinDao = db?.pinDao()
        val pinsList: List<Pin> = pinDao!!.getAll()
        val sheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView: View =
            LayoutInflater.from(context).inflate(R.layout.pin_save_bottomsheet, null)
        sheetDialog.setContentView(bottomSheetView)
        sheetDialog.findViewById<LinearLayout>(R.id.ll_all_board)?.setOnClickListener {
            val pin = Pin(pinsList.size + 1, photo.id, photo.urls.getSmall(), "all")
            pinDao.insertAll(pin)
            sheetDialog.cancel()
        }
        sheetDialog.findViewById<LinearLayout>(R.id.ll_create_board)?.setOnClickListener {
            val careateBottomSheet = BottomSheetDialog(requireContext())
            val careateBottomSheetView: View =
                LayoutInflater.from(context).inflate(R.layout.create_board_bottomsheet, null)
            sheetDialog.setContentView(careateBottomSheetView)

            val create = sheetDialog.findViewById<TextView>(R.id.tv_create)
            val image = sheetDialog.findViewById<ImageView>(R.id.iv_boardpin_1)
            if (image != null) {
                Glide.with(requireContext()).load(photo.urls.getSmall()).into(image)
            }

            val editText = sheetDialog.findViewById<TextView>(R.id.et_create_board)
            editText?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (editText.text.toString().length > 0) {
                        val sdk: Int = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            create!!.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.shape_rounded_tv_btn
                                )
                            );
                        } else {
                            create!!.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.shape_rounded_tv_btn
                            );
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {


                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (editText.text.toString().length > 0) {
                        val sdk: Int = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            create!!.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.shape_rounded_tv_btn
                                )
                            );
                        } else {
                            create!!.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.shape_rounded_tv_btn
                            );
                        }
                    }

                }
            })
            if (editText != null) {
                if (editText.text.toString().isNotEmpty()) {
                    create!!.setOnClickListener {
                        val pin = Pin(
                            pinsList.size + 1,
                            photo.id,
                            photo.urls.getSmall(),
                            editText.text.toString()
                        )
                        pinDao.insertAll(pin)
                        Log.d("save", pin.toString())
                        sheetDialog.cancel()
                    }
                }
            }
            sheetDialog.show();
            sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
        }
        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
    }

    private fun relatedPhotos(id: String) {
        dataService?.getRelatedPhotos(id, 4, page)?.enqueue(object : Callback<RelatedPhotos> {
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