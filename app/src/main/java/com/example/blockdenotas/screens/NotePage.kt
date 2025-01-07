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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blockdenotas.data.base.DataBase
import com.example.blockdenotas.data.base.DataNote
import com.example.blockdenotas.ui.theme.black20
import com.example.blockdenotas.ui.theme.blue10
import com.example.blockdenotas.ui.theme.green10
import com.example.blockdenotas.ui.theme.orange10

@Composable
fun DropDownMenuColors(
    backgroundColor: Color,
    onClick: () -> Unit,
    isColorSelected: Boolean
) {
    DropdownMenuItem(
        onClick = {
            onClick()
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
fun DropDownMenuFont(
    fontSize: Int,
    onClick: () -> Unit,
    isFontSizeSelected: Boolean
) {
    val label = when (fontSize) {
        15 -> "pequeña"
        20 -> "mediana"
        25 -> "grande"
        else -> "special"
    }

    DropdownMenuItem(
        onClick = { onClick() },
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
fun TopAppBarNote(
    data: DataNote,
    id: Int,
    navController: NavController,
    onColorChange: (Color) -> Unit
) {

    val db = DataBase(LocalContext.current)

    var title by remember { mutableStateOf(data.title) }

    var focus by remember { mutableStateOf(false) }
    var settingState by remember { mutableStateOf(false) }

    var colorState by remember { mutableStateOf(false) }
    var colorSelected by remember { mutableIntStateOf(1) }
    var color by remember { mutableStateOf("") }

    var fontState by remember { mutableStateOf(false) }
    var fontSelected by remember { mutableIntStateOf(2) }
    var fontSize by remember { mutableIntStateOf(data.fontSize) }

    fontSelected = when (fontSize) {
        15 -> 1
        20 -> 2
        25 -> 3
        else -> 4
    }

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
                onClick = {
                    when (id) {
                       -1 -> {
                           db.insertData(
                               title,
                               data.content,
                               color,
                               fontSize,
                           )
                           navController.popBackStack()
                       }
                        else -> {
                            db.updateData(
                                id,
                                title,
                                data.content,
                                color,
                                fontSize
                            )
                            navController.popBackStack()
                        }
                    }
                },
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
                        onColorChange(black20)
                        color = "black"
                        colorSelected = 1
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = green10,
                    isColorSelected = colorSelected == 2,
                    onClick = {
                        onColorChange(green10)
                        color = "green"
                        colorSelected = 2
                        colorState = !colorState
                    }
                )
                DropDownMenuColors(
                    backgroundColor = orange10,
                    isColorSelected = colorSelected == 3,
                    onClick = {
                        onColorChange(orange10)
                        color = "orange"
                        colorSelected = 3
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = blue10,
                    isColorSelected = colorSelected == 4,
                    onClick = {
                        onColorChange(blue10)
                        color = "blue"
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
                        fontState = !fontState
                    }
                )

                DropDownMenuFont(
                    fontSize = 20,
                    isFontSizeSelected = fontSelected == 2,
                    onClick = {
                        fontSize = 20
                        fontState = !fontState
                    }
                )

                DropDownMenuFont(
                    fontSize = 25,
                    isFontSizeSelected = fontSelected == 3,
                    onClick = {
                        fontSize = 25
                        fontState = !fontState
                    }
                )
            }
        },
        colors = TopAppBarColors(
            containerColor =
            if (colorSelected == 2)
                blue10
            else
                green10,
            actionIconContentColor = black20,
            navigationIconContentColor = black20,
            titleContentColor = black20,
            scrolledContainerColor = green10
        )
    )
}

@Composable
fun NoteBody(data: DataNote,  backgroundColor: Color) {

    var content by remember { mutableStateOf(data.content) }

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
            onValueChange = {
                content = it
                data.content = it
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor =
                if (backgroundColor == orange10 || backgroundColor == green10)
                    Color.Black
                else
                    Color.White,
                unfocusedTextColor =
                if (backgroundColor == orange10 || backgroundColor == green10)
                    Color.Black
                else
                    Color.White,
                cursorColor = Color.White
            ),
            modifier = Modifier
                .fillMaxSize(),
            textStyle = TextStyle(
                fontSize = data.fontSize.sp
            )
        )
    }
}

@Composable
fun MainNote(navController: NavController, id: Int) {

    val db = DataBase(LocalContext.current)
    var data by remember { mutableStateOf<DataNote?>(null) }

    data = when (id) {
        -1 -> {
            DataNote(
                0,
                "",
                "",
                "black",
                20
            )
        }
        else -> db.getDataById(id)
    }

    var backgroundColor by remember {
        mutableStateOf(
            when (data!!.backgroundColor) {
                "blue" -> blue10
                "black" -> black20
                "green" -> green10
                "orange" -> orange10
                else -> black20
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBarNote(
                data!!,
                id,
                navController
            ) {
                color -> backgroundColor = color
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            NoteBody(
                data!!,
                backgroundColor
            )
        }
    }
}
