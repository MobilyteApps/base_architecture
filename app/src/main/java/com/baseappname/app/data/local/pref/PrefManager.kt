package com.baseappname.app.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.baseappname.app.data.local.pref.SharedPreferencesKeys.Companion.ACCESS_TOKEN
import com.baseappname.app.data.local.pref.SharedPreferencesKeys.Companion.PRIVATE_MODE
import com.baseappname.app.data.local.pref.SharedPreferencesKeys.Companion.SHAREPRE_NAME
import com.baseappname.app.data.local.pref.SharedPreferencesKeys.Companion.USER_PROFILE
import com.baseappname.app.data.model.response.UserProfile

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class PrefManager
private constructor() : SharedPreferencesKeys {
    var pref: SharedPreferences? = null

    /**
     * Access token
     *
     * Get or set user access token to hit REST apis
     */
    var accessToken: String
        get() = pref!!.getString(ACCESS_TOKEN, "")!!
        set(accessToken) {
            pref!!.edit().putString(ACCESS_TOKEN, accessToken).apply()
        }


    /**
     * User profile
     *
     * Get or set user profile details
     */
    var userProfile: UserProfile?
        get() = (if (pref!!.getString(USER_PROFILE, "") != "") {
            Gson().fromJson(pref!!.getString(USER_PROFILE, ""), UserProfile::class.java)
        } else {
            null
        })
        set(userProfile) {
            pref!!.edit().putString(USER_PROFILE, Gson().toJson(userProfile)).apply()
        }


    /**
     * init shared preference
     * @param context application context
     */

    fun initPref(context: Context): PrefManager {
        pref = context.getSharedPreferences(SHAREPRE_NAME, PRIVATE_MODE)
        return this
    }

    /**
     * Clear pref data
     *
     * Clear shared preferences data on user log out
     *
     */
    fun clearPrefData() {
        pref!!.edit()
            .remove(USER_PROFILE)
            .remove(ACCESS_TOKEN)
            .apply()
    }

    companion object {
        private var instance: PrefManager? = null

        /**
         * Create shared preference class instance
         *
         * @return instance
         */
        fun getInstance(): PrefManager {
            if (instance == null) {
                instance = PrefManager()
            }
            return instance!!
        }
    }

}