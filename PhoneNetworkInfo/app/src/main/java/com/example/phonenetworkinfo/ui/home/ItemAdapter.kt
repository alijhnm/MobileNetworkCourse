package com.example.phonenetworkinfo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phonenetworkinfo.R

class ItemAdapter(val items: List<Pair<String, String>>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemKey.text = items[position].first
        holder.itemValue.text = items[position].second
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemKey: TextView = itemView.findViewById(R.id.itemKey)
        val itemValue: TextView = itemView.findViewById(R.id.itemValue)
    }

}