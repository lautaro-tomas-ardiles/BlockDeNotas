package com.example.blockdenotas.data.base

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Nombre de la base de datos y versión
        const val DATABASE_NAME = "notas.db"
        const val DATABASE_VERSION = 1

        // Nombre de las tabla y columnas
        const val TABLE_NAME = "note"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLES = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_BACKGROUND_COLOR = "background_color"
        const val COLUMN_FONT_SIZE = "font_size"
    }

    // creacion de la tabla
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLES TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_BACKGROUND_COLOR TEXT,
                $COLUMN_FONT_SIZE INTEGER
            );
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Si la tabla ya existe, elimínala y créala nuevamente

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //insertar datos
    fun insertData(
        title: String,
        content: String,
        backgroundColor: String,
        fontSize: Int
    ){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITLES, title)
            put(COLUMN_CONTENT, content)
            put(COLUMN_BACKGROUND_COLOR, backgroundColor)
            put(COLUMN_FONT_SIZE, fontSize)
        }

        db.insert(TABLE_NAME, null, values)

        //return db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //obtener datos
    fun getAllData(): List<DataNote> {
        val db = readableDatabase
        val data = mutableListOf<DataNote>()

        val cursor = db.query(
            TABLE_NAME, // Nombre de la tabla.
            null, // Selecciona todas las columnas.
            null, // No hay cláusula WHERE.
            null, // No hay valores para el WHERE.
            null, // No hay agrupación.
            null, // No hay filtro de agrupación.
            null // Sin orden específico.
        )

        if (cursor.moveToFirst()) {
            do {
                val note = DataNote(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLES)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    backgroundColor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKGROUND_COLOR)),
                    fontSize = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FONT_SIZE))
                )
                data.add(note)

            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return data
    }

    //obtener datos por id
    fun getDataById(id: Int): DataNote {
        val db = readableDatabase
        var data = DataNote(
            id = 0,
            title = "",
            content = "",
            backgroundColor = "",
            fontSize = 0
        )

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            TABLE_NAME,   // Nombre de la tabla.
            null, // Selecciona todas las columnas.
            selection,    // cláusula WHERE.
            selectionArgs,// valores para el WHERE.
            null, // No hay agrupación.
            null,  // No hay filtro de agrupación.
            null  // Sin orden específico.
        )

        if (cursor.moveToFirst()) {
            do {
                val note = DataNote(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLES)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    backgroundColor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKGROUND_COLOR)),
                    fontSize = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FONT_SIZE))
                )
                data = note

            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return data
    }

    //obtener datos por titulo
    fun getDataByTitle(title: String): List<DataNote> {
        val db = readableDatabase
        val data = mutableListOf<DataNote>()

        val selection = "$COLUMN_TITLES LIKE ?"
        val selectionArgs = arrayOf("%$title%")

        val cursor = db.query(
            TABLE_NAME,   // Nombre de la tabla.
            null, // Selecciona todas las columnas.
            selection,    // cláusula WHERE.
            selectionArgs,// valores para el WHERE.
            null, // No hay agrupación.
            null,  // No hay filtro de agrupación.
            null  // Sin orden específico.
        )

        if (cursor.moveToFirst()){
            do {
                val note = DataNote(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLES)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    backgroundColor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKGROUND_COLOR)),
                    fontSize = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FONT_SIZE))
                )
                data.add(note)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return data
    }

    // Método para actualizar los datos de una nota
    fun updateData(
        id: Int,
        title: String,
        content: String,
        backgroundColor: String,
        fontSize: Int
    ) {
        val db = writableDatabase

        // Crear los valores que se van a actualizar
        val values = ContentValues().apply {
            put(COLUMN_TITLES, title)
            put(COLUMN_CONTENT, content)
            put(COLUMN_BACKGROUND_COLOR, backgroundColor)
            put(COLUMN_FONT_SIZE, fontSize)
        }

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        // Ejecutar la consulta de actualización
        db.update(TABLE_NAME, values, selection, selectionArgs)
        db.close()
    }

    //borrar datos por id
    fun deleteById(id: Int) {
        val db = writableDatabase

        // Cláusula WHERE para identificar la fila a eliminar
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        // Ejecutar la consulta de eliminación
        db.delete(TABLE_NAME, selection, selectionArgs)

        db.close()
    }
}