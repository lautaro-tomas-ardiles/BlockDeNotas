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
    NavHost(navController = navController , startDestination = appScreen.MainPage){
        composable(route = appScreen.MainPage.route) {
            MainPage(navController)
        }
        composable(
            route = appScreen.NotePage.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("noteId") ?: -1

            MainNote(navController, id = id)
        }
    }
}