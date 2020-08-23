package com.am_development.greenhup.features.on_boarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.am_development.greenhup.R
import com.am_development.greenhup.customviews.nonswipable_view_pager.ResizeAnimation
import com.am_development.greenhup.customviews.nonswipable_view_pager.SmartFragmentStatePagerAdapter
import com.am_development.greenhup.features.main.MainActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

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
                    btn_next.text = "Let's Get Started"

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
                    "Watering without worry",
                    "you can set your schedule watering plant\n" +
                            "properly and can exchange schedule\n" +
                            "automatically if come a bad climate",
                    R.drawable.on_boarding_1
                )
                1 -> OnBoardingSlideScreenFragment.newInstance(
                    "Get Information About Your Plants",
                    "you can scan information of your plant\n" +
                            "or a pest that harm your plant and\n" +
                            "get information how to take care\n" +
                            "that problems.",
                    R.drawable.on_boarding_2
                )
                else -> OnBoardingSlideScreenFragment.newInstance(
                    "Build & Share with Community",
                    "you can scan information of your plant\n" +
                            "or a pest that harm your plant and\n" +
                            "get information how to take care\n" +
                            "that problems.",
                    R.drawable.on_boarding_3
                )
            }
        }

        override fun getCount(): Int {
            return 4
        }

    }
}
