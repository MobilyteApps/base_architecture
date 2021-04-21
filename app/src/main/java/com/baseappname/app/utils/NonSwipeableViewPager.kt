package com.baseappname.app.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class NonSwipeableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var isSwipeEnabled: Boolean = false
    private var isSmoothScrollingEnabled: Boolean = false

    init {
        this.isSwipeEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.isSwipeEnabled) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.isSwipeEnabled) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    fun setSwipeEnabled(isEnabled: Boolean) {
        this.isSwipeEnabled = isEnabled
    }

    fun setSmoothScrollingEnabled(isSmoothScrollingEnabled: Boolean) {
        this.isSmoothScrollingEnabled = isSmoothScrollingEnabled
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, isSmoothScrollingEnabled)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, isSmoothScrollingEnabled)
    }
}
