package com.openet.greenhup.customviews.nonswipable_view_pager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import androidx.viewpager.widget.ViewPager

class NonSwipableViewPager (context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    var mScroller: ScrollerCustomDuration? = null

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    fun postInitViewPager() {
        try {
            val scroller = ViewPager::class.java.getDeclaredField("mScroller")
            scroller.isAccessible = true
            val interpolator = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolator.isAccessible = true

            mScroller = ScrollerCustomDuration(
                context,
                interpolator.get(null) as Interpolator
            )
            scroller.set(this, mScroller)
        } catch (e: Exception) {
        }

    }

    /**
     * Set the factor by which the duration will change
     */
    fun setScrollDurationFactor(scrollFactor: Double) {
        mScroller!!.setScrollDurationFactor(scrollFactor)
    }

}
