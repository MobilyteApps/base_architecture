package com.baseappname.app.data.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: UserProfile,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int
)