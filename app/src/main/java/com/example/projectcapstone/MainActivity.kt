package com.example.projectcapstone


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectcapstone.ui.theme.ProjectCapstoneTheme
import com.example.projectcapstone.ui.theme.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var isDarkTheme by remember { mutableStateOf(false) }
            var language by remember { mutableStateOf("English") }

            ProjectCapstoneTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val profileViewModel = remember { UserViewModel() }

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = "SplashScreen"
                    ) {
                        composable("SplashScreen") {
                            SplashScreen(navController = navController)
                        }
                        composable(Routes.LoginScreen) {
                            LoginScreen(navController = navController)
                        }
                        composable(Routes.HomePage) {
                            HomePage(
                                navController = navController,
                                language = language
                            )
                        }
                        composable(Routes.RegisterScreen) {
                            RegisterScreen(navController = navController)
                        }
                        composable(Routes.StatusCheckScreen) {
                            StatusCheckScreen(navController = navController)
                        }
                        composable(Routes.ProfileScreen) {
                            ProfileScreen(
                                navController = navController,
                                isDarkTheme = isDarkTheme,
                                onThemeChange = { isDarkTheme = !isDarkTheme },
                                language = language,
                                onLanguageChange = { language = it },

                            )
                        }
                        composable(Routes.EditScreen) {
                            EditScreen(
                                navController = navController,
                                viewModel = profileViewModel,

                            )
                        }
                    }
                }
            }
        }
    }
}

