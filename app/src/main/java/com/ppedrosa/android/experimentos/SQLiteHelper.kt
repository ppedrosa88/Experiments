package com.ppedrosa.android.experimentos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class SQLiteHelper(context: Context)  : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "experimentos.db"
        private const val CATEGORY = "category"
        private const val ID = "id"
        private const val NAME = "name"
        private const val PHOTO = "photo_url"

    }

    init {
        // Copiar la base de datos desde assets al almacenamiento interno al crear una instancia de SQLiteHelper
        copyDatabaseFromAssets(context)
    }


    private fun copyDatabaseFromAssets(context: Context) {
        val databasePath = context.getDatabasePath(DATABASE_NAME)
        print(databasePath)
        if (databasePath.exists()) {
            // La base de datos ya está copiada, no es necesario copiarla nuevamente
            return
        }

        databasePath.parentFile?.mkdirs()
        databasePath.createNewFile()

        val inputStream = context.assets.open("database/$DATABASE_NAME")
        val outputStream = FileOutputStream(databasePath)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) {
            val createTableCategory = ("CREATE TABLE " + CATEGORY + "("
                    + ID + "INTEGER PRIMARY KEY, " + NAME + "TEXT, " + PHOTO + "TEXT);")

            db?.execSQL(createTableCategory)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CATEGORY")
        onCreate(db)
    }

    fun insertCategory(category: CategoryModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, category.id)
        contentValues.put(NAME, category.name)

        val success = db.insert(CATEGORY, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllCategories(): ArrayList<CategoryModel>{
        val catList: ArrayList<CategoryModel> = ArrayList()
        val selectQuery = "SELECT * FROM $CATEGORY"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var photo: String?

        if (cursor.moveToFirst()) {
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                photo = cursor.getString(cursor.getColumnIndex("photo_url"))

                val category = CategoryModel(id = id, name = name, photo = photo)
                catList.add(category)
            } while (cursor.moveToNext())
        }

        return catList

    }
}