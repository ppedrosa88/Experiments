package com.ppedrosa.android.experimentos.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.data.ExperimentSearchAdapter
import com.ppedrosa.android.experimentos.database.Experiment
import com.ppedrosa.android.experimentos.database.SQLiteHelper


class ExperimentSearchView : AppCompatActivity(), ExperimentSearchAdapter.OnItemClickListener {
    private lateinit var searchView: SearchView
    private lateinit var chipGroup: ChipGroup
    private lateinit var recyclerView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var experimentSearchAdapter: ExperimentSearchAdapter

    private val materialList = ArrayList<String>()
    private var experimentList = ArrayList<Experiment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view)

        searchView= findViewById(R.id.searchView)
        chipGroup = findViewById(R.id.chipMaterialGroup)
        recyclerView = findViewById(R.id.recyclerSearchView)
        sqliteHelper = SQLiteHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        experimentSearchAdapter = ExperimentSearchAdapter(experimentList, this)
        recyclerView.adapter = experimentSearchAdapter

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { it:String ->
                    materialList.add(it)
                    val chip = Chip(this@ExperimentSearchView)
                    chip.text = it
                    chip.setTextColor(Color.WHITE)
                    chip.isCloseIconVisible = true
                    chip.setChipBackgroundColorResource(R.color.md_light_green_500)
                    try {
                        chipGroup.addView(chip)
                    } catch (e: Exception) {
                        Log.e(null, e.toString())
                    }
                    chip.setOnCloseIconClickListener {
                        val materialToRemove = chip.text.toString()
                        materialList.remove(materialToRemove)
                        chipGroup.removeView(chip)
                        if (materialList.isNotEmpty()) {
                            experimentList = sqliteHelper.searchExperimentsByMaterials(materialList) as ArrayList<Experiment>
                            experimentSearchAdapter.addItems(experimentList)
                        } else {
                            experimentList.clear()
                        }
                        experimentSearchAdapter.notifyDataSetChanged()
                    }
                    experimentList = sqliteHelper.searchExperimentsByMaterials(materialList) as ArrayList<Experiment>
                    experimentSearchAdapter.addItems(experimentList)
                    searchView.setQuery("", false)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, ExperimentDetailsView::class.java).apply {
            putExtra(ExperimentDetailsView.EXTRA_EXPERIMENT_ID, position)
        }
        startActivity(intent)
    }
}
