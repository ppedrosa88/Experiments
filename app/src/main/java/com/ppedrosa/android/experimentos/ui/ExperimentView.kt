package com.ppedrosa.android.experimentos.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.database.SQLiteHelper
import com.ppedrosa.android.experimentos.data.ExperimentAdapter
import com.ppedrosa.android.experimentos.ui.ScrollingActivity.Companion.EXTRA_EXPERIMENT_ID

class ExperimentView : AppCompatActivity(), ExperimentAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: ExperimentAdapter? = null
    private var categoryId: Int = -1

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
            sqliteHelper = SQLiteHelper(this)
            adapter?.setOnItemClickListener(this)
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "category_id"
    }

    private fun initView() {
        recyclerView = findViewById(R.id.ExperimentRecycler)
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
        Log.e(null,categoryId.toString())
        getExperiments(categoryId)
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(this, ScrollingActivity::class.java).apply {
            putExtra(EXTRA_EXPERIMENT_ID, id)
        }
        startActivity(intent)    }

}


