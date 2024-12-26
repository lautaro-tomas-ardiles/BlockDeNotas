@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.blockdenotas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blockdenotas.navegation.Screen
import com.example.blockdenotas.sql.lite.SQLite
import com.example.blockdenotas.ui.theme.black10
import com.example.blockdenotas.ui.theme.black20
import com.example.blockdenotas.ui.theme.blue10
import com.example.blockdenotas.ui.theme.green10
import com.example.blockdenotas.ui.theme.orange10

data class NoteCardData(
    val id: Int = 0, // Add an ID field
    val backGroundColor: String,
    val title: String,
    val content: String,
    val fontSize: Int
)

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
fun SearchBar(){
    var busqueda by remember { mutableStateOf("") }
    var focus by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(bottom = 20.dp, top = 30.dp)
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
                        color = black10
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
                    onClick = { /*TODO*/ },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Delete",
                        )
                    }
                )
            },
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    focus = it.isFocused
                }
        )

    }
}

@Composable
fun NoteCard(noteCardData: NoteCardData){

    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp

    var cardcolor = when (noteCardData.backGroundColor){

        "blue" -> blue10

        "green" -> green10

        "orange" -> orange10

        else -> blue10
    }

    Card(
        onClick = {  },
        colors = CardDefaults.cardColors(
            containerColor = cardcolor
        ),
        content = {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(max = ((screenWidth / 2) - 20.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = noteCardData.title,
                    color = if (noteCardData.backGroundColor == "orange") Color.Black else Color.White,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = noteCardData.content,
                    color = if (noteCardData.backGroundColor == "orange") Color.Black else Color.White,
                    fontSize = 15.sp,
                    maxLines = 10
                )
            }
        }
    )
}

@Composable
fun MosaicNoteCard(noteCards: List<NoteCardData>){

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp),
        content = {
            items(noteCards) { data ->
                NoteCard(noteCardData = data)
            }
        }
    )
}

@Composable
fun MainBottomAppBar(){
    BottomAppBar(
        containerColor = green10,
        content = {
            IconButton(
                onClick = { /*TODO*/ },
                content = {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun MainFloatingActionButton(navController: NavHostController){
    FloatingActionButton(
        onClick = {
            navController.navigate(Screen.Note.route)
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
fun MainPage(navController: NavHostController) {
    val db = SQLite(context = LocalContext.current)
    val allData = db.getAllData()

    Scaffold(
        topBar = {
            //SearchBar()
        },
        bottomBar = {
            MainBottomAppBar()
        },
        floatingActionButton = {
            MainFloatingActionButton(navController)
        },
        containerColor = black20
    ) {
        contentPadding ->

        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))

            DivisorWithText()

            MosaicNoteCard(allData)
        }
    }
}