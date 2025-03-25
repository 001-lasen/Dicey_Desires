package com.example.dicey_desires

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.dicey_desires.homepage.HomePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
        finish()
    }
}