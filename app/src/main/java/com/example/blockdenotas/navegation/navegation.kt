package com.example.blockdenotas.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blockdenotas.screens.MainNote
import com.example.blockdenotas.screens.MainPage

@Composable
fun AppNavegation(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = appScreen.MainPage.route){
        composable(route = appScreen.MainPage.route) {
            MainPage(navController)
        }
        composable(route = appScreen.NotePage.route){
            MainNote(navController)
        }
    }
}