package com.team.temara.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class ForumResponse (
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("results")
    val resultForum: List<ForumList>
)

@Parcelize
data class ForumList(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("user_id")
    val user_id: String,

    @field:SerializedName("text")
    val text: String,

    @field:SerializedName("images")
    val images: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String

) : Parcelable

data class PostForumResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)