package com.ppedrosa.android.experimentos.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream


class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "experimentos.db"
        private const val CATEGORY = "category"
        private const val EXPERIMENT = "experiment"
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
            val database: SQLiteDatabase? = null
            val createTableCategory = ("CREATE TABLE " + CATEGORY + "("
                    + ID + "INTEGER PRIMARY KEY, " + NAME + "TEXT, " + PHOTO + "TEXT);")

            database?.execSQL(createTableCategory)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CATEGORY")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllCategories(): ArrayList<Category> {
        val catList: ArrayList<Category> = ArrayList()
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
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                photo = cursor.getString(cursor.getColumnIndex("photo_url"))


                val category = Category(id = id, name = name, photo = photo)
                catList.add(category)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return catList

    }

    @SuppressLint("Range")
    fun getExperimentById(exp_id: Int): Experiment? {
        var experiment: Experiment? = null
        val selectQuery = "SELECT * FROM $EXPERIMENT WHERE id= $exp_id "
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }

        var id: Int
        var name: String
        var category_id: Int?
        var instruction: String?
        var explanation: String?
        var observation: String?
        var advice: String?
        var warning: String?
        var technique: String?
        var challenge: String?
        var photo_url: String?

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                category_id = cursor.getInt(cursor.getColumnIndex("category_id"))
                instruction = cursor.getString(cursor.getColumnIndex("Instruction"))
                explanation = cursor.getString(cursor.getColumnIndex("Explanation"))
                observation = cursor.getString(cursor.getColumnIndex("Observation"))
                advice = cursor.getString(cursor.getColumnIndex("Advice"))
                warning = cursor.getString(cursor.getColumnIndex("Warning"))
                technique = cursor.getString(cursor.getColumnIndex("Technique"))
                challenge = cursor.getString(cursor.getColumnIndex("Challenge"))
                photo_url = cursor.getString(cursor.getColumnIndex("Photo_url"))

                experiment = Experiment(
                    id,
                    name,
                    category_id,
                    instruction,
                    explanation,
                    observation,
                    advice,
                    warning,
                    technique,
                    challenge,
                    photo_url
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return experiment
    }


    @SuppressLint("Range")
    fun getExperimentsByCategoryId(cat_id: Int): ArrayList<Experiment> {
        val experimentList: ArrayList<Experiment> = ArrayList()
        val selectQuery =
            "SELECT experiment.id, experiment.name, experiment.photo_url FROM $EXPERIMENT WHERE category_id= $cat_id "
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
        var photo_url: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                if (!cursor.isNull(cursor.getColumnIndex("Photo_url"))) {
                    photo_url = cursor.getString(cursor.getColumnIndex("Photo_url"))
                } else {
                    photo_url = null.toString()
                }

                val experiment = Experiment(
                    id,
                    name,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    photo_url,
                )
                experimentList.add(experiment)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return experimentList
    }

    @SuppressLint("Range")
    fun getMaterialsByExperimentsId(expId: Int): Map<String, String>? {
        val materialMap = mutableMapOf<String, String>()
        val selectQuery = "SELECT material.material, experiment_material.quantity \n" +
                "FROM experiment_material \n" +
                "JOIN material ON experiment_material.material_id = material.id \n" +
                "WHERE experiment_material.experiment_id = $expId;"

        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }

        var name: String
        var quantity: String

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("material"))
                quantity = cursor.getString(cursor.getColumnIndex("quantity"))

                materialMap.put(name, quantity)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return materialMap
    }


    @SuppressLint("Range")
    fun searchExperimentsByMaterials(materials: List<String>): List<Experiment> {
        val db = this.readableDatabase
        val conditions = materials.joinToString(" OR ") { "material LIKE '%${it}%'" }
        // val nameConditions = materials.joinToString(" OR ") { "name LIKE '%${it}%'" }
        // (name LIKE nameConditions
        val selectQuery = "SELECT * FROM experiment WHERE id IN (SELECT experiment_id FROM experiment_material WHERE material_id IN (SELECT id FROM material WHERE $conditions))"
        val experiments = ArrayList<Experiment>()
        val cursor: Cursor?

        try {

                cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val experiment = Experiment(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getInt(cursor.getColumnIndex("category_id")),
                    null,
                    null,
                    null,
                    null,
                    null,null,
                    null,
                    cursor.getString(cursor.getColumnIndex("Photo_url"))
                )
                experiments.add(experiment)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return experiments
    }
}