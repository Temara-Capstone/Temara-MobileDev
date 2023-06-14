package com.team.temara.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuotesResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("result")
    val result: resultQuotes
)

data class resultQuotes(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("quote")
    val quote: String
)