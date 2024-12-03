package com.example.projectcapstone

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectcapstone.ui.theme.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

// Main Screen
@Composable
fun HomePage(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    val date = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id")).format(Date())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState)
                .align(Alignment.TopStart)

        ) {
            // Logo and Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo Placeholder
                Image(
                    painter = painterResource(id = R.drawable.logo), // Replace with your logo resource
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
                // Date
                Text(
                    text = date,
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Hallo, ${viewModel.extractNameFromEmail()}",
                color = Color(0xFF388E3C),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
//            Text(
//                text = viewModel.name,
//                color = Color(0xFF388E3C),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            )
            Text(
                text = "Sudah cek tumbuh kembang anak hari ini?",
                color = Color.DarkGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Bagian Info
            Text(
                text = "Info",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .height(200.dp)
                        .background(Color(0xFF76FF03), shape = RoundedCornerShape(8.dp))
                ) {

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp)
                        .background(Color(0xFFFFEB3B), shape = RoundedCornerShape(8.dp))
                ) {

                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp)
                        .background(Color(0xFF03A9F4), shape = RoundedCornerShape(8.dp))
                ) {

                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Bagian Artikel
            Text(
                text = "Artikel",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF76FF03), shape = RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                        .padding(bottom = 40.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pentingnya pengetahuan dini tentang stunting",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        BottomNavigationBar(modifier = Modifier.align(Alignment.BottomCenter), navController)
    }
}
//fun extractNameFromEmail(email: String): String {
//    return email.substringBefore('@').replaceFirstChar { it.uppercase() }
//}


// Bottom Navigation Bar
@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    BottomNavigation(
        modifier = modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = Color.White,
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Black,
            onClick = {  navController.navigate(Routes.HomePage) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Check, contentDescription = "Articles") },
            label = { Text("Articles") },
            selected = false,
            onClick = { navController.navigate("StatusCheckScreen") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = false,
            onClick = { navController.navigate(Routes.ProfileScreen) }
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun HomePreview() {
//    val navController = rememberNavController()
//    val viewModel = ProfileViewModel()
//    HomePage(navController = navController, viewModel = viewModel)
//}
