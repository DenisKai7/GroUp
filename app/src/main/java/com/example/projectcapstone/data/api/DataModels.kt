package com.example.projectcapstone.data.api

import com.google.gson.annotations.SerializedName

// Register
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

data class RegisterResponse(
    @SerializedName("data")
    val data: UserData? = null,
    val errors: String? = null
)

// Login
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("data")
    val data: LoginData? = null,
    val errors: String? = null
)

data class LoginData(
    val accessToken: String,
    val status: String
)

// Update User
data class UpdateUserRequest(
    val email: String? = null,
    val password: String? = null,
    val name: String? = null
)

data class UpdateUserResponse(
    @SerializedName("data")
    val data: UserData? = null,
    val errors: String? = null
)

// Get User
data class GetUserResponse(
    @SerializedName("data")
    val data: UserData? = null,
    val errors: String? = null
)

data class UserData(
    val email: String,
    val name: String,
    val status: String? = null
)

// Logout
data class LogoutResponse(
    @SerializedName("data")
    val data: String? = null,
    val errors: String? = null
)


