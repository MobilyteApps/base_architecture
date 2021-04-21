package com.baseappname.app.data.model.request


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpRequest(
    @SerializedName("appleId")
    var appleId: String = "",
    @SerializedName("dateOfBirth")
    var dateOfBirth: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("facebookId")
    var facebookId: String = "",
    @SerializedName("firstName")
    var firstName: String = "",
    @SerializedName("isSocialLogin")
    var isSocialLogin: Boolean = false,
    @SerializedName("lastName")
    var lastName: String = "",
    @SerializedName("location")
    var location: Location,
    @SerializedName("password")
    var password: String = "",
    @SerializedName("profilePicture")
    var profilePicture: String = "",
    @SerializedName("selectedInterests")
    var selectedInterests: List<String> = listOf()
) : Serializable {
    data class Location(
        @SerializedName("coordinates")
        var coordinates: List<Double> = listOf(),
        @SerializedName("placeName")
        var placeName: String = ""
    ) : Serializable
}