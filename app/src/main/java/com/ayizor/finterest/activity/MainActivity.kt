package com.ayizor.finterest.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ayizor.finterest.R
import com.ayizor.finterest.fragment.HomeFragment
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
                R.id.fragment_search -> fragment = ProfileFragment()
                R.id.fragment_message -> fragment = ProfileFragment()
            }
            assert(fragment != null)
            supportFragmentManager.beginTransaction().replace(R.id.frFragment, fragment!!)
                .commit()
            true
        }
    var doubleBackToExitPressedOnce = false


}