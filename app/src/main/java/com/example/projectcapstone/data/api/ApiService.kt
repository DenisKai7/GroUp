package com.example.projectcapstone.data.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/users")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @PATCH("api/users/current")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest
    ): Response<UpdateUserResponse>

    @GET("api/users/current")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<GetUserResponse>

    @DELETE("api/users/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<LogoutResponse>

    @POST("api/predicts/stunting")
    fun predictStunting(@Body request: PredictRequest): Call<PredictResponse>

    @POST("api/predicts/similiarity")
    fun predictSimilarity(@Body request: PredictRequest): Call<PredictResponse>
}