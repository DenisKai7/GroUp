package com.example.projectcapstone

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel(): ViewModel() {
    var profileImageUri by mutableStateOf<Uri?>(null)
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")
    var birth by mutableStateOf("")
    var gender by mutableStateOf("")
    var address by mutableStateOf("")

    fun saveProfile(
        newName:String,
        newPassword:String,
        newEmail:String,
        newBirth:String,
        newGender:String,
        newAddress:String,
        newProfileImageUri:Uri?
    ){
        name = newName
        password = newPassword
        email = newEmail
        birth = newBirth
        gender = newGender
        address = newAddress
        profileImageUri = newProfileImageUri
    }
    fun updateImage(uri: Uri
    ){
        profileImageUri = uri
    }
}