package com.ppedrosa.android.experimentos.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ppedrosa.android.experimentos.database.Category
import com.ppedrosa.android.experimentos.ui.CategoryView
import com.ppedrosa.android.experimentos.R

class CategoryAdapter(var categoryList: ArrayList<Category> = ArrayList())
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var listener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener (listener: CategoryView) {
        this.listener = listener
    }

    class CategoryViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val card:CardView = itemView.findViewById(R.id.card)
        val name:TextView = itemView.findViewById(R.id.titleTextView)
        val image:ImageView = itemView.findViewById(R.id.imageCardView)

        fun bindView(category: Category) {
            when(category.id){
                1 -> {card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.md_green_A400))}
                2 -> {card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.md_purple_A400))}
                3 -> {card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.md_pink_A200))}
                4 -> {card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.md_yellow_A200))}
                5 -> {card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.md_cyan_A400))}
                6 -> {card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.md_teal_A200))}
            }

            name.text = category.name
            if(category.photo != null) {
                Glide.with(itemView.context)
                    .load(category.photo)
                    .into(image)
            }
        }
        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return CategoryViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bindView(category)

        // Set events
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    fun addItems(items: ArrayList<Category>){
        this.categoryList = items
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}