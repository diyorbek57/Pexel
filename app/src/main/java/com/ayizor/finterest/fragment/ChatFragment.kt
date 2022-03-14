package com.ayizor.finterest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ayizor.finterest.R
import com.ayizor.finterest.adapter.ChatViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class ChatFragment : Fragment(){
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var options: ImageView
    lateinit var adapter: ChatViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        inits(view)
        return view
    }

    private fun inits(view: View) {
        tabLayout = view.findViewById(R.id.tl_chat)
        viewPager = view.findViewById(R.id.vp_chat)
        options = view.findViewById(R.id.iv_options)
        adapter = ChatViewPagerAdapter(childFragmentManager)
        adapter.addFragment(UpdatesFragment(), "Updates")
        adapter.addFragment(MessagesFragment(), "Messages")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 0) {
                    options.visibility = View.VISIBLE
                } else {
                    options.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 0) {
                    options.visibility = View.VISIBLE
                } else {
                    options.visibility = View.GONE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }
}