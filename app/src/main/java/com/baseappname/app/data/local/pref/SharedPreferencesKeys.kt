package com.baseappname.app.data.local.pref

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 *
 * Shared preferences keys
 *
 * @constructor Create empty Shared preferences keys
 */
interface SharedPreferencesKeys {
    companion object {
        const val PRIVATE_MODE = 0
        const val SHAREPRE_NAME = "App_Name_Here"
        const val ACCESS_TOKEN = "accessToken"
        const val USER_PROFILE = "userProfile"
    }
}