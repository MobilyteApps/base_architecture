package com.baseappname.app.data.model.response


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int
)