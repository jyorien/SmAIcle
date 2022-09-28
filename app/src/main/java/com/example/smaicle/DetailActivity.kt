package com.example.smaicle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smaicle.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        binding.btnFinish.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
        }
        val event = intent.extras?.getString(EVENT) ?: return
        when(event) {
            "recycle" -> {
                val instructionsList = intent.extras?.getStringArrayList(RECYCLING)
                Log.d("hello", instructionsList.toString())
                binding.instructionsList.adapter = CheckboxAdapter()
                (binding.instructionsList.adapter as CheckboxAdapter).submitList(instructionsList)
            }
            "upcycle" -> {
                val upcyclingTips = intent.extras?.getBundle("bundle")?.getSerializable(UPCYCLING) as HashMap<String, String>
                val ideas = mutableListOf<Idea>()
                upcyclingTips.forEach {
                    val idea = Idea(it.key, it.value)
                    ideas.add(idea)
                }
                binding.instructionsList.adapter = IdeaAdapter()
                (binding.instructionsList.adapter as IdeaAdapter).submitList(ideas)
                binding.btnFinish.text = "View More Ideas"
                binding.btnFinish.setOnClickListener {
                    Intent(this, FeedActivity::class.java).also { startActivity(it) }
                }
                Log.d("hello", upcyclingTips.toString())

            }
        }
    }
}

data class Idea(
    val title: String,
    val url: String
)