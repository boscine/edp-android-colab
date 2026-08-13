package com.liceo.prelim.profilecard

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mandahinog.R
import com.example.mandahinog.ui.theme.MandahinogTheme

@Composable
fun ProfileScreen() {
    // Task 1 — Center the layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Task 2 — Circular avatar (Updated to use unnamed.jpg)
        Image(
            painter = painterResource(id = R.drawable.unnamed),
                contentDescription = "Profile Picture",
                modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        // Task 3 — Full name & subtitle
        Text(
            text = "Jhan Mandahinog",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "BSIT 3-1",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(24.dp))

        // Task 4 — The Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Task 5 — Reusable InfoRow (×5)
                InfoRow(
                    icon = Icons.Default.Person,
                    label = "Full Name",
                    value = "Jhan Mandahinog"
                )
                InfoRow(
                    icon = Icons.Default.School,
                    label = "Course",
                    value = "BSIT"
                )
                InfoRow(
                    icon = Icons.Default.Class,
                    label = "Section",
                    value = "3-1"
                )
                InfoRow(
                    icon = Icons.Default.Phone,
                    label = "Mobile Number",
                    value = "+63 900 000 0000"
                )
                InfoRow(
                    icon = Icons.Default.Email,
                    label = "Email Address",
                    value = "jlmandahinog65115@liceo.edu.ph"
                )
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Task 6 — Verify light AND dark
@Preview(name = "Profile - Light", showBackground = true)
@Composable
fun ProfilePreviewLight() {
    MandahinogTheme(darkTheme = false) {
        ProfileScreen()
    }
}

@Preview(name = "Profile - Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfilePreviewDark() {
    MandahinogTheme(darkTheme = true) {
        ProfileScreen()
    }
}
