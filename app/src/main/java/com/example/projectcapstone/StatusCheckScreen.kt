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
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun StatusCheckScreen(navController: NavController,) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .padding(end = 16.dp)
                    .verticalScroll(scrollState)
            ) {

                LogoSection()

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Status Check",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF888282)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DataAnakCard()

                Spacer(modifier = Modifier.height(16.dp))


                GridCards()
            }


            BottomNavigationBars(navController = navController)
        }
    }
}

@Composable
fun LogoSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(220.dp)
        )
    }
}

@Composable
fun DataAnakCard() {
    var namaAnak by remember { mutableStateOf("") }
    var usiaAnak by remember { mutableStateOf("") }
    var tinggiBadanAnak by remember { mutableStateOf("") }
    var beratBadanAnak by remember { mutableStateOf("") }
    var jenisKelaminAnak by remember { mutableStateOf("") }
    var statusAnak by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00B8D4))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Data Anak",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input fields
            OutlinedTextField(
                value = namaAnak,
                onValueChange = { namaAnak = it },
                label = { Text("Nama Anak") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = usiaAnak,
                onValueChange = { usiaAnak = it },
                label = { Text("Usia Anak") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tinggiBadanAnak,
                onValueChange = { tinggiBadanAnak = it },
                label = { Text("Tinggi Badan Anak (cm)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = beratBadanAnak,
                onValueChange = { beratBadanAnak = it },
                label = { Text("Berat Badan Anak (kg)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = jenisKelaminAnak,
                onValueChange = { jenisKelaminAnak = it },
                label = { Text("Jenis Kelamin Anak") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = statusAnak,
                onValueChange = { statusAnak = it },
                label = { Text("Status Anak") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Button to submit data
            Button(
                onClick = {
                    // Implementasi aksi kirim data
                    println("Data Anak:")
                    println("Nama: $namaAnak")
                    println("Usia: $usiaAnak")
                    println("Tinggi: $tinggiBadanAnak cm")
                    println("Berat: $beratBadanAnak kg")
                    println("Jenis Kelamin: $jenisKelaminAnak")
                    println("Status: $statusAnak")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "Kirim Data",
                    color = Color(0xFF00B8D4),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun GridCards() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Left Column
        Column(modifier = Modifier.weight(1f)) {
            GridCard(color = Color(0xFF86E80D), text = "Rekomendasi Gizi")
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Right Column
        Column(modifier = Modifier.weight(1f)) {
            GridCard(color = Color(0xFF86E80D), text = "Prediksi Perkembangan")
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    GridCard(
        color = Color(0xFF86E80D),
        text = "Kasus Serupa",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun GridCard(color: Color, text: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Composable
fun BottomNavigationBars(modifier: Modifier = Modifier, navController: NavController) {
    BottomNavigation(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate(Routes.HomePage) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Article, contentDescription = " Check") },
            label = { Text(" Check") },
            selected = false,
            onClick = { navController.navigate(Routes.StatusCheckScreen) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = false,
            onClick = { navController.navigate(Routes.ProfileScreen) }
        )
    }
}



@Preview
@Composable
fun CheckPreview(){
    StatusCheckScreen(navController = rememberNavController())
}