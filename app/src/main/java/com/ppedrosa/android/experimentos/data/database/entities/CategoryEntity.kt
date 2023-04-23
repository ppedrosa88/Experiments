package com.ppedrosa.android.experimentos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
// import androidx.room.ForeignKey
import androidx.room.PrimaryKey
// import java.util.Locale.Category

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id") val category_id: Int,
    @ColumnInfo(name = "category_name") val category_name: String
)

/*@Entity(tableName = "experiments",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["experiment_id"],
            childColumns = ["category_id"]
        )
    ])

data class Experiment(
    @PrimaryKey
    @ColumnInfo(name = "experiment_id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "instruction") val instruction: String,
    @ColumnInfo(name = "explanation") val explanation: String,
    @ColumnInfo(name = "observation") val observation: String,
    @ColumnInfo(name = "advice") val advice: String,
    @ColumnInfo(name = "warning") val warning: String,
    @ColumnInfo(name = "technique") val technique: String,
    @ColumnInfo(name = "challenge") val challenge: String,
    @ColumnInfo(name = "category_id") val categoryId: String
)

@Entity(tableName = "experiment_material",
    foreignKeys = [
        ForeignKey(
            entity = Experiment::class,
            parentColumns = ["experiment_id"],
            childColumns = ["experiment_id"]
        ),
        ForeignKey(
            entity = Material::class,
            parentColumns = ["material_id"],
            childColumns = ["material_id"]
        )
    ])
data class ExperimentMaterial(
    @ColumnInfo(name = "experiment_id") val id: Int,
    @ColumnInfo(name = "quantity") val quantity: String,
    @ColumnInfo(name = "material_id") val materialId: Int
)

@Entity(tableName = "material")
data class Material(
    @PrimaryKey
    @ColumnInfo(name = "material_id") val id: Int,
    @ColumnInfo(name = "material") val material: String
)*/