package com.example.blockdenotas.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.blockdenotas.screens.MainNote
import com.example.blockdenotas.screens.MainPage

@Composable
fun AppNavegation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_page") {
        composable("main_page") {
            MainPage(navController)
        }
        composable(
            route = "note_page/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")

            if (noteId != null) {
                MainNote(navController, noteId)
            }
        }
    }

}