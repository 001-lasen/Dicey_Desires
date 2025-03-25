package com.example.dicey_desires.homepage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dicey_desires.about_page.AboutPage
import com.example.dicey_desires.homepage.ui.theme.Dicey_DesiresTheme
import com.example.dicey_desires.new_game_page.NewGame
import com.example.dicey_desires.R

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dicey_DesiresTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val customFontFamily = FontFamily(
        Font(R.font.allura_regular)
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Dicey Desires",
            fontSize = 56.sp,
            fontFamily = customFontFamily
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Every roll takes you closer to ultimate pleasure… I mean, victory.",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontFamily = customFontFamily
        )
        Spacer(modifier = Modifier.height(64.dp))
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(126.dp))
        Button(
            onClick = {
                val intent = Intent(context, NewGame::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.width(200.dp)
        ) {
            Text(text = "New Game")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val intent = Intent(context, AboutPage::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.width(200.dp)
        ) {
            Text(text = "About")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Dicey_DesiresTheme {
        Content()
    }
}