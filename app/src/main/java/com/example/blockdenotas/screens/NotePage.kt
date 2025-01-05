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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.blockdenotas.data.base.DataBase
import com.example.blockdenotas.data.base.ViewModelNote
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
fun TopAppBarNote(noteViewModel: ViewModelNote) {

    val db = DataBase(LocalContext.current)

    val title = noteViewModel.title.observeAsState("")
    val backgroundColor = noteViewModel.backgroundColor.observeAsState(initial = black20)

    var focus by remember { mutableStateOf(false) }
    var settingState by remember { mutableStateOf(false) }

    var colorState by remember { mutableStateOf(false) }
    var colorSelected by remember { mutableIntStateOf(1) }

    var fontState by remember { mutableStateOf(false) }
    var fontSelected by remember { mutableIntStateOf(2) }

    TopAppBar(
        title = {
            OutlinedTextField(
                value = title.value,
                onValueChange = { noteViewModel.updateTitle(it) },
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
                    db.insertData(
                        title = title.value,
                        content = noteViewModel.content.value!!,
                        backgroundColor = backgroundColor.value.toString(),
                        fontSize = noteViewModel.fontSize.value!!
                    )
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
                        noteViewModel.updateBackgroundColor(black20)
                        colorSelected = 1
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = green10,
                    isColorSelected = colorSelected == 2,
                    onClick = {
                        noteViewModel.updateBackgroundColor(green10)
                        colorSelected = 2
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = orange10,
                    isColorSelected = colorSelected == 3,
                    onClick = {
                        noteViewModel.updateBackgroundColor(orange10)
                        colorSelected = 3
                        colorState = !colorState
                    }
                )

                DropDownMenuColors(
                    backgroundColor = blue10,
                    isColorSelected = colorSelected == 4,
                    onClick = {
                        noteViewModel.updateBackgroundColor(blue10)
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
                        noteViewModel.updateFontSize(15)
                        fontSelected = 1
                        fontState = !fontState
                    }
                )

                DropDownMenuFont(
                    fontSize = 20,
                    isFontSizeSelected = fontSelected == 2,
                    onClick = {
                        noteViewModel.updateFontSize(20)
                        fontSelected = 2
                        fontState = !fontState
                    }
                )

                DropDownMenuFont(
                    fontSize = 25,
                    isFontSizeSelected = fontSelected == 3,
                    onClick = {
                        noteViewModel.updateFontSize(25)
                        fontSelected = 3
                        fontState = !fontState
                    }
                )
            }
        },
        colors = TopAppBarColors(
            containerColor =
            if (backgroundColor.value == green10)
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
fun NoteBody(noteViewModel: ViewModelNote) {
    val content = noteViewModel.content.observeAsState("")
    val backgroundColor = noteViewModel.backgroundColor.observeAsState()

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
            value = content.value,
            onValueChange = { noteViewModel.updateContent(it) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor =
                if (backgroundColor.value == orange10 || backgroundColor.value == green10)
                    Color.Black
                else
                    Color.White,
                unfocusedTextColor =
                if (backgroundColor.value == orange10 || backgroundColor.value == green10)
                    Color.Black
                else
                    Color.White,
                cursorColor = Color.White
            ),
            modifier = Modifier
                .fillMaxSize(),
            textStyle = TextStyle(
                fontSize = noteViewModel.fontSize.value!!.sp
            )
        )
    }
}

@Composable
fun MainNote(navController: NavController, noteViewModel: ViewModelNote = viewModel()) {
    val backgroundColor = noteViewModel.backgroundColor.observeAsState()

    Scaffold(
        topBar = {
            TopAppBarNote(noteViewModel)
        },
        containerColor = backgroundColor.value!!
    ) {
        innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            NoteBody(noteViewModel)
        }
    }
}
