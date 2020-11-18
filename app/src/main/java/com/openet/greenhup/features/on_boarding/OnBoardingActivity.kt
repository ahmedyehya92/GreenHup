package com.openet.greenhup.features.on_boarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.customviews.nonswipable_view_pager.ResizeAnimation
import com.openet.greenhup.customviews.nonswipable_view_pager.SmartFragmentStatePagerAdapter
import com.openet.greenhup.features.main.MainActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : BaseActivity() {

    private val adapter = SectionsPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        on_boarding_viewpager.adapter = adapter
        on_boarding_viewpager.postInitViewPager()
        on_boarding_viewpager.setScrollDurationFactor(6.0)

        btn_next.setOnClickListener {
            if(on_boarding_viewpager.currentItem == 2) {
                startActivity(Intent(this, MainActivity::class.java))
            }

            else {
                on_boarding_viewpager.currentItem += 1
                if(on_boarding_viewpager.currentItem== 2)
                    btn_next.text = getString(R.string.lets_get_started)

                if(on_boarding_viewpager.currentItem == 1)
                {
                    val resizeAnimationsmall = ResizeAnimation(cardView, 10)
                    val resizeAnimationLarge = ResizeAnimation(cardView2, 70)
                    resizeAnimationsmall.duration = 600
                    resizeAnimationLarge.duration = 600
                    cardView.startAnimation(resizeAnimationsmall)
                    cardView2.startAnimation(resizeAnimationLarge)
                } else if(on_boarding_viewpager.currentItem== 2)
                {
                    val resizeAnimationsmall = ResizeAnimation(cardView2, 10)
                    val resizeAnimationLarge = ResizeAnimation(cardView3, 70)
                    resizeAnimationsmall.duration = 600
                    resizeAnimationLarge.duration = 600
                    cardView2.startAnimation(resizeAnimationsmall)
                    cardView3.startAnimation(resizeAnimationLarge)
                } else if(on_boarding_viewpager.currentItem == 0)
                {
                    val resizeAnimationsmall = ResizeAnimation(cardView3, 10)
                    val resizeAnimationLarge = ResizeAnimation(cardView, 70)
                    resizeAnimationsmall.duration = 600
                    resizeAnimationLarge.duration = 600
                    cardView3.startAnimation(resizeAnimationsmall)
                    cardView.startAnimation(resizeAnimationLarge)
                }

            }
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : SmartFragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> OnBoardingSlideScreenFragment.newInstance(
                    getString(R.string.first_on_boarding_title),
                    getString(R.string.first_on_boarding_text),
                    R.drawable.on_boarding_1
                )
                1 -> OnBoardingSlideScreenFragment.newInstance(
                    getString(R.string.second_on_boarding_title),
                    getString(R.string.second_on_boarding_text),
                    R.drawable.on_boarding_2
                )
                else -> OnBoardingSlideScreenFragment.newInstance(
                    getString(R.string.third_on_boarding_title),
                    getString(R.string.third_on_boarding_text),
                    R.drawable.on_boarding_3
                )
            }
        }

        override fun getCount(): Int {
            return 4
        }

    }
}
