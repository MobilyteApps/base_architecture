package com.baseappname.app.data.model.custom

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @AUTHOR Amandeep Singh
 * @date 10/08/2020
 * */
data class Tab(
    val tabFragment: Fragment, val tabName: String = "", val tabIcon: Int = 0,
    val isShowTabName: Boolean = true, val bundle: Bundle? = null
) {

    fun getFragment(): Fragment {
        if (bundle != null) {
            tabFragment.arguments = bundle
        }
        return tabFragment
    }

}

