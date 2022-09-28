package com.example.smaicle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smaicle.databinding.ActivityApprovalBinding

class ApprovalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApprovalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApprovalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnHome.setOnClickListener { Intent(this, HomeActivity::class.java).also { startActivity(it) } }

    }
}