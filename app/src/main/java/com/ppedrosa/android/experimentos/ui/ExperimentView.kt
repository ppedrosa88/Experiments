package com.ppedrosa.android.experimentos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.database.SQLiteHelper
import com.ppedrosa.android.experimentos.data.ExperimentAdapter
import com.ppedrosa.android.experimentos.ui.ExperimentDetailsView.Companion.EXTRA_EXPERIMENT_ID

class ExperimentView : AppCompatActivity(), ExperimentAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: ExperimentAdapter? = null
    private var categoryId: Int = -1
    private lateinit var fabSearch: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiment)

        categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, -1)
        val categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME)

        if (categoryName != null) {
            Log.e(null,categoryName)
        } else {
            Log.e(null, "error")
        }

        if (categoryId == -1) {
            finish()
        } else {
            initView()
            textView.text = categoryName
            initRecyclerView()
            fabSearch.setOnClickListener {
                val intent = Intent(this, ExperimentSearchView::class.java)
                startActivity(intent)
            }
            sqliteHelper = SQLiteHelper(this)
            adapter?.setOnItemClickListener(this)
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "category_id"
        const val EXTRA_CATEGORY_NAME = "category_name"
    }

    private fun initView() {
        recyclerView = findViewById(R.id.ExperimentRecycler)
        textView = findViewById(R.id.CategoryTV)
        fabSearch = findViewById(R.id.fab_search)
    }

    private fun getExperiments(id: Int) {
        val experimentList = sqliteHelper.getExperimentsByCategoryId(id)
        adapter?.addItems(experimentList)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        adapter = ExperimentAdapter()
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getExperiments(categoryId)
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, ExperimentDetailsView::class.java).apply {
            putExtra(EXTRA_EXPERIMENT_ID, position)
        }
        startActivity(intent)
    }
}


