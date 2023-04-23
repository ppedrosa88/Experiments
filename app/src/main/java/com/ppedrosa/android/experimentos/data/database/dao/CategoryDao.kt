package com.ppedrosa.android.experimentos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ppedrosa.android.experimentos.data.database.entities.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT category_name FROM category")
    suspend fun getCategoriesNames():List<CategoryEntity>

}