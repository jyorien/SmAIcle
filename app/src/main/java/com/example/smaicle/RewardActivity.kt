package com.example.smaicle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.smaicle.adapters.RewardAdapter
import com.example.smaicle.data.Reward
import com.example.smaicle.databinding.ActivityRewardBinding

class RewardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRewardBinding
    private val rewards = listOf(
        Reward(
            R.drawable.pens,
            "Eco-Friendly Pen",
            15
        ),
        Reward(
            R.drawable.pins,
            "Enamel Pin",
            30
        ),
        Reward(
            R.drawable.straws,
            "Reusable Straw",
            50
        ),
        Reward(
            R.drawable.popvouchers,
            "$20 Popular Voucher",
            100
        ),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Rewards"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rewardlist.adapter = RewardAdapter()
        (binding.rewardlist.adapter as RewardAdapter).submitList(rewards)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}