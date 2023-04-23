package com.ppedrosa.android.experimentos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CategoryAdapter(private var categoryList: ArrayList<CategoryModel> = ArrayList())
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.titleTextView)

        fun bindView(category: CategoryModel) {
            name.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bindView(category)
    }

    fun addItems(items: ArrayList<CategoryModel>){
        this.categoryList = items
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}