package com.ppedrosa.android.experimentos.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.data.CategoryAdapter
import com.ppedrosa.android.experimentos.data.ExperimentAdapter
import com.ppedrosa.android.experimentos.database.SQLiteHelper
import com.ppedrosa.android.experimentos.ui.ExperimentView.Companion.EXTRA_CATEGORY_ID
import com.ppedrosa.android.experimentos.ui.ExperimentView.Companion.EXTRA_CATEGORY_NAME

class CategoryView : AppCompatActivity(), CategoryAdapter.onItemClickListener,
    ExperimentAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: CategoryAdapter? = null
    private lateinit var fabSearch: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_view)


        initView()
        initRecyclerView()

        fabSearch.setOnClickListener {
            val intent = Intent(this, ExperimentSearchView::class.java)
            startActivity(intent)
        }

        sqliteHelper = SQLiteHelper(this)
        adapter?.setOnItemClickListener(this)
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
        fabSearch = findViewById(R.id.fab_search)
    }

    override fun onItemClick(position: Int) {
        val categoryName = adapter?.categoryList?.getOrNull(position)?.name
        categoryName?.let{
            val intent = Intent(this, ExperimentView::class.java).apply {
                putExtra(EXTRA_CATEGORY_ID, position+1)
                putExtra(EXTRA_CATEGORY_NAME, categoryName)
            }
            startActivity(intent)
        }
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
