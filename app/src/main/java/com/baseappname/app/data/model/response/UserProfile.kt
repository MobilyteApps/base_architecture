package com.baseappname.app.data.model.response

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("profilePicture")
    val profilePicture: String,
    @SerializedName("selectedInterests")
    val selectedInterests: List<SelectedInterest>,
    @SerializedName("userId")
    val userId: String
) {
    data class Location(
        @SerializedName("coordinates")
        val coordinates: List<Double>,
        @SerializedName("placeName")
        val placeName: String,
        @SerializedName("type")
        val type: String
    )

    data class SelectedInterest(
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String
    )
}
