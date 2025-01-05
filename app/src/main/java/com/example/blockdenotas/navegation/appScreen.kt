package com.example.blockdenotas.navegation

sealed class appScreen(val route: String){
    object MainPage : appScreen("main_page")
    object NotePage : appScreen("note_page")
}