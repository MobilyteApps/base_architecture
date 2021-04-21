package com.baseappname.app.utils

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 *  Extension functions for activity.
 * */
import android.app.Activity
import android.widget.Toast
import com.baseappname.app.base.Base_NameApplication
import com.baseappname.app.base.ViewModelFactory


fun Activity.getViewModelFactory(): ViewModelFactory {
    val repository = Base_NameApplication.apiCallMethods
    val prefManager = Base_NameApplication.prefManager
    return ViewModelFactory(repository, prefManager)
}


fun Activity.showToast(resId: Int? = null, message: String? = null) {

    Toast.makeText(
        this, if (resId != null) {
            this.getString(resId)
        } else {
            message!!
        }, Toast.LENGTH_SHORT
    ).show()

}
