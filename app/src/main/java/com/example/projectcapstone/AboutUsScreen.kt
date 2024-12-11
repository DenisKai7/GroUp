package com.example.projectcapstone

import android.preference.PreferenceActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AboutUsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Header(navController)

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            Section(
                title = "Our Story",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut id neque vitae nisl facilisis tincidunt."
            )
            Spacer(modifier = Modifier.height(16.dp))
            Section(
                title = "Our Mission",
                content = "Our mission is to leverage technology to make the world a better place."
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
                    .clickable {navController.navigate("Homepage") } // Navigasi ke Homepage
            )
            Text(
                text = "Contacts",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        ) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TeamSection() {
    val teamMembers = listOf(
        Triple("Retsa", "Mobile Dev", R.drawable.retsa),
        Triple("Farhat", "Mobile Dev", R.drawable.farhat),
        Triple("Toha", "Cloud Computing", R.drawable.toha),
        Triple("Hasbi", "Cloud Computing", R.drawable.hasbie),
        Triple("Jofanza", "Machine Learning", R.drawable.jofanza),
        Triple("Fauzan", "Machine Learning", R.drawable.fauzan),
        Triple("Brendha", "Machine Learning", R.drawable.brendha)
    )

    Column {
        Text(
            text = "Our Team",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val rows = teamMembers.chunked(3) // Membagi anggota tim ke dalam grup 3 per baris
        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { member ->
                    TeamMember(
                        name = member.first,
                        role = member.second,
                        imageRes = member.third
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun TeamMember(name: String, role: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Profile picture of $name",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
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

@Preview(showBackground = true)
@Composable
fun PreviewAboutUsScreen() {
    AboutUsScreen(navController = rememberNavController())
}
