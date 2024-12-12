package com.example.projectcapstone

import android.content.Intent
import android.net.Uri
import android.preference.PreferenceActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AboutUsScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ) {
        // Header
        Header(navController)

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

        ) {
            Section(
                title = "Our Story",
                content = "We are mentees of Bangkit 2024 batch 2, we come from several different campuses and from several different regions. Bangkit capstone project brought us together as teammates to create a mobile app product about stunting. Of course, distance and time are the main obstacles for us, but we learned a lot from Bangkit capstone project. So, thank you for everything."
            )
            Spacer(modifier = Modifier.height(16.dp))
            ScrollableSection(
                title = "Our Mission",
                content = "Our mission in the capstone project by raising the topic of stunting is as follows:\n" +
                        "- providing education and the latest information on the impacts and characteristics of stunting\n" +
                        "- providing information services related to stunting\n" +
                        "- providing services for checking the nutritional status of toddlers\n" +
                        "- providing information services for toddler nutritional interventions\n" +
                        "- providing development prediction services as parameters for toddler growth and development."
            )
            Spacer(modifier = Modifier.height(16.dp))
            TeamSection()
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Footer
        Footer()
    }
}

@Composable
fun ScrollableSection(title: String,content: String){
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )

    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "About Us",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Row {
            Text(
                text = "Home",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {navController.navigate("Homepage") }
            )
        }
    }
}

@Composable
fun Section(title: String, content: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun TeamSection() {


    val teamMembers = listOf(
        TeamMemberInfo("Retsa", "Mobile Dev", R.drawable.retsa,"https://www.linkedin.com/in/retsa-a-07a097233?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"),
        TeamMemberInfo("Farhat", "Mobile Dev", R.drawable.farhat,"https://www.linkedin.com/in/retsa-a-07a097233?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"),
        TeamMemberInfo("Toha", "Cloud Computing", R.drawable.toha,"https://www.linkedin.com/in/muhammad-toha-ikhsan/"),
        TeamMemberInfo("Hasbi", "Cloud Computing", R.drawable.hasbie1,"https://id.linkedin.com/in/hasbie-syawaluddin-28599124a"),
        TeamMemberInfo("Jofanza", "Machine Learning", R.drawable.jay,"https://www.linkedin.com/in/jofanza-denis-aldida-2a06262a9?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"),
        TeamMemberInfo("Fauzan", "Machine Learning", R.drawable.fauzan,"https://www.linkedin.com/in/fauzanafif?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app"),
        TeamMemberInfo("Brendha", "Machine Learning", R.drawable.brendha,"https://www.linkedin.com/in/brendha-adiyan-vianca-3876952a1?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app")
    )

    Column {
        Text(
            text = "Our Team",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val rows = teamMembers.chunked(3)
        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { member ->
                    member.url?.let {
                        TeamMember(
                            name = member.name,
                            role = member.role,
                            imageRes = member.imageRes,
                            url = it
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun TeamMember(name: String, role: String, imageRes: Int, url: String) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)) // Membuat intent untuk membuka URL
                    context.startActivity(intent) // Membuka tautan di browser
                }
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Profile picture of $name",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = role,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
@Composable
fun Footer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "© 2023 Our Company. All rights reserved",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "V 1.0.1",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
data class TeamMemberInfo(
    val name: String,
    val role: String,
    val imageRes: Int,
    val url: String? = null // URL bisa bersifat opsional
)

@Preview(showBackground = true)
@Composable
fun PreviewAboutUsScreen() {
    AboutUsScreen(navController = rememberNavController())
}
