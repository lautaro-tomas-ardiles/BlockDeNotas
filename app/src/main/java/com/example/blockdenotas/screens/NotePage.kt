@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.blockdenotas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blockdenotas.ui.theme.black20
import com.example.blockdenotas.ui.theme.blue10
import com.example.blockdenotas.ui.theme.green10
import com.example.blockdenotas.ui.theme.orange10

var globalFontSize = 20

@Composable
fun DropDownMenuColors(backgroundColor: Color, onClick: () -> Unit, isColorSelected: Boolean) {
    DropdownMenuItem(
        onClick = {
            onClick
        },
        text = {
            Box(
                modifier = Modifier
                    .background(backgroundColor)
                    .size(30.dp)
                    .border(
                        width = 2.dp,
                        color = if (isColorSelected) Color.White else Color.Transparent
                    )
            )
        }
    )
}

@Composable
fun DropDownMenuFont(fontSize: Int, onClick: () -> Unit, isFontSizeSelected: Boolean) {
    val label = when (fontSize) {
        15 -> "pequeña"
        20 -> "mediana"
        25 -> "grande"
        else -> "special"
    }

    DropdownMenuItem(
        onClick = { onClick },
        text = {
            Text(
                text = label
            )
        },
        modifier = Modifier.border(
            width = 2.dp,
            color = if (isFontSizeSelected) Color.White else Color.Transparent
        )
    )
}

@Composable
fun TopAppBarNote(onBackgroundColorChange: (Color) -> Unit) {
    var title by remember { mutableStateOf("") }

    var focus by remember { mutableStateOf(false) }
    var settingState by remember { mutableStateOf(false) }

    var colorState by remember { mutableStateOf(false) }
    var colorSelected by remember { mutableIntStateOf(1) }

    var fontState by remember { mutableStateOf(false) }
    var fontSize by remember { mutableIntStateOf(20) }
    var fontSelected by remember { mutableIntStateOf(2) }

    TopAppBar(
        title = {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    if (!focus) {
                        Text(
                            text = "Escriba un titulo...",
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = black20,
                    focusedTextColor = black20,
                    unfocusedTextColor = black20,
                    focusedPlaceholderColor = black20,
                    unfocusedPlaceholderColor = black20
                ),
                modifier = Modifier
                    .onFocusChanged {
                        focus = it.isFocused
                    }
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ },
                content = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            )
        },
        actions = {
            //boton de configuracion
            IconButton(
                onClick = {
                    settingState = !settingState
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings"
                )
            }

            //menu desplegable de configuracion
            DropdownMenu(
                expanded = settingState,
                onDismissRequest = {
                    settingState = !settingState
                }
            ) {
                //item de la configuracion
                DropdownMenuItem(
                    onClick = { colorState = !colorState },
                    text = {
                        Text(
                            text = "Color de fondo"
                        )
                    },
                    trailingIcon = {
                        //icono para desplegar el menu de colores
                        IconButton(
                            onClick = { colorState = !colorState },
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDropDown,
                                    contentDescription = "DROPDOWN",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        )
                    }
                )
                //item de la configuracion
                DropdownMenuItem(
                    onClick = { fontState = !fontState },
                    text = {
                        Text(
                            text = "Tamaño de fuente"
                        )
                    },
                    trailingIcon = {
                        //icono para desplegar el menu de fuentes
                        IconButton(
                            onClick = { fontState = !fontState },
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDropDown,
                                    contentDescription = "DROPDOWN",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        )
                    }
                )
            }

            //menu desplegable de colores
            DropdownMenu(
                expanded = colorState,
                onDismissRequest = { colorState = !colorState }
                //colores de fondo
            ) {

                DropDownMenuColors(
                    backgroundColor = black20,
                    isColorSelected = colorSelected == 1,
                    onClick = {
                        onBackgroundColorChange(black20)
                        colorSelected = 1
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = green10,
                    isColorSelected = colorSelected == 2,
                    onClick = {
                        onBackgroundColorChange(green10)
                        colorSelected = 2
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = orange10,
                    isColorSelected = colorSelected == 3,
                    onClick = {
                        onBackgroundColorChange(orange10)
                        colorSelected = 3
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = blue10,
                    isColorSelected = colorSelected == 4,
                    onClick = {
                        onBackgroundColorChange(blue10)
                        colorSelected = 4
                        colorState = !colorState
                    }
                )
            }

            //menu desplegable de fuentes
            DropdownMenu(
                expanded = fontState,
                onDismissRequest = { fontState = !fontState }
                //fuentes de texto
            ) {
                DropDownMenuFont(
                    fontSize = 15,
                    isFontSizeSelected = fontSelected == 1,
                    onClick = {
                        fontSize = 15
                        fontSelected = 1
                        fontState = !fontState
                    }
                )

                DropDownMenuFont(
                    fontSize = 20,
                    isFontSizeSelected = fontSelected == 2,
                    onClick = {
                        fontSize = 20
                        fontSelected = 2
                        fontState = !fontState
                    }
                )

                DropDownMenuFont(
                    fontSize = 25,
                    isFontSizeSelected = fontSelected == 3,
                    onClick = {
                        fontSize = 25
                        fontSelected = 3
                        fontState = !fontState
                    }
                )
                globalFontSize = fontSize
            }
        },
        colors = TopAppBarColors(
            containerColor = if (colorSelected == 2) blue10 else green10,
            actionIconContentColor = black20,
            navigationIconContentColor = black20,
            titleContentColor = black20,
            scrolledContainerColor = green10
        )
    )
}

@Composable
fun NoteBody(color: Color) {
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(
                top = 10.dp,
                start = 5.dp,
                end = 5.dp
            )
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = if (color == orange10) Color.Black else Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            modifier = Modifier
                .fillMaxSize(),
            textStyle = TextStyle(
                fontSize = globalFontSize.sp
            )
        )
    }
}

@Composable
fun MainNote() {
    var backgroundColor by remember { mutableStateOf(black20) }

    Scaffold(
        topBar = {
            TopAppBarNote(
                onBackgroundColorChange = { newColor ->
                    backgroundColor = newColor
                }
            )
        },
        containerColor = backgroundColor
    ) {
        innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            NoteBody(backgroundColor)
        }
    }
}
