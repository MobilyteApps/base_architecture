package com.baseappname.app.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * @AUTHOR Amandeep Singh
 * @date 20/11/2019
 */
data class Interest(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("selected")
    var selected: Boolean
)