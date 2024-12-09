package com.example.projectcapstone.ui.theme

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Patterns
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
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.example.projectcapstone.data.api.ApiClient
import com.example.projectcapstone.data.api.Article
import com.example.projectcapstone.data.api.GetUserResponse
import com.example.projectcapstone.data.api.UpdateUserRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserViewModel : ViewModel() {


    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _registerResult = MutableStateFlow<Result<String>>(Result.Loading)
    val registerResult: StateFlow<Result<String>> = _registerResult

    private val _loginResult = MutableStateFlow<Result<String>>(Result.Loading)
    val loginResult: StateFlow<Result<String>> = _loginResult

    var profileImageUri by mutableStateOf<Uri?>(null)

    fun saveUserData(inputEmail: String, inputName: String, inputPassword: String) {
        email = inputEmail
        name = inputName
        password = inputPassword
    }




    // Save access token and login status
    fun getLoginData(callback:(token: String, isLoggedIn: Boolean) ->Unit) {
        callback(accessToken, isLogin)
    }

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
                        accessToken =loginData.accessToken
                        isLogin =true
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
    fun updateUser(token: String, name: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().updateUser(
                    token = "Bearer $token",
                    request = UpdateUserRequest(name = name)
                )
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        callback(true, "Update successful")
                    } ?: run {
                        callback(false, "Unknown error occurred during update")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    callback(false, parseError(errorBody))
                }
            } catch (e: Exception) {
                callback(false, "Network error: ${e.message}")
            } finally {
                _isLoading.value = false
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
                // Contoh parsing manual untuk error tertentu
                "Email atau password tidak valid"
            } else {
                errorBody
            }
        }
    }
    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getArticles()
                _articles.value = response.data
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error fetching articles: ${e.message}", e)
            }
        }
    }


    data class ErrorResponse(
        val errors: Map<String, String>? = null
    )



    var email by mutableStateOf("")
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var accessToken by mutableStateOf("")
    var isLogin by mutableStateOf(true)


    fun updateEmail(newEmail: String) {
        email = newEmail
        println("Updated email: $email")
    }




}