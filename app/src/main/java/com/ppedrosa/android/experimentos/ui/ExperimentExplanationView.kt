
package com.ppedrosa.android.experimentos.ui

import android.content.ContentValues
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.data.ExperimentAdapter
import com.ppedrosa.android.experimentos.database.SQLiteHelper

class ExperimentExplanationView : AppCompatActivity() {
/*
    private lateinit var titleTV: TextView
    private lateinit var warningTV: TextView
    private lateinit var instructionTV: TextView
    private lateinit var explanationTV: TextView
    private lateinit var observationTV: TextView
    private lateinit var adviceTV: TextView
    private lateinit var techniqueTV: TextView
    private lateinit var challengeTV: TextView

    private lateinit var sqliteHelper: SQLiteHelper
    private var experimentId: Int = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiment_explanation)

        val experimentId = intent.getIntExtra(EXTRA_EXPERIMENT_ID, -1)
        this.experimentId = experimentId

        if (experimentId == -1) {
            Log.d(ContentValues.TAG,"No recibe category")
            finish()
        } else {
            Log.e(null,experimentId.toString())

            titleTV = findViewById(R.id.titleTVExperiment)
            warningTV = findViewById(R.id.warningTVExperiment)
            instructionTV = findViewById(R.id.instructionTVExperiment)
            explanationTV = findViewById(R.id.explanationTVExperiment)
            observationTV = findViewById(R.id.observationTVExperiment)
            adviceTV = findViewById(R.id.adviceTVExperiment)
            techniqueTV = findViewById(R.id.techniqueTVExperiment)
            challengeTV = findViewById(R.id.challengeTVExperiment)

            sqliteHelper = SQLiteHelper(this)


            val experiment = sqliteHelper.getExperimentById(experimentId)

            if (experiment != null) {
                Log.e(null, experiment.name.toString())
            }


            if (experiment != null) {
                titleTV.text = experiment.name
                if (experiment.warning == null) {
                    warningTV.visibility = View.GONE
                } else {
                    warningTV.text = experiment.warning
                }

                if (experiment.instruction == null) {
                    instructionTV.visibility = View.GONE
                } else {
                    instructionTV.text = experiment.instruction
                }

                if (experiment.explanation == null) {
                    explanationTV.visibility = View.GONE
                } else {
                    explanationTV.text = experiment.explanation
                }

                if (experiment.observation == null) {
                    observationTV.visibility = View.GONE
                } else {
                    observationTV.text = experiment.observation
                }

                if (experiment.advice == null) {
                    adviceTV.visibility = View.GONE
                } else {
                    adviceTV.text = experiment.advice
                }

                if (experiment.technique == null) {
                    techniqueTV.visibility = View.GONE
                } else {
                    techniqueTV.text = experiment.technique
                }

                if (experiment.challenge == null) {
                    challengeTV.visibility = View.GONE
                } else {
                    challengeTV.text = experiment.challenge
                }

            }


        }


    }

    companion object {
        const val EXTRA_EXPERIMENT_ID = "experiment_id"
    }


    private fun initView() {
        titleTV = findViewById(R.id.titleTVExperiment)
        warningTV = findViewById(R.id.warningTVExperiment)
        instructionTV = findViewById(R.id.instructionTVExperiment)
        explanationTV = findViewById(R.id.explanationTVExperiment)
        observationTV = findViewById(R.id.observationTVExperiment)
        adviceTV = findViewById(R.id.adviceTVExperiment)
        techniqueTV = findViewById(R.id.techniqueTVExperiment)
        challengeTV = findViewById(R.id.challengeTVExperiment)
    }

    private fun getExperiment(id: Int) {
        val experiment = sqliteHelper.getExperimentById(id)
    }

    override fun onResume() {
        super.onResume()
        Log.e(null,experimentId.toString())
        getExperiment(experimentId)
    }*/
}
