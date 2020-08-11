package com.am_development.greenhup.features.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.am_development.greenhup.features.cart.CartFragment
import com.am_development.greenhup.features.home.HomeFragment

class ViewPagerAdapter (private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0-> HomeFragment()
            1-> CartFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}