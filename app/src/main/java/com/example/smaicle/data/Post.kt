package com.example.smaicle.data

data class Post(
    val username: String,
    val timePosted: String,
    val likesCount: Int,
    val commentsCount: Int,
    val title: String,
    val instructions: String,
    val image: Int
)
