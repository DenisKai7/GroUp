package com.example.projectcapstone

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.projectcapstone.data.api.Article
import com.example.projectcapstone.ui.theme.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

// Main Screen
@Composable
fun HomePage(navController: NavController, viewModel: UserViewModel = viewModel(),language: String) {
    val welcomeText = if (language == "Bahasa") "Selamat Datang" else "Welcome"
    val scrollState = rememberScrollState()
    val articles = viewModel.articles.collectAsState(initial = emptyList())
    val date = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id")).format(Date())
    val isLoading = viewModel.isLoading.collectAsState(initial = false)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchArticles()
    }
    val email = SessionManager.getEmail(context)
    val name = extractNameFromEmail(email)

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        if (isLoading.value) {
            // Menampilkan loading indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAAFFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF388E3C))
            }
        } else {
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
                    Image(
                        painter = painterResource(id = R.drawable.logo),
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
                    text = "$welcomeText, $name",
                    color = Color(0xFF388E3C),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Sudah cek tumbuh kembang anak hari ini?",
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))


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

                Text(
                    text = "Artikel",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                articles.value.forEach { article ->
                    ArticleItem(article = article)
                }

            }
            Spacer(modifier = Modifier.height(30.dp))
            BottomNavigationBar(modifier = Modifier.align(Alignment.BottomCenter), navController)
        }
    }
}
fun extractNameFromEmail(email: String): String {
    return email.substringBefore("@")
}


@Composable
fun ArticleItem(article: Article) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.urlWeb))
                context.startActivity(intent)
            }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gambar Artikel
            if (article.urlImage != "kosong") {
                Image(
                    painter = rememberAsyncImagePainter(article.urlImage),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray, RoundedCornerShape(4.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray, RoundedCornerShape(4.dp))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Detail Artikel
            Column {
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tanggal: ${article.scrapedAt}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}



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
            icon = { Icon(Icons.Filled.Article, contentDescription = " Check") },
            label = { Text(" Check") },
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


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val viewModel = UserViewModel()
    val language = "English"
    HomePage(navController = navController, viewModel = viewModel, language =language )
}