package com.example.projectcapstone

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
fun HomePage(navController: NavController, viewModel: UserViewModel = viewModel(), language: String) {
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
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAAFFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material.CircularProgressIndicator(color = Color(0xFF388E3C))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
                    .align(Alignment.TopStart)
            ) {
                // Header Section
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
                    Text(
                        text = date,
                        color = MaterialTheme.colorScheme.onBackground,
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
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Info Section
                Text(
                    text = "Info",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Horizontal Scrollable Box with both "Apa itu Stunting?" and "Panduan Orang Tua"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(8.dp)
                ) {
                    // Apa itu stunting? Box
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                            .border(
                                width = 4.dp,
                                color = Color.Green,
                                shape = RoundedCornerShape(8.dp)
                            )
//                            .background(
//                                color = Color(0xFF90EE90),
//                                shape = RoundedCornerShape(8.dp)
//                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.stunting),
                                contentDescription = "Stunting",
                                modifier = Modifier.size(100.dp)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Apa itu Stunting?",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Stunting adalah kondisi gagal tumbuh pada anak akibat kekurangan gizi kronis.",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Baca Selengkapnya",
                                    color = Color.Blue,
                                    fontSize = 14.sp,
                                    modifier = Modifier.clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/14JKJ_LMPq-oceqFBNV1O9YE59O6Xfbte/view?usp=drive_link"))
                                        context.startActivity(intent)
                                    }
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))


                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                            .border(
                                width = 4.dp,
                                color = Color.Green,
                                shape = RoundedCornerShape(8.dp)
                            )
//                            .background(
//                                color = Color(0xFF90EE90),
//                                shape = RoundedCornerShape(8.dp)
//                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.parents),
                                contentDescription = "Panduan Orang Tua",
                                modifier = Modifier.size(100.dp)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Panduan Orang Tua",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Panduan ini memberikan tips bagi orang tua untuk mendukung tumbuh kembang anak.",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Baca Selengkapnya",
                                    color = Color.Blue,
                                    fontSize = 14.sp,
                                    modifier = Modifier.clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1etmI1rJSGzucyoAFocOH-eo_7TuPRRT_/view?usp=sharing"))
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Artikel Section
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Artikel",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    articles.value.forEach { article ->
                        ArticleItem(article = article)
                    }
                }
            }

            // Bottom Navigation
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
            .padding(bottom = 30.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.urlWeb))
                context.startActivity(intent)
            }
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (article.urlImage != "kosong") {
                Image(
                    painter = rememberAsyncImagePainter(article.urlImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)

                )
            } else {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Gray, RoundedCornerShape(4.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = article.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
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
        backgroundColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            selectedContentColor = Color(0xFF388E3C),
            unselectedContentColor = Color.Gray,
            onClick = { navController.navigate(Routes.HomePage) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Article, contentDescription = "Check") },
            label = { Text("Check") },
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
    HomePage(navController = navController, viewModel = viewModel, language = language)
}