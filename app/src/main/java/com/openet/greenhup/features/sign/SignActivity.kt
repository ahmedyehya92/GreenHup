package com.openet.greenhup.features.sign

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.customviews.nonswipable_view_pager.SmartFragmentStatePagerAdapter
import com.openet.greenhup.features.login.LoginFragment
import com.openet.greenhup.features.register.RegisterFragment
import com.openet.greenhup.features.verification_sent.VerificationFragment
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : BaseActivity(), RegisterFragment.customButtonListener {

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
            val registerFragment= RegisterFragment()
            registerFragment.setCustomButtonListner(this@SignActivity)
            return when (position) {
                0 -> LoginFragment()
                1 -> registerFragment
                else ->VerificationFragment()
            }
        }

        override fun getCount(): Int {
            return 3
        }

    }

    override fun onSuccessRegister() {
        view_pager_sign.currentItem= 2
    }
}
