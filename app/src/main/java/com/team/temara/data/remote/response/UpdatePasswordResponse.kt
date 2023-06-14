package com.team.temara.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdatePasswordResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String
)