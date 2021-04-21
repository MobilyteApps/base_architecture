package com.baseappname.app.ui.landing

import com.baseappname.app.base.BaseViewActor
import com.baseappname.app.base.BaseViewModel
import com.baseappname.app.data.local.pref.PrefManager
import com.baseappname.app.data.remote.APICallMethods


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class LandingViewModel(
    apiCallMethods: APICallMethods, prefManager: PrefManager
) : BaseViewModel<BaseViewActor>(apiCallMethods, prefManager) {

}