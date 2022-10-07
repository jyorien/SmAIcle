package com.example.smaicle.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smaicle.R
import com.example.smaicle.data.Goal
import com.google.android.material.progressindicator.LinearProgressIndicator

class GoalAdapter : ListAdapter<Goal, GoalVH>(GoalComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.goal_item_layout, parent, false)
        return GoalVH(view)
    }

    override fun onBindViewHolder(holder: GoalVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class GoalVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(goal: Goal) {
        val nameView : TextView = itemView.findViewById(R.id.goal_name)
        val progressView : LinearProgressIndicator = itemView.findViewById(R.id.goal_progress)
        val progressTextView : TextView = itemView.findViewById(R.id.goal_progress_name)
        val pointsView : TextView = itemView.findViewById(R.id.goal_points)

        nameView.text = goal.title
        val progressText = "${goal.currentProgress} / ${goal.totalProgress}"
        progressTextView.text = progressText
        pointsView.text = "${goal.points} POINTS"

        val progressPercentage = (goal.currentProgress.toFloat() / goal.totalProgress ) * 100
        progressView.progress = progressPercentage.toInt()

    }
}

class GoalComparator : DiffUtil.ItemCallback<Goal>() {
    override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem.title == newItem.title
    }

}