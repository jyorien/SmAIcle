package com.example.smaicle

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class IdeaAdapter: ListAdapter<Idea, IdeaVH>(IdeaComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcycling_list_item_layout, parent, false)
        return IdeaVH(view)
    }

    override fun onBindViewHolder(holder: IdeaVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class IdeaVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(idea: Idea) {
        val titleView : TextView = itemView.findViewById(R.id.title)
        val linkView : TextView = itemView.findViewById(R.id.link)
        titleView.text = idea.title
        val formattedUrl = Html.fromHtml(String.format("<a href=\"%s\">%s</a> ", idea.url, idea.url))
        linkView.text = formattedUrl
    }
}

class IdeaComparator : DiffUtil.ItemCallback<Idea>() {
    override fun areItemsTheSame(oldItem: Idea, newItem: Idea): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Idea, newItem: Idea): Boolean {
        return oldItem == newItem
    }

}