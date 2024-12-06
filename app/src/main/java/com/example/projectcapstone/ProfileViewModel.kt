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

    fun saveProfile(
        newName:String,
        newPassword:String,
        newEmail:String,

    ){
        name = newName
        password = newPassword
        email = newEmail

        extractNameFromEmail()
    }
    fun updateImage(uri: Uri) {
        profileImageUri = uri
    }
    fun extractNameFromEmail() {
        if (email.contains("@")) {
            val extractedName = email.substringBefore("@")
            name = extractedName.replaceFirstChar { it.uppercaseChar() } // Kapitalisasi huruf pertama
        }
    }

}