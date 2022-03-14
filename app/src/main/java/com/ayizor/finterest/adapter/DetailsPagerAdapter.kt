package com.ayizor.finterest.adapter

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DetailsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    var fragments = ArrayList<Fragment>()
    var titles = ArrayList<String>()

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun addTitle(title: String) {
        titles.add(title)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}