package com.example.smaicle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.smaicle.adapters.PostAdapter
import com.example.smaicle.data.Post
import com.example.smaicle.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {
    private val postList = listOf(
        Post(
            "LIM LI PING JOEY",
            "2h",
            5,
            2,
            "Cat Plant Pots",
            "1x Plastic Bottle",
            R.drawable.catpot),
        Post(
            "RACHEL TAN MIN ZHI",
            "3h",
            10,
            3,
            "Jeans Bag",
            "1x Old Pair of Jeans",
            R.drawable.jeansbag),
        Post(
            "PEK YI CHERN",
            "1d",
            5,
            2,
            "Seat with Drawers",
            "1x Old Drawers \n 2x Old Cushions",
            R.drawable.seatwithdrawers)
    )
    private lateinit var binding: ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Projects"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.feedlist.adapter = PostAdapter()
        (binding.feedlist.adapter as PostAdapter).submitList(postList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Intent(this, HomeActivity::class.java).also { startActivity(it) }
        return super.onOptionsItemSelected(item)
    }
}