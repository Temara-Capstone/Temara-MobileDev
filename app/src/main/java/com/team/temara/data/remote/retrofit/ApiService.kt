package com.team.temara.data.remote.retrofit

import com.team.temara.data.remote.response.ArticleResponse
import com.team.temara.data.remote.response.LoginResponse
import com.team.temara.data.remote.response.RegisterResponse
import com.team.temara.data.remote.response.UpdatePasswordResponse
import com.team.temara.data.remote.response.UpdateUserResponse
import com.team.temara.data.remote.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("profile/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): UserResponse

    @FormUrlEncoded
    @POST("profile/update")
    suspend fun updateUser(
        @Field("id") userId: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("dateofbirth") dateofbirth: String,
        @Field("gender") gender: String,
        @Field("no_hp") no_hp: String
    ): UpdateUserResponse

    @FormUrlEncoded
    @POST("profile/update")
    suspend fun updatePassword(
        @Field("id") userId: String,
        @Field("password") password: String
    ): UpdatePasswordResponse

    @GET("articles")
    suspend fun getArticle(
        @Header("Authorization") token: String,
    ): ArticleResponse


}