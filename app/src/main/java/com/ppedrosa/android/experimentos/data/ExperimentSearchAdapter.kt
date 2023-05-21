package com.ppedrosa.android.experimentos.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.database.Experiment

class ExperimentSearchAdapter(
    private var experimentList: ArrayList<Experiment> = ArrayList(),
    private var listener: OnItemClickListener)
    : RecyclerView.Adapter<ExperimentSearchAdapter.ExperimentSearchViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ExperimentSearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.titleTV)
        private val photo: ImageView = itemView.findViewById(R.id.ExperimentsIV)

        fun bindView(experiment: Experiment) {
            name.text = experiment.name
            if(experiment.photo_url != null) {
                Glide.with(itemView.context)
                    .load(experiment.photo_url)
                    .into(photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperimentSearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.experiment_card_view,parent,false)
        return ExperimentSearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExperimentSearchViewHolder, position: Int) {
        val experiment = experimentList[position]
        val index = experimentList[position].id
        holder.bindView(experiment)
        // Set events
        holder.itemView.setOnClickListener {
            if (index != null) {
                listener.onItemClick(index)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: ArrayList<Experiment>) {
        this.experimentList = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return experimentList.size
    }

}

