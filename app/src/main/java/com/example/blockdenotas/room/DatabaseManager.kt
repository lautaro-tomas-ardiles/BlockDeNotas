
package com.example.blockdenotas.room

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseManager(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "notas-db"
    ).build()
    private val notaDao: NotasDAO = database.notasDao()

    fun insertNota(nota: Notas) {
        CoroutineScope(Dispatchers.IO).launch {
            notaDao.insert(nota)
        }
    }

    fun getAllNotas(callback: (List<Notas>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val notas = notaDao.getAllNotas()
            callback(notas)
        }
    }

    fun getNotaById(id: Int, callback: (Notas?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val nota = notaDao.getNotaById(id)
            callback(nota)
        }
    }

    fun getNotaByTitulo(titulo: String, callback: (Notas?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val nota = notaDao.getNotaByTitulo(titulo)
            callback(nota)
        }
    }

    fun deleteNotaById(id: Int, callback: (Notas?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val nota = notaDao.deleteNotaById(id)
            callback(nota)
        }
    }
}
