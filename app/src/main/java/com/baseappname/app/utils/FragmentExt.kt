package com.baseappname.app.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.baseappname.app.base.Base_NameApplication
import com.baseappname.app.base.ViewModelFactory
/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = Base_NameApplication.apiCallMethods
    val prefManager = Base_NameApplication.prefManager
    return ViewModelFactory(repository, prefManager)
}

fun Fragment.showToast(resId: Int? = null, message: String? = null) {
    activity?.let {
        Toast.makeText(
            it, if (resId != null) {
                it.getString(resId)
            } else {
                message!!
            }, Toast.LENGTH_SHORT
        ).show()

    }

}