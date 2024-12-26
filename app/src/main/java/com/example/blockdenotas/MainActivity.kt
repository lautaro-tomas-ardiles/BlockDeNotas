package com.example.blockdenotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.blockdenotas.screens.MainNote
import com.example.blockdenotas.ui.theme.BlockDeNotasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlockDeNotasTheme (darkTheme = true) {
                MainNote()
            }
        }
    }
}

