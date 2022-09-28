package com.example.smaicle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smaicle.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnGoRewards.setOnClickListener {
            Intent(this, RewardActivity::class.java).also { startActivity(it) }
        }
        binding.btnGoGoals.setOnClickListener {
            Intent(this, GoalActivity::class.java).also { startActivity(it) }
        }
        binding.btnGoProjects.setOnClickListener {
            Intent(this, FeedActivity::class.java).also { startActivity(it) }
        }
    }
}