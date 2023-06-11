package com.team.temara.data.remote.retrofit

import com.team.temara.data.remote.response.LoginResponse
import com.team.temara.data.remote.response.RegisterResponse
import com.team.temara.data.remote.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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


}