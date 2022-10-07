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
import com.example.smaicle.data.Post

class PostAdapter  : ListAdapter<Post, PostVH>(PostComparator()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_post_item_layout, parent, false)
        return PostVH(view)
    }

    override fun onBindViewHolder(holder: PostVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(post: Post) {
        val nameView : TextView = itemView.findViewById(R.id.post_name)
        val postTime : TextView = itemView.findViewById(R.id.post_time)
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
        val postStats : TextView = itemView.findViewById(R.id.post_stats)
        val postTitle : TextView = itemView.findViewById(R.id.post_title)
        val postInstructions : TextView = itemView.findViewById(R.id.post_instructions)
        nameView.text = post.username
        postTime.text = post.timePosted
        postImage.setImageResource(post.image)
        val stats = "${post.likesCount} likes and ${post.commentsCount} comments"
        postStats.text = stats
        postTitle.text = post.title
        postInstructions.text = post.instructions
    }
}

class PostComparator : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.title == newItem.title
    }

}