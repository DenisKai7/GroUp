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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.example.projectcapstone.data.api.ApiClient
import com.example.projectcapstone.data.api.ApiService
import com.example.projectcapstone.data.api.Article
import com.example.projectcapstone.data.api.GetUserResponse
import com.example.projectcapstone.data.api.PredictData
import com.example.projectcapstone.data.api.PredictRequest
import com.example.projectcapstone.data.api.PredictResponse
import com.example.projectcapstone.data.api.UpdateUserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel : ViewModel() {

    private val _similarCases = MutableLiveData<List<PredictData>>()
    val similarCases: LiveData<List<PredictData>> get() = _similarCases

    private val _predictStuntingResult = MutableLiveData<PredictResponse>()
    val predictStuntingResult: LiveData<PredictResponse> get() = _predictStuntingResult

    private val _predictSimilarityResult = MutableLiveData<PredictResponse>()
    val predictSimilarityResult: LiveData<PredictResponse> = _predictSimilarityResult


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val apiService = ApiConfig.getApiService()

    private val _userName = MutableStateFlow("Initial Name")
    val userName: StateFlow<String> = _userName

    private val _userEmail = MutableStateFlow("initial.email@example.com")
    val userEmail: StateFlow<String> = _userEmail

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _registerResult = MutableStateFlow<Result<String>>(Result.Loading)
    val registerResult: StateFlow<Result<String>> = _registerResult

    private val _loginResult = MutableStateFlow<Result<String>>(Result.Loading)
    val loginResult: StateFlow<Result<String>> = _loginResult



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

    fun predictStunting(request: PredictRequest) {
        if (request.gender != "Male" && request.gender != "Female") {
            _errorMessage.value = "Gender must be one of [Male or Female]"
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val call = apiService.predictStunting(request)
            call.enqueue(object : Callback<PredictResponse> {
                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    if (response.isSuccessful) {
                        _predictStuntingResult.postValue(response.body())
                    } else {
                        _errorMessage.postValue("Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    _errorMessage.postValue("Failure: ${t.message}")
                }
            })
        }
    }

    fun predictSimilarity(request: PredictRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = apiService.predictSimilarity(request)
            call.enqueue(object : Callback<PredictResponse> {
                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        responseData?.let {
                            if (it.errors.isNullOrEmpty()) {
                                _predictSimilarityResult.postValue(it)
                            } else {
                                _errorMessage.postValue(it.errors)
                            }
                        }
                    } else {
                        _errorMessage.postValue("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    _errorMessage.postValue("Failure: ${t.message}")
                }
            })
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