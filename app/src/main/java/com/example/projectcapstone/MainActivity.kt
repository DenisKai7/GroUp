package com.example.projectcapstone


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectcapstone.ui.theme.ProjectCapstoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Atur state untuk dark mode secara global
            var isDarkTheme by remember { mutableStateOf(false) }


            ProjectCapstoneTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val profileViewModel = remember{ProfileViewModel()}
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.LoginScreen
                    ) {
                        composable(Routes.LoginScreen) {
                            LoginScreen(navController )
                        }
                        composable(Routes.HomePage) {
                            HomePage(navController, viewModel = profileViewModel )
                        }
                        composable(Routes.RegisterScreen) {
                            RegisterScreen(navController )
                        }

                        composable(Routes.StatusCheckScreen) {
                            StatusCheckScreen(navController)
                        }
                        composable(Routes.ProfileScreen) {
                            ProfileScreen(
                                navController = navController,
                                isDarkTheme = isDarkTheme,
                                onThemeChange = {},
                                viewModel = profileViewModel
                            )
                        }
                        composable(Routes.EditScreen) {
                            EditScreen(navController = navController, viewModel = profileViewModel)
                        }
                    }

                }
            }
        }
    }
}
