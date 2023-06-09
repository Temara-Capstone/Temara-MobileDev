package com.team.temara.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("Data")
    val data: Data

)

data class Data(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)