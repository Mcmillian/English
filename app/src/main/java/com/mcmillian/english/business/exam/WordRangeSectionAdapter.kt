package com.mcmillian.english.business.exam

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mcmillian.english.R

class WordRangeSectionAdapter(var sections:MutableList<String>,val onItemClick:(position:Int,title:String)->Unit) :RecyclerView.Adapter<WordRangeSectionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount() = sections.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = sections[position]
        (holder.itemView as TextView).text = s
        holder.itemView.setOnClickListener {
            onItemClick(position,s)
        }
    }

    inner class ViewHolder( parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_selection,parent,false)
    )
}