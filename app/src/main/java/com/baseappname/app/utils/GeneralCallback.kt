package com.baseappname.app.utils

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
interface GeneralCallback {
    fun onItemSelectedFromList(item: Any, position: Int) {}
    fun onDatePicked(date: String, dateInMillis: Long) {}
    fun onPositiveClick() {}
    fun onNegativeClick() {}
}