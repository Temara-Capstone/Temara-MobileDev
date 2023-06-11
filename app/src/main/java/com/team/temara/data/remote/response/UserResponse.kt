package com.team.temara.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("result")
    val result: resultData
)

data class resultData(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("dateofbirth")
    val dateofbirth: String,

    @field:SerializedName("no_hp")
    val no_hp: String,

    @field:SerializedName("image")
    val image: String
)