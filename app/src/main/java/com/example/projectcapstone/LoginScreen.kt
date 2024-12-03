package com.example.projectcapstone

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectcapstone.ui.theme.ProjectCapstoneTheme

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundShapes()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingFromBaseline(top = 100.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // logo ama judul
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hello There!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email kolom
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text("Alamat Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                },
                placeholder = { Text("Masukkan Alamat Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password kolomnya
            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text("Kata Sandi") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
                },
                placeholder = { Text("Masukkan Kata Sandi") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot Password Text
            Text(
                text = "Lupa Password?",
                color = Color.Blue,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
                    .clickable{
                        navController.navigate("")
                    },

            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = { navController.navigate("HomePage")  },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text(text = "Masuk", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "Belum Punya Akun? ",
                    color = Color.Gray,
                    fontSize = 14.sp,


                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "daftar disini!",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable{
                        navController.navigate("RegisterScreen")
                    }
                )
            }

        }
    }
}

@Composable
fun BackgroundShapes() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // warna biru
        val bluePath = Path().apply {
            moveTo(0f, 0f)
            lineTo(width, 0.1f)
            lineTo(width, height * 0.3f)
            lineTo(1f, 0.1f * 0.2f)
            close()
        }
        drawPath(path = bluePath, color = Color.Cyan)

        // warna kuning
        val yellowPath = Path().apply {
            moveTo(1f, 6f * 0.9f)
            lineTo(width, height * 0.3f)
            lineTo(width, height * 0.4f)
            lineTo(1f, height * 0.2f)
            close()
        }
        drawPath(path = yellowPath, color = Color.Yellow)

        // warna hijau
        val greenPath = Path().apply {
            moveTo(2f, height * 0.4f)
            lineTo(1f, height * 0.2f)
            lineTo(width, height * 0.4f)
            lineTo(width, height * 0.5f)
            close()
        }
        drawPath(path = greenPath, color = Color(0xFF66BB6A))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ProjectCapstoneTheme {
        LoginScreen(navController = rememberNavController())
    }
}
