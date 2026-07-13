package com.example.mandahinog

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mandahinog.ui.theme.MandahinogTheme


private object Brand {
    val Background = Color(0xFFF4F2F1)
    val Primary = Color(0xFF771C1B)
    val AvatarSize = 120.dp
    val BorderWidth = 2.dp
    val NameSize = 24.sp
    val TitleSize = 16.sp
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MandahinogTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Brand.Background
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand.Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.padding(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Avatar()

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Jhan Mandahinog",
                    fontSize = Brand.NameSize,
                    fontWeight = FontWeight.Bold,
                    color = Brand.Primary
                )
                Text(
                    text = "student",
                    fontSize = Brand.TitleSize,
                    color = Color.DarkGray
                )

                Spacer(Modifier.height(24.dp))
                ContactRow(
                    icon = Icons.Default.Phone,
                    label = "+63 900 000 0000",
                    onClickLabel = "Call phone number"
                ) { /* TODO: launch dialer intent */ }

                ContactRow(
                    icon = Icons.Default.Email,
                    label = "jhan@example.com",
                    onClickLabel = "Send email"
                ) { /* TODO: launch email intent */ }

                ContactRow(
                    icon = Icons.Default.LocationOn,
                    label = "Cagayan de Oro City, PH",
                    onClickLabel = "Open location"
                ) { /* TODO: launch maps intent */ }
            }
        }
    }
}

@Composable
fun Avatar() {
    Image(
        painter = painterResource(id = R.drawable.unnamed),
        contentDescription = "Avatar Image",
        modifier = Modifier
            .size(Brand.AvatarSize)
            .clip(CircleShape)
            .border(Brand.BorderWidth, Color.White, CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ContactRow(
    icon: ImageVector,
    label: String,
    onClickLabel: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .clickable(onClickLabel = onClickLabel) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Brand.Primary
        )
        Spacer(Modifier.width(8.dp))
        Text(label)
    }
}

@Preview(name = "Card - Light", showBackground = true, widthDp = 360)
@Composable
fun BusinessCardPreviewLight() {
    MandahinogTheme {
        BusinessCard()
    }
}

@Preview(
    name = "Card - Dark",
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BusinessCardPreviewDark() {
    MandahinogTheme {
        BusinessCard()
    }
}
