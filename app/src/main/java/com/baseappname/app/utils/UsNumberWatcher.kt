package com.baseappname.app.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class UsNumberWatcher : TextWatcher {
    private var lengthBefore = 0
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        lengthBefore = s.length
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (lengthBefore < s.length) {
            if (s.length == 3) s.append("-")
            if (s.length == 7) s.append("-")
        }
    }
}