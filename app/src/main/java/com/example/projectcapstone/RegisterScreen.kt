package com.example.projectcapstone

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectcapstone.ui.theme.ProjectCapstoneTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectcapstone.ui.theme.UserViewModel
import com.example.projectcapstone.data.api.Result

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel(),
    ViewModel:ProfileViewModel = viewModel()
) {
    val isloading by viewModel.isLoading.collectAsState()
    val registerResult by viewModel.registerResult.
    collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        BelakangShapes()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingFromBaseline(top = 80.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var email by remember { mutableStateOf("") }
            var password by remember{ mutableStateOf("") }
            var fullName by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }
            val registerSuccess = remember { mutableStateOf(false) }
            val context = LocalContext.current

            Image(
                painter = painterResource(id = R.drawable.logo), // Ganti dengan logo Anda
                contentDescription = "Logo",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Register First!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Input Email

            OutlinedTextField(
                value = email,
                onValueChange = { email = it }, // Update email secara real-time
                label = { Text("Masukkan Alamat Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                },
                placeholder = { Text("Masukkan Alamat Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Input Nama Lengkap
            Text(
                text = "Nama Lengkap",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it }, // Update fullName secara real-time
                label = { Text("Masukkan Nama Lengkap") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account Icon")
                },
                placeholder = { Text("Masukkan Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))


            // Input Kata Sandi
            Text(
                text = "Kata Sandi",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Masukkan Kata Sandi") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
                },
                placeholder = { Text("Masukkan Kata Sandi") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Sembunyikan Kata Sandi" else "Tampilkan Kata Sandi"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Daftar
            Button(
                onClick = {
                    viewModel.register(email, password, fullName){ success,errorMessage ->
                        if(success){
                            ViewModel.email = email
                            ViewModel.extractNameFromEmail()
                            navController.navigate("loginScreen")
                        }else{
                            Toast.makeText(
                                context,
                                errorMessage?:"Resgister anda gagal",
                                Toast.LENGTH_LONG

                            ).show()
                        }

                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Daftar Diri", color = Color.White, fontSize = 16.sp)
            }
            if (isloading){
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(20.dp))
            }else{
                Text(text = "masuk", color = Color.White, fontSize = 16.sp)
            }

            when (registerResult) {
                is Result.Loading -> CircularProgressIndicator()
                is Result.Success -> {
                    Text("Registrasi berhasil: ${(registerResult as Result.Success).data}")
                    LaunchedEffect(Unit) {
                        navController.navigate(Routes.LoginScreen)
                    }
                }
                is Result.Error -> Text(
                    text = (registerResult as Result.Error).error,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )

                else -> {}
            }
        }
    }
}


@Composable
fun BelakangShapes() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Blue shape
        val bluePath = Path().apply {
            moveTo(0f, 0f)
            lineTo(width, 0.1f)
            lineTo(width, height * 0.3f)
            lineTo(1f, 0.1f * 0.2f)
            close()
        }
        drawPath(path = bluePath, color = Color.Cyan)

        // Yellow shape
        val yellowPath = Path().apply {
            moveTo(1f, 6f * 0.9f)
            lineTo(width, height * 0.3f)
            lineTo(width, height * 0.4f)
            lineTo(1f, height * 0.2f)
            close()
        }
        drawPath(path = yellowPath, color = Color.Yellow)

        // Green shape
        val greenPath = Path().apply {
            moveTo(2f, height * 0.4f)
            lineTo(1f, height * 0.2f)
            lineTo(width, height * 0.4f)
            lineTo(width, height * 0.5f)
            close()
        }
        drawPath(path = greenPath, color = Color(0xFF66BB6A)) // Adjust the green shade if needed
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    ProjectCapstoneTheme {
        RegisterScreen(navController = rememberNavController())
    }
}