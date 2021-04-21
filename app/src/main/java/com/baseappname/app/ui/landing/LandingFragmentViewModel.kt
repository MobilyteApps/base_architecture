package com.baseappname.app.ui.landing


import com.baseappname.app.base.BaseViewModel
import com.baseappname.app.data.local.pref.PrefManager
import com.baseappname.app.data.remote.APICallMethods


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class LandingFragmentViewModel(
    val apiCallMethods: APICallMethods, val prefManager: PrefManager
) : BaseViewModel<LandingFragmentViewActor>(apiCallMethods, prefManager) {

    //region Ui Binding Methods
    fun onClickLoginButton() {
        getViewActor().moveToLoginScreen()
    }

    fun onClickSignUpButton() {
        getViewActor().moveToSignUpScreen()
    }


    //endregion
}

