package com.example.blockdenotas.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notas::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notasDao(): NotasDAO
}