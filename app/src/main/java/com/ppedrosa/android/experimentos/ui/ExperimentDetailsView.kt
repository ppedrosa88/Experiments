package com.ppedrosa.android.experimentos.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.database.Experiment
import com.ppedrosa.android.experimentos.database.SQLiteHelper
import com.ppedrosa.android.experimentos.databinding.ActivityExperimentDetailBinding


class ExperimentDetailsView : AppCompatActivity() {

    private lateinit var titleTV: TextView
    private lateinit var warningTV: TextView
    private lateinit var instructionTV: TextView
    private lateinit var explanationTV: TextView
    private lateinit var observationTV: TextView
    private lateinit var adviceTV: TextView
    private lateinit var techniqueTV: TextView
    private lateinit var challengeTV: TextView
    private lateinit var photo:ImageView
    private lateinit var sqliteHelper: SQLiteHelper
    private var experimentId: Int = -1
    private lateinit var binding: ActivityExperimentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExperimentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val experimentId = intent.getIntExtra(EXTRA_EXPERIMENT_ID, -1)
        this.experimentId = experimentId

        if (experimentId == -1) {
            finish()
        } else {
            titleTV = findViewById(R.id.titleTVExp)
            warningTV = findViewById(R.id.warningTVExperiment)
            instructionTV = findViewById(R.id.instructionTVExperiment)
            explanationTV = findViewById(R.id.explanationTVExperiment)
            observationTV = findViewById(R.id.observationTVExperiment)
            adviceTV = findViewById(R.id.adviceTVExperiment)
            techniqueTV = findViewById(R.id.techniqueTVExperiment)
            challengeTV = findViewById(R.id.challengeTVExperiment)
            photo = findViewById(R.id.photo_details)

            sqliteHelper = SQLiteHelper(this)

            val experiment = sqliteHelper.getExperimentById(experimentId)
            val material = sqliteHelper.getMaterialsByExperimentsId(experimentId)

            if (experiment != null) {
                titleTV.text = experiment.name
                formatMaterials(material)

                if (experiment.photo_url != null) {
                    Glide.with(this)
                        .load(experiment.photo_url)
                        .into(photo)
                }

                if (experiment.warning == null) {
                    warningTV.visibility = View.GONE
                } else {
                    warningTV.setTextColor(ContextCompat.getColor(this, R.color.md_red_200))
                    warningTV.text = experiment.warning
                }

                if (experiment.instruction == null) {
                    instructionTV.visibility = View.GONE
                } else {
                    val builder = formatInstruction(experiment)
                    instructionTV.text = builder
                }

                if (experiment.explanation == null) {
                    explanationTV.visibility = View.GONE
                } else {
                    formatExplanation(experiment)
                }

                if (experiment.observation == null) {
                    observationTV.visibility = View.GONE
                } else {
                    val finalText = formatObservation(experiment)
                    observationTV.text = HtmlCompat.fromHtml(
                        finalText.toString(),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }

                if (experiment.advice == null) {
                    adviceTV.visibility = View.GONE
                } else {
                    adviceTV.text = experiment.advice
                }

                if (experiment.technique == null) {
                    techniqueTV.visibility = View.GONE
                } else {
                    val finalText = formatTechnique(experiment)
                    techniqueTV.text = HtmlCompat.fromHtml(
                        finalText.toString(),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }

                if (experiment.challenge == null) {
                    challengeTV.visibility = View.GONE
                } else {
                    val finalText = formatChallenge(experiment)
                    challengeTV.text = HtmlCompat.fromHtml(
                        finalText.toString(),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
            }
        }
    }

    private fun formatChallenge(experiment: Experiment): StringBuilder {
        val challengeText = experiment.challenge
        val words = challengeText?.split("\\s+".toRegex())?.toTypedArray()
        val finalText = StringBuilder()

        if (words != null) {
            for (i in words.indices) {
                val word = words[i]
                if (i < 2) {
                    finalText.append("<font color='#317f43'>").append("<b>").append(word)
                        .append("</b>").append(" </font>")
                } else if (i == 2) {
                    finalText.append("<br>").append(word)
                } else {
                    finalText.append(word).append(" ")
                }
            }
        }
        return finalText
    }

    private fun formatTechnique(experiment: Experiment): StringBuilder {
        val techniqueText = experiment.technique
        val words = techniqueText?.split("\\s+".toRegex())?.toTypedArray()
        val finalText = StringBuilder()

        if (words != null) {
            for (i in words.indices) {
                val word = words[i]
                if (i == 0) {
                    finalText.append("<font color='#00b8d4'>").append("<b>").append(word)
                        .append("</b>").append(" </font>").append("<br>")
                } else {
                    finalText.append(word).append(" ")
                }
            }
        }
        return finalText
    }

    private fun formatObservation(experiment: Experiment): java.lang.StringBuilder {
        val observationText = experiment.observation
        val words = observationText?.split("\\s+".toRegex())?.toTypedArray()
        val finalText = java.lang.StringBuilder()

        if (words != null) {
            for (i in words.indices) {
                val word = words[i]
                if (i == 0) {
                    finalText.append("<font color='#317f43'>").append("<b>").append(word)
                        .append("</b>").append(" </font>").append("<br>")
                } else {
                    finalText.append(word).append(" ")
                }
            }
        }
        return finalText
    }

    private fun formatExplanation(experiment: Experiment) {
        val explanationText = experiment.explanation
        val words = explanationText?.split("\\s+".toRegex())?.toTypedArray()
        val finalText = StringBuilder()
        var type = false
        var variantFinalText: String? = null

        if (words != null) {
            if (words.first() == "TODO") {
                type = false
                for (i in words.indices) {
                    val word = words[i]
                    if (i < 4) {
                        finalText.append("<font color='#317f43'>").append(word)
                            .append(" </font>")
                    } else if (i == 4) {
                        finalText.append("<br>").append("<b>").append(word)
                            .append(" </b>")
                    } else {
                        finalText.append("<b>").append(word).append(" </b>")
                    }
                }
            } else if (words.first() == "¿PORQUÉ?") {
                type = false
                for (i in words.indices) {
                    val word = words[i]
                    if (i == 0) {
                        finalText.append("<font color='#317f43'>").append(word)
                            .append(" </font>").append("<br>")
                    } else {
                        finalText.append("<b>").append(word).append(" </b>")
                    }
                }
            } else {
                type = true
                for (i in words.indices) {
                    variantFinalText = explanationText.replace("\\n", System.lineSeparator())
                }
            }
        }
        if (!type) {
            explanationTV.text = HtmlCompat.fromHtml(
                finalText.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        } else {
            explanationTV.text = variantFinalText
        }
    }

    private fun formatMaterials(material: Map<String, String>?) {
        if (material != null) {
            val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
            val materialList = ArrayList<String>()

            for ((key, value) in material) {
                if (key.first().isUpperCase()) {
                    materialList.add(key)
                } else {
                    materialList.add("$value $key")
                }
            }

            for (mat in materialList) {
                val chip = Chip(this)
                chip.text = mat
                chip.setTextColor(Color.WHITE)
                chip.isClickable = false
                chip.isCheckable = false
                chip.setChipBackgroundColorResource(R.color.md_light_green_500)
                chipGroup.addView(chip)
            }
        }
    }

    private fun formatInstruction(experiment: Experiment): SpannableStringBuilder {
        val instruction = experiment.instruction
        val formattedInstruction = instruction?.replace("\\n", System.lineSeparator())
        val builder = SpannableStringBuilder()

        formattedInstruction?.lines()?.forEach { line ->
            val icon = ContextCompat.getDrawable(this, R.drawable.ic_check)
            icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
            val imageSpan = icon?.let { ImageSpan(it) }

            val spannableString = SpannableString("  $line")
            spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.append(spannableString).appendLine()
        }
        return builder
    }

    companion object {
        const val EXTRA_EXPERIMENT_ID = "experiment_id"
    }
}