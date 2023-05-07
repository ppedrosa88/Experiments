package com.ppedrosa.android.experimentos.database

import java.io.Serializable

data class Category (
    var id: Int?,
    var name: String?,
    var photo: String?,
): Serializable

data class Experiment(
    var id: Int?,
    var name: String?,
    var category_id : Int?,
    var instruction: String?,
    var explanation: String?,
    var observation: String?,
    var advice: String?,
    var warning: String?,
    var technique: String?,
    var challenge: String?,
    var photo_url: String?
): Serializable {
    class Experiment constructor()
}

data class Experiment_Material(
    var experiment_id:Int?,
    var material_id:Int?,
    var quantity:String?
): Serializable

data class Material(
    var id:Int?,
    var material:String?
): Serializable

