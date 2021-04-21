package com.baseappname.app.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.baseappname.app.data.model.custom.Tab

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class BaseTabsAdapter(private val fm: FragmentManager, private val tabsList: List<Tab>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = tabsList[position].getFragment()

    override fun getCount(): Int = tabsList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return if (tabsList[position].isShowTabName) tabsList[position].tabName else ""
    }
}
