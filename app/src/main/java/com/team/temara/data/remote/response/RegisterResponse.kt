package com.team.temara.data.remote.response

import com.google.gson.annotations.SerializedName

class RegisterResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String
)