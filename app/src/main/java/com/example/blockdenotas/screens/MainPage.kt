@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.blockdenotas.screens

import android.util.MutableBoolean
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blockdenotas.data.base.DataBase
import com.example.blockdenotas.data.base.DataNote
import com.example.blockdenotas.navegation.appScreen
import com.example.blockdenotas.ui.theme.black10
import com.example.blockdenotas.ui.theme.black20
import com.example.blockdenotas.ui.theme.blue10
import com.example.blockdenotas.ui.theme.green10
import com.example.blockdenotas.ui.theme.orange10

@Composable
fun DivisorWithText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            color = Color.Gray,
            modifier = Modifier.weight(0.07f)
        )

        Text(
            text = "Notas",
            modifier = Modifier.padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 23.sp
        )

        HorizontalDivider(
            color = Color.Gray,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit){

    var busqueda by remember { mutableStateOf("") }
    var focus by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(
                bottom = 20.dp,
                top = 50.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = {
                if (!focus) {
                    Text(
                        "busqueda de notas",
                        color = black10,
                        fontSize = 18.sp
                    )
                }
            },
            shape = RoundedCornerShape(100),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = green10,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = black10,
                focusedTrailingIconColor = black10,
                unfocusedTrailingIconColor = black10,
                disabledTextColor = black10,
                focusedTextColor = black10,
                unfocusedTextColor = black10
            ),
            trailingIcon = {
                IconButton(
                    onClick = { onSearch(busqueda) },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                        )
                    }
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(busqueda) }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    focus = it.isFocused
                }
        )
    }
}

@Composable
fun NoteCard(
    noteData: DataNote,
    navController: NavController,
    deleteState: Boolean,
    onDelete: (Int) -> Unit
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp

    val db = DataBase(LocalContext.current)

    val cardColor =
        when (noteData.backgroundColor) {
            "blue" -> blue10
            "green" -> green10
            "orange" -> orange10
            else -> blue10
        }

    val colorText =
        when (cardColor) {
            orange10 -> Color.Black
            else -> Color.White
        }

    val borderColor =
        if (deleteState) {
            val gradientColors = when (cardColor) {
                blue10 -> listOf(Color.Yellow, Color.Red)
                green10 -> listOf(Color.Cyan, Color.Yellow)
                orange10 -> listOf(Color.Cyan, Color.Blue)
                else -> listOf(Color.Gray, Color.DarkGray)
            }

            BorderStroke(
                width = 5.dp,
                brush = Brush.horizontalGradient(gradientColors)
            )
        } else {
            BorderStroke(
                width = 0.dp,
                color = Color.Transparent
            )
        }

    Card(
        onClick = {
            if (deleteState) {
                db.deleteById(noteData.id)
                onDelete(noteData.id)
            } else {
                navController.navigate(
                    appScreen.NotePage.createRoute(noteData.id)
                )
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        content = {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(max = (screenWidth / 2) - 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = noteData.title,
                    color = colorText,
                    fontSize = 20.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.padding(2.dp))

                Text(
                    text = noteData.content,
                    color = colorText,
                    fontSize = 15.sp,
                    maxLines = 5
                )
            }
        },
        border = borderColor
    )
}

@Composable
fun MosaicNoteCard(
    noteCards: List<DataNote>,
    navController: NavController,
    deleteState: Boolean,
    onNoteDeleted: (Int) -> Unit
) {
    if (noteCards.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay notas disponibles",
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 10.dp,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp),
            content = {
                items(noteCards) { data ->
                    NoteCard(
                        noteData = data,
                        navController = navController,
                        deleteState = deleteState,
                        onDelete = {
                            deletedId -> onNoteDeleted(deletedId)
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun MainBottomAppBar(
    deleteState: Boolean,
    onDeleteStateChange: (Boolean) -> Unit
){
    BottomAppBar(
        containerColor = green10,
        content = {
            IconButton(
                onClick = {
                    onDeleteStateChange(!deleteState)
                }
            ) {
                if (deleteState) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete2",
                        modifier = Modifier.size(30.dp),
                    )
                }else {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun MainFloatingActionButton(navController: NavController){
    FloatingActionButton(
        onClick = {
            navController.navigate(appScreen.NotePage.createRoute(-1))
        },
        containerColor = orange10,
        content = {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Delete",
                modifier = Modifier.size(40.dp)
            )
        }
    )
}

@Composable
fun MainPage(navController: NavController) {
    val db = DataBase(LocalContext.current)
    var allData by remember { mutableStateOf(db.getAllData()) }

    var deleteState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchBar(
                onSearch = {
                    allData = db.getDataByTitle(it)
                }
            )
        },
        bottomBar = {
            MainBottomAppBar(
                deleteState,
                onDeleteStateChange = {boolean -> deleteState = boolean}
            )
        },
        floatingActionButton = {
            MainFloatingActionButton(navController)
        },
        containerColor = black20
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            DivisorWithText()

            MosaicNoteCard(
                noteCards = allData,
                navController = navController,
                deleteState = deleteState,
                onNoteDeleted = {
                    allData = db.getAllData()
                }
            )
        }
    }
}

