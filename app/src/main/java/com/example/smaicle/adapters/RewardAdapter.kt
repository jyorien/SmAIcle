package com.example.smaicle.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smaicle.R
import com.example.smaicle.data.Reward

class RewardAdapter : ListAdapter<Reward, RewardVH>(RewardComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reward_item, parent, false)
        return RewardVH(view)
    }

    override fun onBindViewHolder(holder: RewardVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class RewardVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(reward: Reward) {
        val imgView: ImageView = itemView.findViewById(R.id.reward_image)
        val nameView: TextView = itemView.findViewById(R.id.reward_name)
        val costView: TextView = itemView.findViewById(R.id.reward_cost)
        imgView.setImageResource(reward.imageId)
        nameView.text = reward.title
        val points = "${reward.points} POINTS"
        costView.text = points
    }
}

class RewardComparator : DiffUtil.ItemCallback<Reward>() {
    override fun areItemsTheSame(oldItem: Reward, newItem: Reward): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Reward, newItem: Reward): Boolean {
        return oldItem.title == newItem.title
    }

}