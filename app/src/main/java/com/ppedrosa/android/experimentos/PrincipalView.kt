package com.ppedrosa.android.experimentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PrincipalView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val dataList = mutableListOf<CardItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_view)

        recyclerView = findViewById(R.id.recyclerView)

        // Iniciializar RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this,2)

        // Agregar datos de ejemplo a la lista
        dataList.add(CardItem("Título 1", "Descripción 1", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 2", "Descripción 2", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 3", "Descripción 3", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 4", "Descripción 4", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 5", "Descripción 5", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 6", "Descripción 6", R.drawable.ic_launcher_background))

        // Crear adaptador y asignarlo al RecyclerView

        val adapter = CardAdapter(dataList)
        recyclerView.adapter = adapter

    }
}

data class CardItem(val title: String, val description: String, val imageResource: Int)