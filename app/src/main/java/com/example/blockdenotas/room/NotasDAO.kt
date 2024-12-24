package com.example.blockdenotas.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotasDAO {
    @Insert
    suspend fun insert(notas: Notas)

    @Query("SELECT * FROM notas")
    suspend fun getAllNotas(): List<Notas>

    @Query("SELECT * FROM notas WHERE id = :id")
    suspend fun getNotaById(id: Int): Notas?

    @Query("SELECT * FROM notas WHERE titulo = :titulo")
    suspend fun getNotaByTitulo(titulo: String): Notas?

    @Query("DELETE FROM notas WHERE id = :id")
    suspend fun deleteNotaById(id: Int): Notas?


}