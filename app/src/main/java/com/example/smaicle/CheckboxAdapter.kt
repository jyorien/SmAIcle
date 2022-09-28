package com.example.smaicle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CheckboxAdapter : ListAdapter<String, StringVH>(StringComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checklist_item_layout, parent, false)
        return StringVH(view)
    }

    override fun onBindViewHolder(holder: StringVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class StringVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(text: String) {
        val txtView = itemView.findViewById<TextView>(R.id.check_text)
        txtView.text = text
    }
}

class StringComparator : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}