package com.am_development.greenhup.features.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.am_development.greenhup.R
import com.am_development.greenhup.customviews.nonswipable_view_pager.SmartFragmentStatePagerAdapter
import com.am_development.greenhup.features.login.LoginFragment
import com.am_development.greenhup.features.on_boarding.OnBoardingSlideScreenFragment
import com.am_development.greenhup.features.register.RegisterFragment
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity() {

    private val adapter = SectionsPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        view_pager_sign.adapter = adapter
        view_pager_sign.postInitViewPager()
        btn_back.visibility = View.INVISIBLE
        btn_sign_up.visibility = View.VISIBLE
        btn_back.setOnClickListener {
            view_pager_sign.currentItem= 0
            btn_back.visibility = View.INVISIBLE
            btn_sign_up.visibility = View.VISIBLE
        }

        btn_sign_up.setOnClickListener {
            view_pager_sign.currentItem= 1
            btn_back.visibility = View.VISIBLE
            btn_sign_up.visibility = View.GONE
        }
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : SmartFragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> LoginFragment()
                else -> RegisterFragment()
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }
}
