package com.example.blockdenotas.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blockdenotas.screens.MainNote
import com.example.blockdenotas.screens.MainPage

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Note : Screen("note")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainPage(navController)
        }
        composable(Screen.Note.route) {
            MainNote(navController)
        }
    }
}