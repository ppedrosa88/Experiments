package com.ppedrosa.android.experimentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PrincipalView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_view)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun getCategories() {
        val categoryList = sqliteHelper.getAllCategories()
        adapter?.addItems(categoryList)
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = CategoryAdapter()
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getCategories()
    }
}


/*package com.ppedrosa.android.experimentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PrincipalView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_view)

        recyclerView = findViewById(R.id.recyclerView)

        // Inicializar RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this,2)

        // Agregar datos de ejemplo a la lista
        dataList.add(CardItem("Título 1", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 2", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 3", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 4", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 5", R.drawable.ic_launcher_background))
        dataList.add(CardItem("Título 6", R.drawable.ic_launcher_background))

        // Crear adaptador y asignarlo al RecyclerView

        val adapter = CategoryAdapter()
        recyclerView.adapter = adapter

    }
}

data class CardItem(val title: String, val imageResource: Int)

*/