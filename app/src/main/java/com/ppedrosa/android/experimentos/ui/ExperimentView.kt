package com.ppedrosa.android.experimentos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.database.SQLiteHelper
import com.ppedrosa.android.experimentos.data.ExperimentAdapter

import com.ppedrosa.android.experimentos.ui.ExperimentDetailsView.Companion.EXTRA_EXPERIMENT_ID

class ExperimentView : AppCompatActivity(), ExperimentAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: ExperimentAdapter? = null
    private var categoryId: Int = -1
    private lateinit var fabSearch: FloatingActionButton
    private var listener: ExperimentAdapter.onItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiment)

        val categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, -1)
        this.categoryId = categoryId

        if (categoryId == -1) {
            finish()
        } else {
            initView()
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
    }

    private fun initView() {
        recyclerView = findViewById(R.id.ExperimentRecycler)
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

    override fun onItemClick(id: Int) {
        val intent = Intent(this, ExperimentDetailsView::class.java).apply {
            putExtra(EXTRA_EXPERIMENT_ID, id)
        }
        startActivity(intent)
    }


}


