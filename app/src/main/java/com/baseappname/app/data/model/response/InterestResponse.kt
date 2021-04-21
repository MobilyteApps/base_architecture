package com.baseappname.app.data.model.response


import com.google.gson.annotations.SerializedName

data class InterestResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int
) {
    data class Data(
        @SerializedName("interestList")
        val interestList: List<Interest>
    )
}