package com.ayizor.finterest.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.ChatViewPagerAdapter
import com.ayizor.finterest.adapter.HistoryAdapter
import com.ayizor.finterest.helper.HistoryHelper
import com.example.pinterestappclone.fragment.ExploreFragment
import com.example.pinterestappclone.fragment.ProfilesFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SearchResultFragment(private val textParent: String?) : Fragment() {

    private lateinit var prefsManager: HistoryHelper
    private lateinit var helperAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefsManager = HistoryHelper.getInstance(requireContext())!!
        helperAdapter = HistoryAdapter(requireContext(), getHistory())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(view: View) {
        val ivBtnBack = view.findViewById<ImageView>(R.id.iv_btn_back)
        val etSearch = view.findViewById<EditText>(R.id.et_search)
        val rvHelper = view.findViewById<RecyclerView>(R.id.rv_helper)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val llContainer = view.findViewById<LinearLayout>(R.id.ll_container)
        val tlFilter = view.findViewById<TabLayout>(R.id.tl_filter)
        val vpFilter = view.findViewById<ViewPager>(R.id.vp_filter)
        rvHelper.layoutManager = LinearLayoutManager(context)

        if (textParent.isNullOrEmpty()) {
            ivBtnBack.visibility = GONE
            tvCancel.visibility = VISIBLE
            refreshAdapter(rvHelper)
            etSearch.showKeyboard()

        } else {
            hideKeyboardFrom(requireContext(), view)
            tvCancel.visibility = GONE
            ivBtnBack.visibility = VISIBLE
            etSearch.setText(textParent)
            visibleViewPager(rvHelper, llContainer, vpFilter, tlFilter, textParent)
        }
        tvCancel.setOnClickListener {
            clearFragments()
        }
        ivBtnBack.setOnClickListener {
            removeFragment(this)
        }

        etSearch.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (etSearch.text.isNotEmpty() && etSearch.text.isNotBlank()) {

                    val text = etSearch.text.toString()
                    helperAdapter.addHelper(text)
                    hideKeyboardFrom(requireContext(), view)
                    ivBtnBack.visibility = VISIBLE
                    tvCancel.visibility = GONE
                    visibleViewPager(rvHelper, llContainer, vpFilter, tlFilter, text)
                }
            }
            false
        }
    }

    private fun refreshAdapter(recyclerView: RecyclerView) {
        recyclerView.visibility = VISIBLE
        recyclerView.adapter = helperAdapter
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = parentFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment)
        ft.addToBackStack(backStateName)
        ft.commit()
    }

    private fun removeFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().remove(fragment).commit()
        parentFragmentManager.popBackStack()
    }

    private fun clearFragments() {
        val backStateName = this.javaClass.name
        parentFragmentManager.popBackStack(backStateName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun EditText.showKeyboard() {
        if (requestFocus()) {
            (activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(this, SHOW_IMPLICIT)
            setSelection(text.length)
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getHistory(): ArrayList<String> {
        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        return prefsManager.getArrayList(HistoryHelper.KEY_LIST, type)
    }

    private fun visibleViewPager(
        recyclerView: RecyclerView,
        linearLayout: LinearLayout,
        viewPager: ViewPager,
        tabLayout: TabLayout,
        text: String?
    ) {
        if (!text.isNullOrEmpty()) {

            recyclerView.visibility = GONE
            linearLayout.visibility = VISIBLE

            val pagerAdapter = ChatViewPagerAdapter(parentFragmentManager)
            pagerAdapter.addFragment(ExploreFragment(text),"Explore")
            pagerAdapter.addFragment(ProfilesFragment(text),"Profiles")
            viewPager.adapter = pagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

}