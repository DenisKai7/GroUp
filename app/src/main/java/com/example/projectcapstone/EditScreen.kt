package com.example.projectcapstone

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun EditScreen(navController: NavController,viewModel: ProfileViewModel) {
    var profileImageUri by remember { mutableStateOf<Uri?>(viewModel.profileImageUri) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri:Uri? ->
            profileImageUri = uri
        }
    )
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf(viewModel.name) }
    var password by remember { mutableStateOf(viewModel.password) }
    var email by remember { mutableStateOf(viewModel.email) }
    var birth by remember { mutableStateOf(viewModel.birth) }
    var gender by remember { mutableStateOf(viewModel.gender) }
    var address by remember { mutableStateOf(viewModel.address) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // Header with back button and logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                // Set the green background color

        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFF69E56E)),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                IconButton(onClick = { /* Handle back action */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo), // Replace with your logo resource
                    contentDescription = "Logo",
                    modifier = Modifier.size(90.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 74.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = profileImageUri?: ImageRequest.Builder(context)
                        .data("https://example.com/profile_image.png")
                        .build(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )

                Button(
                    onClick = { launcher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF02A9F1)),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Edit Profile", color = Color.White)
                }
            }
        }

        // Form fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFE8E8E8), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            ProfileTextField(label = "Name", value = name, onValueChange = { name = it })
            ProfileTextField(label = "Password", value = password, onValueChange = { password = it })
            ProfileTextField(label = "E-mail", value = email, onValueChange = { email = it })
            ProfileTextField(label = "Birth", value = birth, onValueChange = { birth = it })
//            ProfileTextField(label = "Gender", value = gender, onValueChange = { gender = it })
            ProfileTextField(label = "Address", value = address, onValueChange = { address = it })
        }

        // Save Button
        Button(
            onClick = {
                viewModel.saveProfile(
                    name,
                    password,
                    email,
                    birth,
                    gender,
                    address,
                    profileImageUri
                )
                navController.navigate("ProfileScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF69E56E))
        ) {
            Text(text = "Save", color = Color.White)
        }
        BottomNavigationBar(navController = navController)
    }

}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, color = Color(0xFF2C2C2C), style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.White,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}



@Preview(showBackground = true)
@Composable
fun EditPreview() {
    val mockViewModel = ProfileViewModel().apply {
        name = "John Doe"
        email = "john.doe@example.com"
    }

    EditScreen(
        navController = rememberNavController(),
        viewModel = mockViewModel
    )
}

