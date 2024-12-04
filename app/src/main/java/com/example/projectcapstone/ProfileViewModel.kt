package com.example.projectcapstone

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcapstone.data.api.ApiConfig
import com.example.projectcapstone.data.api.ApiService
import com.example.projectcapstone.data.api.UpdateUserRequest
import kotlinx.coroutines.launch

class ProfileViewModel(): ViewModel() {
    var profileImageUri by mutableStateOf<Uri?>(null)
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")

    private val ApiService = ApiConfig.create()
    fun saveProfile(name: String, password: String, email: String, profileImageUri: Uri?) {
        viewModelScope.launch {
            try {
                val token = "Bearer your_accessToken"
                val request = UpdateUserRequest(name, email, password)
                val response = ApiService.updateUser(token, request)

                if (response.isSuccessful) {
                    val userData = response.body()?.data
                    this@ProfileViewModel.name = userData?.name ?: ""
                    this@ProfileViewModel.email = userData?.email ?: ""
                } else {
                    // Handle error
                    Log.e("ProfileViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            }
        }
    }
    fun updateImage(uri: Uri) {
        profileImageUri = uri
    }
    fun extractNameFromEmail() {
        if (email.contains("@")) {
            val extractedName = email.substringBefore("@")
            name = extractedName.replaceFirstChar { it.uppercaseChar() }
        }

    }

}