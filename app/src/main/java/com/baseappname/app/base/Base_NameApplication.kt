package com.baseappname.app.base

import android.app.Application
import com.baseappname.app.data.local.pref.PrefManager
import com.baseappname.app.data.remote.APICallMethods
import com.baseappname.app.data.remote.APIHandler
import com.baseappname.app.utils.InternetUtil

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class Base_NameApplication : Application() {

    //region Companion Object
    companion object {
        private var app: Base_NameApplication? = null
        val apiCallMethods: APICallMethods get() = APIHandler.getInstance().handler
        val prefManager: PrefManager get() = PrefManager.getInstance().initPref(app!!.applicationContext)
    }
    //endregion

    override fun onCreate() {
        super.onCreate()
        app = this
        //init internet utils
        InternetUtil.init(this)
    }

}