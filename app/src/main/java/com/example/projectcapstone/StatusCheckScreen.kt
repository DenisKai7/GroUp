package com.example.projectcapstone

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectcapstone.data.api.PredictRequest
import com.example.projectcapstone.data.api.PredictResponse
import com.example.projectcapstone.ui.theme.UserViewModel

@Composable
fun StatusCheckScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val predictViewModel: UserViewModel = viewModel()
    val predictResult by predictViewModel.predictStuntingResult.observeAsState()
    val similarCasesResult by predictViewModel.predictSimilarityResult.observeAsState()
    val errorMessage by predictViewModel.errorMessage.observeAsState()


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
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                LogoSection()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Status Check",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00B8D4),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                DataAnakCard(predictViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                ResultCard(predictResult, errorMessage)

                Spacer(modifier = Modifier.height(16.dp))

                SimilarCasesCard(similarCasesResult, errorMessage)
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
            painter = painterResource(id = R.drawable.logo), // Replace with your logo resource
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
fun DataAnakCard(predictViewModel: UserViewModel) {
    var namaAnak by remember { mutableStateOf("") }
    var usiaAnak by remember { mutableStateOf("") }
    var tinggiBadanAnak by remember { mutableStateOf("") }
    var beratBadanAnak by remember { mutableStateOf("") }
    var jenisKelaminAnak by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Data Anak",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00B8D4)
            )
            Spacer(modifier = Modifier.height(16.dp))

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
                label = { Text("Usia Anak (bulan)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tinggiBadanAnak,
                onValueChange = { tinggiBadanAnak = it },
                label = { Text("Tinggi Badan (cm)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = beratBadanAnak,
                onValueChange = { beratBadanAnak = it },
                label = { Text("Berat Badan (kg)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = jenisKelaminAnak,
                onValueChange = { jenisKelaminAnak = it },
                label = { Text("Jenis Kelamin (Male/ Female)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val request = PredictRequest(
                        name = namaAnak,
                        age = usiaAnak,
                        height = tinggiBadanAnak,
                        weight = beratBadanAnak,
                        gender = jenisKelaminAnak
                    )
                    predictViewModel.predictStunting(request)
                    predictViewModel.predictSimilarity(request)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B8D4))
            ) {
                Text(text = "Cek Status", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun ResultCard(predictResult: PredictResponse?, errorMessage: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Hasil Status Anak",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00B8D4)
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (predictResult != null) {
                Text(text = "Status: ${predictResult.data?.status ?: "Tidak diketahui"}")
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Prediksi Perkembangan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Nama: ${predictResult.data?.name ?: "-"}")
                Text(text = "Usia: ${predictResult.data?.age ?: "-"}")
                Text(text = "Tinggi: ${predictResult.data?.height ?: "-"}")
                Text(text = "Berat: ${predictResult.data?.weight ?: "-"}")
                Text(text = "Jenis Kelamin: ${predictResult.data?.gender ?: "-"}")
            } else if (!errorMessage.isNullOrEmpty()) {
                Text(text = "Error: $errorMessage", color = Color.Red)
            } else {
                Text(text = "Belum ada hasil prediksi.")
            }


        }
    }
}
@Composable
fun SimilarCasesCard(similarCasesResult: PredictResponse?, errorMessage: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Kasus Serupa",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00B8D4)
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (similarCasesResult != null) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Nama: ${similarCasesResult.data?.name}", fontWeight = FontWeight.Bold)
                    Text(text = "Usia: ${similarCasesResult.data?.age}")
                    Text(text = "Tinggi: ${similarCasesResult.data?.height} cm")
                    Text(text = "Berat: ${similarCasesResult.data?.weight} kg")
                    Text(text = "Jenis Kelamin: ${similarCasesResult.data?.gender}")
                    Text(text = "Status: ${similarCasesResult.data?.status}", color = Color.Red)
                }
            } else if (!errorMessage.isNullOrEmpty()) {
                Text(text = "Error: $errorMessage", color = Color.Red)
            } else {
                Text(text = "Memproses data, harap tunggu...")
            }
        }
    }
}


@Composable
fun BottomNavigationBars(modifier: Modifier = Modifier, navController: NavController) {
    BottomNavigation(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("HomePage") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Article, contentDescription = "Check") },
            label = { Text("Check") },
            selected = true,
            onClick = { navController.navigate("StatusCheckScreen") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = false,
            onClick = { navController.navigate("ProfileScreen") }
        )
    }
}




@Preview
@Composable
fun CheckPreview(){
    StatusCheckScreen(navController = rememberNavController())
}