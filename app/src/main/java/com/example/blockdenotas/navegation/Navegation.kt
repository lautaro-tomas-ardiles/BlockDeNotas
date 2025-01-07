package com.example.blockdenotas.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(
            route = "note/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1 // Usamos -1 como indicador de "nueva nota"
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            MainNote(
                navController = navController,
                noteId = if (noteId == -1) null else noteId // Convierte -1 a null para notas nuevas
            )
        }
    }
}