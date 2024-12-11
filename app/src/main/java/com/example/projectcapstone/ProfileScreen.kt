package com.example.projectcapstone

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projectcapstone.SessionManager.KEY_EMAIL
import com.example.projectcapstone.ui.theme.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,

    language: String,
    onLanguageChange: (String) -> Unit
) {

    val context = LocalContext.current
    val (name, email, profileImageUri) = SessionManager.getUserData(context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Back Button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        // Profile Picture and Details
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = profileImageUri ?: "https://example.com/default_profile_image.png",
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
            Text(text = name ?: "No Name", style = MaterialTheme.typography.bodyLarge)
            Text(text = email ?: "No Email", style = MaterialTheme.typography.bodyLarge)
        }

        // Options Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
        ) {
            ProfileOption(icon = Icons.Default.Info, label = "About us", onClick = {})
            ProfileOption(icon = Icons.Default.Help, label = "Help", onClick = {})
            ProfileOption(icon = Icons.Default.Settings, label = "Edit Profile", onClick = {navController.navigate(Routes.EditScreen)})
            LanguageToggle(isBahasa = language == "Bahasa", onLanguageChange = onLanguageChange)

            // Switch untuk Dark Mode
            ThemeOption(isDarkTheme = isDarkTheme, onThemeChange = onThemeChange)

            ProfileOption(
                icon = Icons.Default.ExitToApp,
                label = "Logout",
                labelColor = Color.Red,
                onClick = {
                    SessionManager.logout(context)
                    if (!SessionManager.getIsLogin(context)) {
                        Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()

                        navController.navigate(Routes.LoginScreen) {
                            popUpTo(0) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Logout gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        }
    }
}




@Composable
fun ProfileOption(icon: ImageVector, label: String, labelColor: Color = MaterialTheme.colorScheme.onBackground, onClick:() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, color = labelColor, fontSize = 16.sp)
    }
    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), thickness = 1.dp)
}

@Composable
fun LanguageToggle(isBahasa: Boolean, onLanguageChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Language,
            contentDescription = "Language",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = if (isBahasa) "Bahasa Indonesia" else "English",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isBahasa,
            onCheckedChange = { isChecked ->
                onLanguageChange(if (isChecked) "Bahasa" else "English")
            }
        )
    }
}

@Composable
fun ThemeOption(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Brightness5,
            contentDescription = "Dark Theme",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Dark Mode", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { onThemeChange(it) }
        )
    }
    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), thickness = 1.dp)
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val mockContext = LocalContext.current
    val mockData = Triple("John Doe", "johndoe@example.com", null)
    SessionManager.saveUserData(mockContext, mockData.first, mockData.second)

    ProfileScreen(
        navController = rememberNavController(),
        isDarkTheme = false,
        onThemeChange = {},
        language = "English",
        onLanguageChange = {}
    )
}

