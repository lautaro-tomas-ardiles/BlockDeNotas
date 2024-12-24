package com.example.blockdenotas.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Notas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "titulo") val titulo: String?,
    @ColumnInfo(name = "contenido") val contenido: String?,
    @ColumnInfo(name = "colores") val color: String?,
    @ColumnInfo(name = "tamañoDeTexto") val tamañoDeTexto: Int?
)
