package com.example.projectcapstone

// Import yang relevan
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditScreen(navController: NavController, viewModel: ProfileViewModel) {
    var profileImageUri by remember { mutableStateOf<Uri?>(viewModel.profileImageUri) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            profileImageUri = uri
        }
    )

    var name by remember { mutableStateOf(viewModel.name) }
    var password by remember { mutableStateOf(viewModel.password) }
    var email by remember { mutableStateOf(viewModel.email) }

    val scrollState = rememberScrollState()

    // Bagian Column untuk keseluruhan layar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header dengan latar belakang hijau
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF69E56E)) // Warna hijau
        ) {
            // Tombol kembali dan logo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        // Bagian untuk gambar profil
        Box(
            contentAlignment = Alignment.Center, // Pusatkan gambar di tengah
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-50).dp) // Geser ke atas agar berada di depan latar hijau
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp) // Ukuran lingkaran gambar
                    .clip(CircleShape)
                    .background(Color.White) // Warna latar belakang lingkaran gambar
                    .shadow(5.dp, CircleShape) // Tambahkan bayangan untuk efek depan
            ) {
                AsyncImage(
                    model = profileImageUri ?: ImageRequest.Builder(LocalContext.current)
                        .data("https://example.com/profile_image.png")
                        .build(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Tombol ganti foto
        Button(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF02A9F1)),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Change Photo", color = Color.White)
        }

        // Form field
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            ProfileTextField(label = "Name", value = name, onValueChange = { name = it })
            ProfileTextField(label = "Password", value = password, onValueChange = { password = it })
            ProfileTextField(label = "E-mail", value = email, onValueChange = { email = it })
        }

        // Tombol simpan
        Button(
            onClick = {
                viewModel.saveProfile(
                    name = name,
                    password = password,
                    email = email,
                    profileImageUri = profileImageUri
                )
                navController.navigate("ProfileScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF69E56E))
        ) {
            Text(text = "Save", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }

}

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordField: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            color = Color(0xFF2C2C2C),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPasswordField) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.White,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditPreview() {
    val mockViewModel = ProfileViewModel().apply {
        name = "Yuki Chan"
        password = "Admin123"
        email = "akuYangTersakiti@gmail.com"
    }

    EditScreen(
        navController = rememberNavController(),
        viewModel = mockViewModel
    )
}
