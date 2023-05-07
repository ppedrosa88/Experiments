package com.ppedrosa.android.experimentos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.ui.ExperimentView.Companion.EXTRA_CATEGORY_ID
import com.ppedrosa.android.experimentos.data.CategoryAdapter
import com.ppedrosa.android.experimentos.data.ExperimentAdapter
import com.ppedrosa.android.experimentos.database.SQLiteHelper

class CategoryView : AppCompatActivity(), CategoryAdapter.onItemClickListener,
    ExperimentAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_view)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)
        adapter?.setOnItemClickListener(this)
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(this, ExperimentView::class.java).apply {
            putExtra(EXTRA_CATEGORY_ID, id+1)
        }
        startActivity(intent)
    }

    private fun getCategories() {
        val categoryList = sqliteHelper.getAllCategories()
        adapter?.addItems(categoryList)
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = GridLayoutManager(this,1)
        adapter = CategoryAdapter()
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getCategories()
    }
}
