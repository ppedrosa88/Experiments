package com.ppedrosa.android.experimentos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ppedrosa.android.experimentos.data.database.dao.CategoryDao
import com.ppedrosa.android.experimentos.data.database.entities.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDatabase: RoomDatabase() {
    abstract fun getCategoryDao():CategoryDao

    companion object {
        private var INSTANCE: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CategoryDatabase::class.java, "experimentsDB.db"
                    ).createFromAsset("database/experimentsDB.db").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}