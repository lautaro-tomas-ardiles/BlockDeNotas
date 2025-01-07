package com.example.blockdenotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.blockdenotas.navegation.AppNavegation
import com.example.blockdenotas.screens.MainPage
import com.example.blockdenotas.ui.theme.BlockDeNotasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlockDeNotasTheme (darkTheme = true) {
                AppNavegation()
            }
        }
    }
}

