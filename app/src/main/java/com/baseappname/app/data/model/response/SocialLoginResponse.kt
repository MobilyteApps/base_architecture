package com.baseappname.app.data.model.response


import com.google.gson.annotations.SerializedName

data class SocialLoginResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int
) {
    data class Data(
        @SerializedName("isProfileCompleted")
        val isProfileCompleted: Boolean,
        @SerializedName("userDetails")
        val userDetails: UserProfile
    )
}