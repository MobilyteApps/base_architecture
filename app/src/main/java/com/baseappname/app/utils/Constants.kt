package com.baseappname.app.utils

import android.os.Environment


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
object Constants {

    //region Urls and Keys
    const val TERM_AND_CONDITION_URL = Dev.TERM_AND_CONDITION_URL
    const val PRIVACY_POLICY_URL = Dev.PRIVACY_POLICY_URL
    const val BASE_URL = Dev.BASE_URL
    //endregion

    //region Environments
    private object Dev {
        const val BASE_URL = "http://52.203.40.246:3000/api/"
        const val TERM_AND_CONDITION_URL = ""
        const val PRIVACY_POLICY_URL = ""
    }

    private object Staging {
        const val BASE_URL = "http://52.203.40.246:3000/api/"
        const val TERM_AND_CONDITION_URL = ""
        const val PRIVACY_POLICY_URL = ""
    }

    private object Live {
        const val BASE_URL = "http://52.203.40.246:3000/api/"
        const val TERM_AND_CONDITION_URL = ""
        const val PRIVACY_POLICY_URL = ""
    }
    //endregion

    //region Passing Keys
    interface BundleKey {
        companion object {
            const val KEY_PARAM_SIGN_UP_REQUEST = "param_sign_up_request"
        }
    }
    //endregion

    //region Date Utils
    const val DISPLAY_APPOINTMENT_DATE_FORMAT = "d MMMM, YYYY"
    //endregion

    //region Destinations
    const val DESTINATION = "destinationScreen"
    //endregion

    //region Permission Codes
    const val RC_WRITE_STORAGE_PERMISSION = 2022
    const val RC_CAMERA_PERMISSION = 202
    //endregion

    //region Firebase Topics
    const val FCM_TOPIC_PREFIX = "baseappname_"
    const val FCM_TOPIC_NEWS = "baseappname_"
    //endregion

    //region Image and Files
    const val JPEG_FILE_PREFIX = "IMG_"
    const val JPEG_FILE_SUFFIX = ".jpg"
    const val PDF_FILE_PREFIX = "PDF_"
    const val PDF_FILE_SUFFIX = ".pdf"
    var LOCAL_STORAGE_BASE_PATH_FOR_MEDIA =
        Environment.getExternalStorageDirectory().toString() + "/baseappname"

    var LOCAL_STORAGE_BASE_PATH_FOR_USER_PROFILE =
        "$LOCAL_STORAGE_BASE_PATH_FOR_MEDIA/Profile"


    var LOCAL_STORAGE_BASE_PATH_FOR_DOCS_PDF =
        "$LOCAL_STORAGE_BASE_PATH_FOR_MEDIA/Documents/Pdfs"
    var LOCAL_STORAGE_BASE_PATH_FOR_DOCS_IMG =
        "$LOCAL_STORAGE_BASE_PATH_FOR_MEDIA/Documents/Images"
    //endregion

}