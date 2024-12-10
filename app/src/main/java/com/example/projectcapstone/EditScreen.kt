    package com.example.projectcapstone
    
    import android.net.Uri
    import android.util.Patterns
    import androidx.activity.compose.rememberLauncherForActivityResult
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.compose.foundation.Canvas
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
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
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.input.PasswordVisualTransformation
    import androidx.compose.ui.text.input.VisualTransformation
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import coil.compose.AsyncImage
    import coil.request.ImageRequest
    import com.example.projectcapstone.ui.theme.UserViewModel
    
    
    
    
    @Composable
    fun EditScreen(navController: NavController, viewModel: UserViewModel) {
    
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        var name by remember { mutableStateOf(viewModel.name) }
        var email by remember { mutableStateOf(viewModel.email) }
        var password by remember { mutableStateOf(viewModel.password) }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
        var isPasswordVisible by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf<String?>(null) }
        var emailError by remember { mutableStateOf<String?>(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? -> selectedImageUri = uri }
        )
        val currentName by viewModel.userName.collectAsState()
        val currentEmail by viewModel.userEmail.collectAsState()


        fun validatePassword(): Boolean {
            return if (password.length >= 8) {
                passwordError = null
                true
            } else {
                passwordError = "Password must be at least 8 characters long."
                false
            }
        }

        // Validasi form
        fun validateForm(): Boolean {
            var isValid = true

            // Validasi email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailError = "Please enter a valid email address."
                isValid = false
            } else {
                emailError = null
            }

            // Validasi password
            if (!validatePassword()) {
                isValid = false
            }

            return isValid
        }
    
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
    
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color(0xFF69E56E)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(onClick = { navController.navigate(Routes.ProfileScreen) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.logo),
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
                        model = selectedImageUri ?: ImageRequest.Builder(context)
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
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = emailError != null
                )
                emailError?.let {
                    Text(
                        text = it
                    )
                }
                ProfileTextField(
                    label = "Password",
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    isPasswordField = true,
                    onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                    passwordVisible = isPasswordVisible,
                    errorMessage = passwordError
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (validateForm()) {
                            val token = SessionManager.getAccessToken(context)
                            viewModel.updateUser(token, name) { success, message ->
                                if (success) {
                                    // Simpan data ke SessionManager
                                    SessionManager.saveUserData(context, name, email, selectedImageUri?.toString())
                                    println("Update successful: $message")
                                    navController.navigate(Routes.ProfileScreen) // Navigasi ke ProfileScreen
                                } else {
                                    println("Update failed: $message")
                                }
                            }
                        }
                    },
                    enabled = name.isNotBlank() && email.isNotBlank() && validateForm(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Profile")
                }


            }
        }
    }
    
    
    
    @Composable
    fun ProfileTextField(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        isPasswordField: Boolean = false,
        onTogglePasswordVisibility: (() -> Unit)? = null,
        passwordVisible: Boolean = false,
        errorMessage: String? = null
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = label, color = Color(0xFF2C2C2C), style = MaterialTheme.typography.bodyLarge)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if (isPasswordField) {
                        IconButton(onClick = { onTogglePasswordVisibility?.invoke() }) {
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
                modifier = Modifier.fillMaxWidth()
            )
            errorMessage?.let {
                Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
    
    
    
    @Preview(showBackground = true)
    @Composable
    fun EditPreview() {
        val mockViewModel = UserViewModel().apply {
            name = "John Doe"
            email = "john.doe@example.com"
        }
        val language = "English"
        EditScreen(
            navController = rememberNavController(),
            viewModel = mockViewModel,

        )
    }