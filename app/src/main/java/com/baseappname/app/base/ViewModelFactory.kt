package com.baseappname.app.base


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baseappname.app.data.local.pref.PrefManager
import com.baseappname.app.data.remote.APICallMethods
import com.baseappname.app.ui.landing.LandingFragmentViewModel
import com.baseappname.app.ui.landing.LandingViewModel
import com.baseappname.app.ui.splash.SplashViewModel

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val apiCallMethods: APICallMethods,
    private val prefManager: PrefManager,

    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SplashViewModel::class.java) ->
                    SplashViewModel(apiCallMethods, prefManager)
                isAssignableFrom(LandingViewModel::class.java) ->
                    LandingViewModel(apiCallMethods, prefManager)
                isAssignableFrom(LandingFragmentViewModel::class.java) ->
                    LandingFragmentViewModel(apiCallMethods, prefManager)
//                    isAssignableFrom(SplashViewModel::class.java) ->
//                    SplashViewModel(apiCallMethods, prefManager)
//                isAssignableFrom(SplashViewModel::class.java) ->
//                    SplashViewModel(apiCallMethods, prefManager)
//                isAssignableFrom(SplashViewModel::class.java) ->
//                    SplashViewModel(apiCallMethods, prefManager)
//                isAssignableFrom(SplashViewModel::class.java) ->
//                    SplashViewModel(apiCallMethods, prefManager)
//                isAssignableFrom(SplashViewModel::class.java) ->
//                    SplashViewModel(apiCallMethods, prefManager)
//                isAssignableFrom(SplashViewModel::class.java) ->
//                    SplashViewModel(apiCallMethods, prefManager)


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}