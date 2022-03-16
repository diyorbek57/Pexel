package com.ayizor.finterest.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ayizor.finterest.R
import com.ayizor.finterest.fragment.ChatFragment
import com.ayizor.finterest.fragment.HomeFragment
import com.ayizor.finterest.fragment.IdeasFragment
import com.ayizor.finterest.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inits(savedInstanceState)
    }

    private fun inits(savedInstanceState: Bundle?) {
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigationView.setOnNavigationItemSelectedListener(navListener)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.frFragment, HomeFragment())
                .commit()
        }
    }

    @SuppressLint("NonConstantResourceId")
    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            var fragment: Fragment? = null

            when (item.itemId) {
                R.id.fragment_home -> fragment = HomeFragment()
                R.id.fragment_search -> fragment = IdeasFragment()
                R.id.fragment_message -> fragment = ChatFragment()
                R.id.fragment_profile -> fragment = ProfileFragment()
            }
            assert(fragment != null)
            if (fragment != null) {
                supportFragmentManager.beginTransaction().addToBackStack(fragment.javaClass.name)
                    .replace(R.id.frFragment, fragment!!)
                    .commit()
            }
            true
        }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 3) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}