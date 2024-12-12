package com.example.projectcapstone

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val isLoggedIn = SessionManager.getIsLogin(context)

    // Animasi untuk pergerakan Y
    val offsetY = remember { Animatable(-300f) }
    // Animasi untuk scale (pembesaran logo)
    val scale = remember { Animatable(1f) }

    LaunchedEffect(key1 = true) {
        // Animasi bola memantul
        offsetY.animateTo(
            targetValue = 0f,
            animationSpec = keyframes {
                durationMillis = 1500
                -300f at 0
                0f at 700 with androidx.compose.animation.core.FastOutSlowInEasing
                -100f at 900
                0f at 1100
                -50f at 1200
                0f at 1500
            }
        )
        // Animasi pembesaran logo
        scale.animateTo(
            targetValue = 1.5f, // Besar logo meningkat 1.5x
            animationSpec = tween(
                durationMillis = 500, // Durasi pembesaran
                easing = { it * it } // Efek percepatan
            )
        )
        // Tunggu sebelum navigasi
        delay(2000L)
        if (isLoggedIn) {
            navController.navigate("HomePage") {
                popUpTo("LoginScreen") { inclusive = true }
            }
        } else {
            navController.navigate("LoginScreen") {
                popUpTo("SplashScreen") { inclusive = true }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90EE90))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .offset(y = Dp(offsetY.value)) // Gerakan vertikal
                .scale(scale.value) // Pembesaran logo
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = NavController(context = androidx.compose.ui.platform.LocalContext.current))
}
