package com.openet.greenhup.features.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.openet.greenhup.features.cart.CartFragment
import com.openet.greenhup.features.favorites.FavoritesFragment
import com.openet.greenhup.features.home.HomeFragment


class ViewPagerAdapter(
    private val myContext: Context,
    fm: FragmentManager,
    internal var totalTabs: Int
) : FragmentPagerAdapter(fm)
{


    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> CartFragment()
            2 -> FavoritesFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}