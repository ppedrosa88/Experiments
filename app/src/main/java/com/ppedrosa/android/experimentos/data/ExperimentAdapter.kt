package com.ppedrosa.android.experimentos.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ppedrosa.android.experimentos.database.Experiment
import com.ppedrosa.android.experimentos.R
import com.ppedrosa.android.experimentos.ui.ExperimentView

class ExperimentAdapter(private var experimentList: ArrayList<Experiment> = ArrayList())
    : RecyclerView.Adapter<ExperimentAdapter.ExperimentViewHolder>() {

    private lateinit var listener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener (listener: ExperimentView) {
        this.listener = listener
    }

    class ExperimentViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.titleTV)
        val image: ImageView = itemView.findViewById(R.id.ExperimentsIV)

        fun bindView(experiment: Experiment) {
            name.text = experiment.name
            if(experiment.photo_url != null) {
                Glide.with(itemView.context)
                    .load(experiment.photo_url)
                    .into(image)
            }
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperimentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.experiment_card_view,parent,false)
        return ExperimentViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ExperimentViewHolder, position: Int) {
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

    fun addItems(items: ArrayList<Experiment>) {
        this.experimentList = items
    }

    override fun getItemCount(): Int {
        return experimentList.size
    }
}
