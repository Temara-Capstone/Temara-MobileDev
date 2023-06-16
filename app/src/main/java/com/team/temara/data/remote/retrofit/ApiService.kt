package com.team.temara.data.remote.retrofit

import com.team.temara.data.remote.response.ArticleResponse
import com.team.temara.data.remote.response.ForumResponse
import com.team.temara.data.remote.response.LoginResponse
import com.team.temara.data.remote.response.QuotesResponse
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
import retrofit2.http.PUT
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
    @PUT("profile/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Field("id") userId: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("dateofbirth") dateofbirth: String,
        @Field("gender") gender: String,
        @Field("no_hp") no_hp: String
    ): UpdateUserResponse

    @FormUrlEncoded
    @PUT("changePassword")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Field("id") userId: String,
        @Field("newPassword") password: String
    ): UpdatePasswordResponse

    @GET("articles")
    suspend fun getArticle(
        @Header("Authorization") token: String,
    ): ArticleResponse

    @GET("quotes")
    suspend fun getQuotes(
        @Header("Authorization") token: String,
    ): QuotesResponse

    @GET("forum")
    suspend fun getForum(
        @Header("Authorization") token: String
    ): ForumResponse


}