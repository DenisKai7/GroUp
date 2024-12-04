package com.example.projectcapstone.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectcapstone.data.api.ApiConfig
import com.example.projectcapstone.data.api.LoginRequest
import com.example.projectcapstone.data.api.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.projectcapstone.data.api.Result
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class UserViewModel : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _registerResult = MutableStateFlow<Result<String>>(Result.Loading)
    val registerResult: StateFlow<Result<String>> = _registerResult

    private val _loginResult = MutableStateFlow<Result<String>>(Result.Loading)
    val loginResult: StateFlow<Result<String>> = _loginResult

    fun register(email: String, password: String, name: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().register(
                    RegisterRequest(email, password, name)
                )
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        _registerResult.value = Result.Success("Registration successful")
                        callback(true, null)
                    } ?: run {
                        val error = "Unknown error"
                        _registerResult.value = Result.Error(error)
                        callback(false, error)
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val parsedError = parseError(errorBody)
                    _registerResult.value = Result.Error(parsedError)
                    callback(false, parsedError)
                }
            } catch (e: Exception) {
                val errorMessage = "Network error: ${e.message}"
                _registerResult.value = Result.Error(errorMessage)
                callback(false, errorMessage)
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.data?.let { loginData ->
                        updateEmail(email)
                        callback(true, null)
                    } ?: run {
                        callback(false, "Unknown error occurred during login")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val parsedError = parseError(errorBody)
                    callback(false, parsedError)
                }
            } catch (e: Exception) {
                callback(false, "Network error: ${e.message}")
            }
        }
    }

    private fun parseError(errorBody: String): String {
        return try {
            val gson = com.google.gson.Gson()
            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.errors?.values?.joinToString(", ") ?: "Unknown error"
        } catch (jsonException: Exception) {
            if (errorBody.contains("is not allowed", ignoreCase = true)) {
                "Email atau password tidak valid"
            } else {
                errorBody
            }
        }
    }

    data class ErrorResponse(
        val errors: Map<String, String>? = null
    )

    var email by mutableStateOf("")
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
        println("Updated email: $email")
    }


    fun extractNameFromEmail(): String {
        return if (email.contains("@")) {
            email.substringBefore("@").replaceFirstChar { it.uppercaseChar() }
        } else {
            "Guest"
        }
    }

}

