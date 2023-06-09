package com.team.temara.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("data")
    val data: Data
)

data class Data(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("token")
    val token: String
)