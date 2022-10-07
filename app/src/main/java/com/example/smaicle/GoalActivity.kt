package com.example.smaicle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.smaicle.adapters.GoalAdapter
import com.example.smaicle.data.Goal
import com.example.smaicle.databinding.ActivityGoalBinding

class GoalActivity : AppCompatActivity() {
    private val goals = listOf(
        Goal(
            "Recycle 5 paper items",
            5,
            4,
            5
        ),
        Goal(
            "Recycle 5 plastic items",
            5,
            4,
            5
        ),
        Goal(
            "Recycle 5 plastic bottle caps",
            3,
            2,
            3
        ),
        Goal(
            "Recycle 5 plastic bottles",
            3,
            2,
            3
        ),
    )
    private lateinit var binding: ActivityGoalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Goals"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.goallist.adapter = GoalAdapter()
        (binding.goallist.adapter as GoalAdapter).submitList(goals)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}