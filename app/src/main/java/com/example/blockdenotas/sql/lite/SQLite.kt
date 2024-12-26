package com.example.blockdenotas.sql.lite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.blockdenotas.screens.NoteCardData

class SQLite(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "notas_data.db"
        const val TABLE_NAME = "notas"
        const val COLUMN_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_CONTENIDO = "contenido"
        const val COLUMN_COLOR_DE_FONDO = "color_de_fondo"
        const val COLUMN_TAMANO_DE_TEXTO = "tamano_de_texto"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = (
                "CREATE TABLE $TABLE_NAME (" +
                        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$COLUMN_TITULO TEXT," +
                        "$COLUMN_CONTENIDO TEXT," +
                        "$COLUMN_COLOR_DE_FONDO TEXT," +
                        "$COLUMN_TAMANO_DE_TEXTO INTEGER" +
                    ")"
                )
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(
        titulo: String,
        contenido: String,
        colorDeFondo: String,
        tamanoDeTexto: Int
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, titulo)
            put(COLUMN_CONTENIDO, contenido)
            put(COLUMN_COLOR_DE_FONDO, colorDeFondo)
            put(COLUMN_TAMANO_DE_TEXTO, tamanoDeTexto)
        }
        val newRowId = db.insert(TABLE_NAME, null, values)
        if (newRowId == -1L) {
            Log.e("SQLite", "Error inserting data into the database")
        } else {
            Log.d("SQLite", "Data inserted successfully. New row ID: $newRowId")
        }
        db.close()

    }

    fun getAllData(): List<NoteCardData> {
        val dataList = mutableListOf<NoteCardData>()
        val db = this.readableDatabase

        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENIDO))
                val backgroundColor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR_DE_FONDO))
                val fontSize = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TAMANO_DE_TEXTO))

                val noteCardData = NoteCardData(id, backgroundColor, title, content, fontSize)
                dataList.add(noteCardData)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return dataList
    }

    fun getDataById(id: Int): Map<String, Any>? {
        val db = this.readableDatabase
        val dataMap: Map<String, Any>?

        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db.rawQuery(selectQuery, selectionArgs)

        if (cursor.moveToFirst()) {
            dataMap = mutableMapOf<String, Any>().apply {
                put(COLUMN_ID, cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                put(COLUMN_TITULO, cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)))
                put(COLUMN_CONTENIDO, cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENIDO)))
                put(COLUMN_COLOR_DE_FONDO, cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR_DE_FONDO)))
                put(COLUMN_TAMANO_DE_TEXTO, cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TAMANO_DE_TEXTO)))
            }
        } else {
            dataMap = null
        }

        cursor.close()
        db.close()
        return dataMap
    }

    fun deleteDataById(id: Int): Boolean {

        val db = this.writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())

        val deletedRows = db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()

        return deletedRows > 0
    }

}