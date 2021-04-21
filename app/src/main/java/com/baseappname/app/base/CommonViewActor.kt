package com.baseappname.app.base

import androidx.annotation.StringRes


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
interface CommonViewActor : BaseViewActor {
    /**
     *Method to show API Error/Internet Error on view
     * @param throwable containing error
     */
    fun onApiError(throwable: Throwable)

    /**
     * show validation error on view
     *
     * @param resId string resource id
     */
    fun onValidationError(@StringRes resId: Int)

}