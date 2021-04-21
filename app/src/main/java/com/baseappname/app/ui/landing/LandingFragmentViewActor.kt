package com.baseappname.app.ui.landing

import com.baseappname.app.base.BaseViewActor

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
interface LandingFragmentViewActor : BaseViewActor {
    /**
     * Move to login screen
     *
     */
    fun moveToLoginScreen()

    /**
     * Move to sign up screen
     *
     */
    fun moveToSignUpScreen()

}