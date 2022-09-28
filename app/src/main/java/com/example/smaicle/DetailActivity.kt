package com.example.smaicle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smaicle.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

            }
        }
    }
}